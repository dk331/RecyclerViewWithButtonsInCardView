package com.dhananjay.recyclerviewwithbuttonsincardview.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.dhananjay.recyclerviewwithbuttonsincardview.app.Constants;
import com.dhananjay.recyclerviewwithbuttonsincardview.models.ApiResponse;
import com.dhananjay.recyclerviewwithbuttonsincardview.models.Result;
import com.google.gson.Gson;

import java.util.List;

public class SharedPreference {

    private static final String PREFS_NAME = "RecyclerViewButtonsInCardView";
    private static final String RESPONSE = "ApiResponse";

    public SharedPreference() {
        super();
    }

    public void saveResponse(Context context, ApiResponse apiResponse) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(apiResponse);

        editor.putString(RESPONSE, jsonResponse);

        editor.apply();
    }

    public void accept(Context context, Result result) {
        ApiResponse apiResponse = getResponse(context);
        if (apiResponse != null && apiResponse.getResults() != null) {
            List<Result> results = apiResponse.getResults();
            for (int i = 0; i < results.size(); i++) {
                if (result.getLogin().getUuid().equals(results.get(i).getLogin().getUuid())) {
                    results.get(i).setStatus(Constants.ACCEPT);
                }
            }
        }
        saveResponse(context, apiResponse);
    }

    public void decline(Context context, Result result) {
        ApiResponse apiResponse = getResponse(context);
        if (apiResponse != null && apiResponse.getResults() != null) {
            List<Result> results = apiResponse.getResults();
            for (int i = 0; i < results.size(); i++) {
                if (result.getLogin().getUuid().equals(results.get(i).getLogin().getUuid())) {
                    results.get(i).setStatus(Constants.DECLINE);
                }
            }
        }
        saveResponse(context, apiResponse);
    }

    public ApiResponse getResponse(Context context) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        ApiResponse apiResponse;
        if (settings.contains(RESPONSE)) {
            String jsonResponse = settings.getString(RESPONSE, null);
            Gson gson = new Gson();
            apiResponse = gson.fromJson(jsonResponse,
                    ApiResponse.class);

        } else
            return null;

        return apiResponse;
    }

    public boolean hasResponse(Context context) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        return settings.contains(RESPONSE);
    }
}
