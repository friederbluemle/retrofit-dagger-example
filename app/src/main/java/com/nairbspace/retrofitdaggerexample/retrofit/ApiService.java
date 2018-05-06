package com.nairbspace.retrofitdaggerexample.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Dummy API interface used for Retrofit
 */
public interface ApiService {
    @GET("/")
    Call<ResponseBody> get();
}
