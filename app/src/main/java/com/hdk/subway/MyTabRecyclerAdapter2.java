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

        vh.trainLineNm.setText(item.trainLineNm);
        vh.last.setText(item.last);
        vh.btrainSttus.setText("- (" + item.btrainSttus + ")");
        vh.arvlMsg2.setText(item.arvlMsg2);
        vh.recptnDt.setText(item.recptnDt);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView trainLineNm, btrainSttus, last, arvlMsg2, recptnDt;

        public VH(@NonNull View itemView) {
            super(itemView);

            trainLineNm = itemView.findViewById(R.id.trainLineNm);
            btrainSttus= itemView.findViewById(R.id.btrainSttus);
            last= itemView.findViewById(R.id.last);
            arvlMsg2= itemView.findViewById(R.id.arvlMsg2);
            recptnDt = itemView.findViewById(R.id.recptnDt);
        }
    }

}//MyTabRecyclerAdapter...
