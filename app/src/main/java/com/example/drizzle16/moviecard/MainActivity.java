package com.example.drizzle16.moviecard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import android.R;

public class MainActivity extends AppCompatActivity {

    NowPlayingItem nowPlayingItemObj;
    GenreObj genresObj;
    HashMap<Integer, String> genreMap = new HashMap<>();
    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        toolbar.setLogo(R.drawable.movie);

        rv = (RecyclerView) findViewById(R.id.recycler);
        if (rv != null) {
            rv.setHasFixedSize(true);
        }
        mLinearLayoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(mLinearLayoutManager);


        String baseURL = getString(R.string.base_url);
        String apiKey = getString(R.string.key);

        context = getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);

        Call<GenreObj> cGenres = service.genres(apiKey);
        Call<NowPlayingItem> cNowItem = service.nowItem(apiKey);

        cGenres.enqueue(new Callback<GenreObj>() {
            @Override
            public void onResponse(Call<GenreObj> call, Response<GenreObj> response) {
                if (response.isSuccessful()) {
                    genresObj = response.body();
                      /*장르 id, name을 key, value로 저장*/
                    if (genresObj != null) {
                        for (int i = 0; i < genresObj.genres.size(); ++i) {
                            genreMap.put(genresObj.genres.get(i).getId(), genresObj.genres.get(i).getName());
                        }
                    } else {
                        Log.i("lsb", "genresObj is null");
                    }
                }
            }

            @Override
            public void onFailure(Call<GenreObj> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });


        cNowItem.enqueue(new Callback<NowPlayingItem>() {
            @Override
            public void onResponse(Call<NowPlayingItem> call, Response<NowPlayingItem> response) {
                if (response.isSuccessful()) {
                    nowPlayingItemObj = response.body();

                    if (nowPlayingItemObj != null) {
                        List<Results> resultsObj = nowPlayingItemObj.getResults();
                        mAdapter = new MyAdapter(context, genreMap, resultsObj);
                        rv.setAdapter(mAdapter);
                    } else {
                        Log.i("lsb", "nowPlayingItemObj is null");
                    }
                } else {
                    Log.i("lsb", "onResponse doesn't work");
                }
            }

            @Override
            public void onFailure(Call<NowPlayingItem> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });



    }
}
