package com.hdk.subway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyTabRecyclerAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<StationItem> stationItems;
    public MyTabRecyclerAdapter(Context context, ArrayList<StationItem> stationItems) {
        this.context = context;
        this.stationItems = stationItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.recyclerview_item, parent, false);

        VH vh = new VH(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StationItem stationItem = stationItems.get(position);

        VH vh = (VH) holder;

    }

    @Override
    public int getItemCount() {
        return stationItems.size();
    }

    class VH extends RecyclerView.ViewHolder{

//        TextView ;

        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

}//MyTabRecyclerAdapter...
