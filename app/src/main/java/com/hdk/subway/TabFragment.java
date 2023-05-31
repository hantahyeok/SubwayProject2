package com.hdk.subway;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment {

    TextView tv1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        tv1 = v.findViewById(R.id.tv1);
//        http://swopenapi.seoul.go.kr/api/subway/416678437474616837356359705349/json/realtimeStationArrival/0/1000/%EC%99%95%EC%8B%AD%EB%A6%AC

        DataSubwayThread dataSubwayThread = new DataSubwayThread();
        dataSubwayThread.start();

        return v;
    }

    class DataSubwayThread extends Thread{

        // /옆에 역명 넣기
        String serverUrl = "http://swopenapi.seoul.go.kr/api/subway/416678437474616837356359705349/json/realtimeStationArrival/0/1000/";
        @Override
        public void run() {
        }
    }

}