package com.hdk.subway;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ClickFragment extends Fragment {

    LinearLayout clock, exit, information, favorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_click, container, false);

        clock = v.findViewById(R.id.clock);
//        exit = v.findViewById(R.id.exit);
//        information = v.findViewById(R.id.information);
//        favorite = v.findViewById(R.id.favorite);

        clock();
//        exit();
//        information();
//        favorite();


        return v;
    }

    void clock(){
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClockActivity.class);
                startActivity(intent);
            }
        });
    }

    void exit(){
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void information(){
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void favorite(){
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}