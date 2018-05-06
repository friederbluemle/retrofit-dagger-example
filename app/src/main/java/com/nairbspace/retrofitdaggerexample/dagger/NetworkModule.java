package com.nairbspace.retrofitdaggerexample.dagger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nairbspace.retrofitdaggerexample.retrofit.ApiService;
import com.nairbspace.retrofitdaggerexample.retrofit.UrlInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    UrlInterceptor provideUrlInterceptor() { // This is where the Interceptor object is constructed
        return new UrlInterceptor();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(UrlInterceptor interceptor) { // The Interceptor is then added to the client
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(Gson gson, OkHttpClient okHttpClient) { // The Client is then added to Retrofit
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("http://localhost/"); // Dummy Base URL needed to create instance
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit.Builder builder) { // This is where Retrofit is finally created
        return builder.build().create(ApiService.class);     // with the Interface we provided
    }
}
