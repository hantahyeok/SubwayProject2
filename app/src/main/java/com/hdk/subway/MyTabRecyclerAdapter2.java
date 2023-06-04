package com.hdk.subway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyTabRecyclerAdapter2 extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item2> items;
    public MyTabRecyclerAdapter2(Context context, ArrayList<Item2> items) {
        this.context = context;
        this.items = items;
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
        Item2 item = items.get(position);

        VH vh = (VH) holder;
//        vh.tv1.setText(item.subwayId);
        vh.tv2.setText(item.trainLineNm);
        vh.tv3.setText(item.statnNm);
        vh.tv4.setText(item.btrainSttus);
        vh.tv5.setText(item.barvlDt);
        vh.tv6.setText(item.bstatnNm);
        vh.tv7.setText(item.recptnDt);
        vh.tv8.setText(item.arvlMsg2);
        vh.tv9.setText(item.arvlMsg3);
        vh.tv10.setText(item.arvlCd);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;

        public VH(@NonNull View itemView) {
            super(itemView);

            tv1= itemView.findViewById(R.id.tv1);
            tv2= itemView.findViewById(R.id.tv2);
            tv3= itemView.findViewById(R.id.tv3);
            tv4= itemView.findViewById(R.id.tv4);
            tv5= itemView.findViewById(R.id.tv5);
            tv6= itemView.findViewById(R.id.tv6);
            tv7= itemView.findViewById(R.id.tv7);
            tv8= itemView.findViewById(R.id.tv8);
            tv9= itemView.findViewById(R.id.tv9);
            tv10= itemView.findViewById(R.id.tv10);


        }
    }

}//MyTabRecyclerAdapter...
