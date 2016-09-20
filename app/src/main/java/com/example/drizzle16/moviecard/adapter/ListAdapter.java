package com.example.drizzle16.moviecard.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drizzle16.moviecard.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by drizzle16 on 2016-09-13.
 */

public class ListAdapter extends BaseAdapter {

    ArrayList<String> mTrashedArray = new ArrayList<>();

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    public ListAdapter(ArrayList trashedArray) {
        mTrashedArray = trashedArray;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        String imgFrontPath = context.getResources().getString(R.string.img_front);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.loved_item, parent, false);
        }

        ImageView posterV = (ImageView) convertView.findViewById(R.id.lovedposter);
        TextView titleV = (TextView) convertView.findViewById(R.id.lovedtitle);
        TextView genreV = (TextView) convertView.findViewById(R.id.lovedgenre);

        final ListViewItem listViewItem = listViewItemList.get(position);

        Picasso.with(context).setIndicatorsEnabled(false);
        Picasso.with(context)
                .load(imgFrontPath + listViewItem.getPosterPath())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.waiting)
                .error(R.drawable.erroricon)
                .into(posterV);
        titleV.setText(listViewItem.getTitle());
        genreV.setText(listViewItem.getGenre());

        Button trashBtn = (Button) convertView.findViewById(R.id.trashcan);
        trashBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTrashedArray.add(listViewItemList.get(position).getTitle());
                }catch (Exception e){
                    Log.i("lsb", String.valueOf(e));
                }
                listViewItemList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    public void addItem(String posterPath, String title, String genre) {
        ListViewItem item = new ListViewItem();

        item.setPosterPath(posterPath);
        item.setTitle(title);
        item.setGenre(genre);

        listViewItemList.add(item);
    }
}


