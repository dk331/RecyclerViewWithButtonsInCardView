package com.dhananjay.recyclerviewwithbuttonsincardview.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dhananjay.recyclerviewwithbuttonsincardview.R;
import com.dhananjay.recyclerviewwithbuttonsincardview.models.Result;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.InvitationHolder> {

    private Context context;
    private List<Result> results;
    private OnRecordEventListener listener;

    public CardAdapter(Context context, List<Result> results, OnRecordEventListener listener) {
        this.context = context;
        this.results = results;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InvitationHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new InvitationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitationHolder holder, int position) {
        Result planet = results.get(position);
        holder.setDetails(planet);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class InvitationHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtDeclined, txtAccepted;
        private ImageButton btn_decline, btn_accept;
        private CircleImageView img;

        InvitationHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            img = itemView.findViewById(R.id.img);
            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_decline = itemView.findViewById(R.id.btn_decline);
            txtAccepted = itemView.findViewById(R.id.txtAccepted);
            txtDeclined = itemView.findViewById(R.id.txtDeclined);
        }

        void setDetails(final Result result) {
            txtName.setText(String.format("%s %s", result.getName().getFirst(), result.getName().getLast().substring(0, 1)));


            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.accept(result);
                    result.setStatus(Constants.ACCEPT);
                    setVisibility(result);
                }
            });


            btn_decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.decline(result);
                    result.setStatus(Constants.DECLINE);
                    setVisibility(result);
                }
            });


            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(result.getPicture().getThumbnail())
                    .placeholder(R.drawable.other)
                    .error(R.drawable.other)
                    .into(img);

            setVisibility(result);

            // TODO: 19/11/2019 we can add code here to show badges like just joined, description, age & expectations
        }

        void setVisibility(Result result) {
            switch (result.getStatus()) {
                case Constants.PENDING:
                    txtDeclined.setVisibility(View.GONE);
                    txtAccepted.setVisibility(View.GONE);
                    btn_decline.setVisibility(View.VISIBLE);
                    btn_accept.setVisibility(View.VISIBLE);
                    break;
                case Constants.ACCEPT:
                    txtDeclined.setVisibility(View.GONE);
                    txtAccepted.setVisibility(View.VISIBLE);
                    btn_decline.setVisibility(View.GONE);
                    btn_accept.setVisibility(View.GONE);
                    break;
                case Constants.DECLINE:
                    txtDeclined.setVisibility(View.VISIBLE);
                    txtAccepted.setVisibility(View.GONE);
                    btn_decline.setVisibility(View.GONE);
                    btn_accept.setVisibility(View.GONE);
                    break;
            }

        }

    }

}
