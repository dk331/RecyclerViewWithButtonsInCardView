package com.dhananjay.recyclerviewwithbuttonsincardview.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhananjay.recyclerviewwithbuttonsincardview.R;
import com.dhananjay.recyclerviewwithbuttonsincardview.data.SharedPreference;
import com.dhananjay.recyclerviewwithbuttonsincardview.models.ApiResponse;
import com.dhananjay.recyclerviewwithbuttonsincardview.models.Result;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnRecordEventListener {

    private RecyclerView recyclerView;
    private SharedPreference sharedPreference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        sharedPreference = new SharedPreference();
        if (sharedPreference.hasResponse(this)) {
            ApiResponse apiResponse = sharedPreference.getResponse(this);
            if (apiResponse != null) {
                generateDataList(apiResponse);
            }
        } else {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading....");
            progressDialog.show();

            /*Create handle for the RetrofitInstance interface*/
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ApiResponse> call = service.getResponse(10);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NotNull Call<ApiResponse> call, @NotNull Response<ApiResponse> apiResponse) {
                    progressDialog.dismiss();
                    if (apiResponse.body() != null) {
                        generateDataList(apiResponse.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ApiResponse> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void generateDataList(ApiResponse apiResponse) {
        sharedPreference.saveResponse(this, apiResponse);
        // set adapter
        CardAdapter adapter = new CardAdapter(this, apiResponse.getResults(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void accept(Result result) {
        sharedPreference.accept(this, result);
    }

    @Override
    public void decline(Result result) {
        sharedPreference.decline(this, result);
    }
}
