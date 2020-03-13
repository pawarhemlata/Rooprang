package com.tsi.rooprang.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tsi.rooprang.retrofit.BaseUrl.BASE_URL2;

public class NewApiClient {


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.connectTimeout(15, TimeUnit.SECONDS);
            client.readTimeout(15, TimeUnit.SECONDS);
            client.writeTimeout(15, TimeUnit.SECONDS);
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client.build())
                    .build()
            ;
        }
        return retrofit;
    }
}
