package com.nairbspace.retrofitdaggerexample.retrofit;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An interceptor that allows runtime changes to the API Base URL in Retrofit.
 * The Base URL is set by calling the {@link UrlInterceptor#setUrl(String)} method.
 */
public class UrlInterceptor implements Interceptor {
    private String mScheme;
    private String mHost;

    public void setUrl(String url) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl != null) {
            mScheme = httpUrl.scheme();
            mHost = httpUrl.host();
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // If new Base URL is properly formatted then replace the old one
        if (mScheme != null && mHost != null) {
            HttpUrl url = original.url().newBuilder()
                    .scheme(mScheme)
                    .host(mHost)
                    .build();
            original = original.newBuilder()
                    .url(url)
                    .build();
        }
        return chain.proceed(original);
    }
}
