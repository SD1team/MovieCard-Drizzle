package com.example.drizzle16.moviecard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drizzle16.moviecard.dataSet.Results;
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

        /*뷰 visibility 초기화*/
        holder.nullTv.setVisibility(View.GONE);
        holder.imgv.setVisibility(View.VISIBLE);

        holder.tv.setText(mResultSet.get(position).getTitle());

        /*하트 세팅*/
        if(mResultSet.get(position).isLoved()){
            holder.loveV.setImageResource(R.drawable.like);
        }else {
            holder.loveV.setImageResource(R.drawable.likegray);
        }
        holder.loveV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResultSet.get(position).isLoved() == false) {
                    mResultSet.get(position).setLoved(true);
                    holder.loveV.setImageResource(R.drawable.like);

                    Toast toast = Toast.makeText(context, "I LOVE IT!", Toast.LENGTH_SHORT);
                    toast.show();

                } else if (mResultSet.get(position).isLoved() == true) {
                    mResultSet.get(position).setLoved(false);
                    holder.loveV.setImageResource(R.drawable.likegray);

                    Toast toast = Toast.makeText(context, "LOVE CANCELED", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        String imgFrontPath = context.getResources().getString(R.string.img_front);
        final Context context = holder.imgv.getContext();

        /*이전에 클릭됐었는지 체크*/
        if (mResultSet.get(position).isClicked()) {

            String fullText = OrganizedInfo(position);
            holder.infoTv.setText(Html.fromHtml(fullText));
            holder.scrollV.setVisibility(View.VISIBLE);
            holder.infoTv.setVisibility(View.VISIBLE);
        } else {
            holder.scrollV.setVisibility(View.GONE);
            holder.infoTv.setVisibility(View.GONE);
        }

        /*제공된 포스터가 있다면 이미지로드*/

        if (mResultSet.get(position).getPoster_path() != null) {
            Log.i("lsb", "getPoster_path is not null in position : " + position);

            Picasso.with(context).setIndicatorsEnabled(true);
            Picasso.with(context)
                    .load(imgFrontPath + mResultSet.get(position).getPoster_path())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.waiting)
                    .error(R.drawable.erroricon)
                    .into(holder.imgv);

            /*이미지뷰 현재 클릭된 여부에 따라 클릭이벤트*/
            if (holder.infoTv.getVisibility() == View.GONE) {
                holder.imgv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getInfoBox(holder);
                    }
                });
            }

        } else {
            /*제공된 포스터가 없습니다*/
            holder.nullTv.setVisibility(View.VISIBLE);
            if (holder.infoTv.getVisibility() == View.GONE) {
                holder.nullTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("lsb", "nullTv onclick");
                        getInfoBox(holder);

                    }
                });
            }
            holder.imgv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        if (mResultSet != null) {
            Log.i("lsb", "getItemCount : " + mResultSet.size());
            return mResultSet.size();
        } else {
            Log.i("lsb", "mItemSet.getResults is null");
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;
        public ImageView loveV;
        public TextView nullTv;
        public ImageView imgv;
        public TextView infoTv;
        public ScrollView scrollV;

        public ViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.title);
            nullTv = (TextView) view.findViewById(R.id.nullposter);
            imgv = (ImageView) view.findViewById(R.id.poster);
            infoTv = (TextView) view.findViewById(R.id.infoBox);
            scrollV = (ScrollView) view.findViewById(R.id.scrollview);
            loveV = (ImageView) view.findViewById(R.id.titleLove);
        }

    }

    /*뷰에 띄울 info 정리*/
    public String OrganizedInfo(int position) {

        String originalTitle = "";

        if (mResultSet.get(position).getTitle() != null) {
            if (mResultSet.get(position).isAdult()) {
                String adultImg = context.getResources().getString(R.string.adult_img);
                originalTitle = mResultSet.get(position).getTitle() + " " + adultImg + '\n';
            } else {
                originalTitle = mResultSet.get(position).getTitle() + '\n';
            }
        } else {
            originalTitle = "# NO TITLE DATA #";
        }

        /*모든 장르id에 따른 value 찍기*/
        String fullGenres = "";
        String temp = "";

        if (mResultSet.get(position).getGenre_ids().size() != 0) {
            for (int i = 0; i < mResultSet.get(position).getGenre_ids().size(); ++i) {
                int id = mResultSet.get(position).getGenre_ids().get(i);
                temp = mGenres.get(id);
                fullGenres += temp + " | ";
            }
        } else {
            fullGenres = "# NO GENRE DATA # |";
        }

        String backInfo = mResultSet.get(position).toString();

        String fullText = "<font color='#EB2A31'><b>" + originalTitle + "</b></font><br><br>" + "| " + fullGenres + "<br>" + backInfo;

        return fullText;
    }

    /*Info뷰 띄우기*/
    public void getInfoBox(final ViewHolder holder) {

        final int position = holder.getAdapterPosition();
        String fullText = OrganizedInfo(position);

        holder.infoTv.setText(Html.fromHtml(fullText));
        holder.scrollV.setVisibility(View.VISIBLE);
        holder.infoTv.setVisibility(View.VISIBLE);

        holder.infoTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP: {

                        holder.infoTv.setVisibility(v.GONE);
                        holder.scrollV.setVisibility(v.GONE);
                        mResultSet.get(position).setClicked(false);

                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        if (holder.infoTv.getLineCount() > 24) {
                            holder.scrollV.getParent().requestDisallowInterceptTouchEvent(true);
                        }

                        break;
                    }
                }
                return false;

            }
        });

        holder.infoTv.setFocusable(true);
        mResultSet.get(position).setClicked(true);

    }

}
