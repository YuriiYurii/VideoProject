package com.yuriitsap.videoproject.utils.rest;

import com.yuriitsap.videoproject.ui.model.UserVideo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yuriitsap on 05.12.16.
 */
public interface VineVideoEndpoint {

    @GET("{tag}")
    Call<List<UserVideo>> getAllVideosByTag(@Path("tag") String tag);
}
