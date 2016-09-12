package com.example.drizzle16.moviecard;

import com.example.drizzle16.moviecard.dataSet.GenreObj;
import com.example.drizzle16.moviecard.dataSet.NowPlayingItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by drizzle16 on 2016-09-01.
 */
public interface CallByGet {
    @GET("movie/now_playing")
    Call<NowPlayingItem> nowItem(@Query("api_key") String key);

    @GET("genre/movie/list")
    Call<GenreObj> genres(@Query("api_key") String key);
}



