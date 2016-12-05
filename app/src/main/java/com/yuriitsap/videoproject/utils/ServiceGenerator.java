package com.yuriitsap.videoproject.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yuriitsap.videoproject.ui.model.UserVideo;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yuriitsap on 05.12.16.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "https://api.vineapp.com/timelines/tags/";

    private static String mBaseUrl = API_BASE_URL;

    private static Retrofit retrofit;
    private static Retrofit.Builder builder;
    public static Gson mGson;

    static {
        Type listType = new TypeToken<List<UserVideo>>() {
        }.getType();
        mGson = new GsonBuilder().registerTypeAdapter(listType, new UserVideoDeserializer()).create();
        builder =
                new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create(mGson))
                        .baseUrl(API_BASE_URL);
        retrofit = builder.build();
    }

    // No need to instantiate this class.
    private ServiceGenerator() {

    }

    public static void changeApiBaseUrl(String newApiBaseUrl) {
        mBaseUrl = newApiBaseUrl;


        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .baseUrl(mBaseUrl);
        retrofit = builder.build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        S service = retrofit.create(serviceClass);
        return service;
    }

}
