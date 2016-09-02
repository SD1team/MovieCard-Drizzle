package com.example.drizzle16.moviecard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by drizzle16 on 2016-08-31.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Results> mResultSet;
    String imgFrontPath = "http://image.tmdb.org/t/p/w500";
    private Context context;

    public MyAdapter(Context context, List<Results> resultsObj) {
        this.context = context;
        mResultSet = resultsObj;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv.setText(mResultSet.get(position).title);
        // holder.imgv.setImageResource(mItemSet.getResults().get(position).getPoster_path());
        Context context = holder.imgv.getContext();
        if(mResultSet.get(position).getPoster_path()!=null) {
            Picasso.with(context)
                    .load(imgFrontPath + mResultSet.get(position).getPoster_path())
                    .placeholder(R.drawable.waiting)
                    .error(R.drawable.erroricon)
                    .fit()
                    .centerCrop()
                    .into(holder.imgv);
        }else{
            holder.nullTv.setVisibility(View.VISIBLE);
            holder.imgv.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {

            if (mResultSet != null) {
                return mResultSet.size();
            } else {
                Log.i("lsb", "mItemSet.getResults is null");
                return 1;
            }

        }





    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;
        public TextView nullTv;
        public ImageView imgv;

        public ViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.title);

            nullTv = (TextView) view.findViewById(R.id.nullposter);
            nullTv.setVisibility(view.GONE);
            imgv = (ImageView) view.findViewById(R.id.poster);
        }
    }

}


