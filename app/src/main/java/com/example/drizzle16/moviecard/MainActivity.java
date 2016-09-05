package com.example.drizzle16.moviecard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import android.R;

public class MainActivity extends AppCompatActivity {

    NowPlayingItem nowPlayingItemObj;
    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //     setSupportActionBar(toolbar);

        String baseURL = getString(R.string.base_url);
        String apiKey = getString(R.string.key);

        context = getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<NowPlayingItem> cNowItem = service.nowItem(apiKey);

        cNowItem.enqueue(new Callback<NowPlayingItem>() {
            @Override
            public void onResponse(Call<NowPlayingItem> call, Response<NowPlayingItem> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        nowPlayingItemObj = response.body();
                        //  Log.i("lsb", String.valueOf(nowPlayingItemObj.getResults().get(0).getPoster_path()));

                        rv = (RecyclerView) findViewById(R.id.recycler);
                        if (rv != null) {
                            rv.setHasFixedSize(true);
                        }

                        mLinearLayoutManager = new LinearLayoutManager(context);
                        rv.setLayoutManager(mLinearLayoutManager);

                        List<Results> resultsObj = nowPlayingItemObj.getResults();
                        mAdapter = new MyAdapter(context, resultsObj);
                        rv.setAdapter(mAdapter);

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
