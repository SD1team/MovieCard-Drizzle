package com.example.drizzle16.moviecard;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by drizzle16 on 2016-09-01.
 */
public interface GitHubService {
    @GET("movie/now_playing")
    Call<NowPlayingItem> nowItem(@Query("api_key") String key);

    @GET("genre/movie/list")
    Call<ArrayList<Genres>> genres(@Query("api_key") String key);
}



