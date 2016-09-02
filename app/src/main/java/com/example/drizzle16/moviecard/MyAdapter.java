package com.example.drizzle16.moviecard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by drizzle16 on 2016-08-31.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private NowPlayingItem mItemSet;
    String imgFrontPath = "http://image.tmdb.org/t/p/w500";
    private Context context;

    public MyAdapter(Context context, NowPlayingItem nowPlayingItemObj) {
        this.context = context;
        mItemSet = nowPlayingItemObj;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(mItemSet.getResults().get(position).title);
        // holder.imgv.setImageResource(mItemSet.getResults().get(position).getPoster_path());
        Context context = holder.imgv.getContext();
        Picasso.with(context)
                .load(imgFrontPath + mItemSet.getResults().get(position).getPoster_path())
                .into(holder.imgv);

    }

    @Override
    public int getItemCount() {
        return mItemSet.getResults().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;
        public ImageView imgv;

        public ViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.title);
            imgv = (ImageView) view.findViewById(R.id.poster);
        }
    }

}


