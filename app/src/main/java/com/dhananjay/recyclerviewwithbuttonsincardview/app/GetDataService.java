package com.dhananjay.recyclerviewwithbuttonsincardview.app;

import com.dhananjay.recyclerviewwithbuttonsincardview.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/api/?")
    Call<ApiResponse> getResponse(@Query("results") int results);
}
