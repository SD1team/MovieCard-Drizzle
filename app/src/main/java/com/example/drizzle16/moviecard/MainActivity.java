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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<NowPlayingItem> cNowItem = service.nowItem("9135f4d26cf27d0c9b9b106e80356a17");

        cNowItem.enqueue(new Callback<NowPlayingItem>() {
            @Override
            public void onResponse(Call<NowPlayingItem> call, Response<NowPlayingItem> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        nowPlayingItemObj = response.body();
                      //  Log.i("lsb", String.valueOf(nowPlayingItemObj.getResults().get(0).getPoster_path()));

                        rv = (RecyclerView) findViewById(R.id.recycler);
                        rv.setHasFixedSize(true);

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


//        }
//        catch (Exception e){
//            Log.i("lsb", String.valueOf(e));
//        }

    }
}

        /*


        //  nowPlayingItemObj = MyRetrofit().execute().bo;
//        nowPlayingItemObj = MyRetrofit.execute("https://api.themoviedb.org/3/movie/");

    }

    public class MyRetrofit extends AsyncTask<Void, Void, NowPlayingItem> {

        Retrofit retrofit;
        NowPlayingItem nowPlayingItemObj;

//    @Override
//    protected void onPreExecute(){
//
//    }



        @Override
        protected NowPlayingItem doInBackground(Void... params) {
            try {
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/3/movie/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                GitHubService service = retrofit.create(GitHubService.class);
                NowPlayingItem cNowItem = service.nowItem("9135f4d26cf27d0c9b9b106e80356a17");

          //      rv = (RecyclerView)
                // Log.i("lsb", String.valueOf(cNowItem.execute().body().getResults().get(0).getPoster_path()));
                //  Log.i("lsb", String.valueOf(nowPlayingItemObj.getResults().get(0).getPoster_path()));

                return cNowItem;

            } catch (Exception e) {
                Log.i("lsb", String.valueOf(e));
                return null;
            }

        }

    }


}
*/