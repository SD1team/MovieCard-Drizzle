package com.example.drizzle16.moviecard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by drizzle16 on 2016-08-31.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Results> mResultSet;
    private Context context;
    private HashMap<Integer, String> mGenres;

    public MyAdapter(Context context, HashMap<Integer, String> genres, List<Results> resultsObj) {
        this.context = context;
        mGenres = genres;
        mResultSet = resultsObj;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String imgFrontPath = context.getResources().getString(R.string.img_front);
        holder.tv.setText(mResultSet.get(position).title);

       final Context context = holder.imgv.getContext();

        if (mResultSet.get(position).getPoster_path() != null) {
            Picasso.with(context)
                    .load(imgFrontPath + mResultSet.get(position).getPoster_path())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.waiting)
                    .error(R.drawable.erroricon)
                    .into(holder.imgv);
            holder.imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.infoTv.getVisibility()==View.GONE) {
                        String originalTitle = mResultSet.get(position).getTitle()+'\n';
                        /*모든 장르 찍기*/
                        String fullGenres = "";
                        String temp = "";
                        for(int i=0;i<mResultSet.get(position).getGenre_ids().size();++i){
                            int id = mResultSet.get(position).getGenre_ids().get(i);
                            temp = mGenres.get(id);
                            fullGenres += temp + " | ";
                        }
                        String backInfo = mResultSet.get(position).toString();

                        String fullText = "<b>"+ originalTitle + "</b><br><br>"+ "| " +fullGenres +"<br>" + backInfo;
                        //String genres =;
                        //holder.infoTv.setMovementMethod(new ScrollingMovementMethod());
                        holder.infoTv.setText(Html.fromHtml(fullText));
                        holder.infoTv.setVisibility(v.VISIBLE);
                    }else if(holder.infoTv.getVisibility()==View.VISIBLE){
                        holder.infoTv.setVisibility(v.GONE);
                    }
                }
            });
        } else {
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
        public TextView infoTv;

        public ViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.title);
            nullTv = (TextView) view.findViewById(R.id.nullposter);
            imgv = (ImageView) view.findViewById(R.id.poster);
            infoTv = (TextView) view.findViewById(R.id.infoBox);
        }

    }

}


