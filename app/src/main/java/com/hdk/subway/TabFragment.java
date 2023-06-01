package com.hdk.subway;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {

    ArrayList<Item1> items = new ArrayList<>();
    ArrayList<StationItem> stationItems2 = new ArrayList<>();
    RecyclerView recyclerView1, recyclerView2;
    MyTabRecyclerAdapter adapter1, adapter2;

    List<String> list;
    String subwaylist = "";

    TextView tv1, tv2;
    String stationName;


    public TabFragment(String stationName, List<String> list){
        this.stationName = stationName;
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        tv1 = v.findViewById(R.id.tv1);
        tv2 = v.findViewById(R.id.tv2);

        recyclerView1 = v.findViewById(R.id.recyelr1);
        recyclerView2 = v.findViewById(R.id.recyelr2);

//        for(int i = 0; i < list.size(); i++){
//            switch(list.get(i)){
//                case "01호선":
//                    subwaylist = "1001";
//                    break;
//                case "02호선":
//                    subwaylist = "1002";
//                    break;
//                case "03호선":
//                    subwaylist = "1003";
//                    break;
//                case "04호선":
//                    subwaylist = "1004";
//                    break;
//                case "05호선":
//                    subwaylist = "1005";
//                    break;
//                case "06호선":
//                    subwaylist = "1006";
//                    break;
//                case "07호선":
//                    subwaylist = "1007";
//                    break;
//                case "08호선":
//                    subwaylist = "1008";
//                    break;
//                case "09호선":
//                    subwaylist = "1009";
//                    break;
//
//
//            }
//
//        }
//        1001:1호선, 1002:2호선, 1003:3호선, 1004:4호선, 1005:5호선
//        1006:6호선, 1007:7호선, 1008:8호선, 1009:9호선, 1061:중앙선
//        1063:경의중앙선, 1065:공항철도, 1067:경춘선, 1075:수의분당선
//        1077:신분당선, 1092:우이신설선



        tv2.setText(list.get(1));

        DataSubwayThread dataSubwayThread = new DataSubwayThread();
        dataSubwayThread.start();

        return v;
    }

    class DataSubwayThread extends Thread{

        // /옆에 역명 넣기
        String serverUrl = "http://swopenapi.seoul.go.kr/api/subway/416678437474616837356359705349/json/realtimeStationArrival/0/100/" + stationName;

        @Override
        public void run() {
            try {
                URL url = new URL(serverUrl);
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(isr).getAsJsonObject();
                JsonArray realtimeArrivalList = jsonObject.getAsJsonArray("realtimeArrivalList");

                Gson gson = new Gson();

                for (JsonElement element : realtimeArrivalList) {
                    StationItem stationItem = gson.fromJson(element, StationItem.class);

                    String subwayId = stationItem.getsubwayId();

                    String trainLineNm = stationItem.gettrainLineNm();
                    String statnNm = stationItem.getstatnNm();
                    String btrainSttus = stationItem.getbtrainSttus();
                    String barvlDt = stationItem.getbarvlDt();
                    String bstatnNm = stationItem.getbstatnNm();
                    String recptnDt = stationItem.getrecptnDt();
                    String arvlMsg2 = stationItem.getarvlMsg2();
                    String arvlMsg3 = stationItem.getarvlMsg3();
                    String arvlCd = stationItem.getarvlCd();
                    String subwayList = stationItem.getsubwayList();

                    list.get(0);

                    if(subwayId.equals()){

                    }

                    items.add(new Item1(trainLineNm, statnNm, btrainSttus, barvlDt, bstatnNm, recptnDt, arvlMsg2, arvlMsg3, arvlCd, subwayList));

//                    stationItems1.add(new StationItem());




                    getActivity().runOnUiThread(() -> {
                        tv1.setText(bstatnNm);

                        adapter1 = new MyTabRecyclerAdapter(getContext(), items);
                        adapter2 = new MyTabRecyclerAdapter(getContext(), items);
                        recyclerView1.setAdapter(adapter1);
                        recyclerView2.setAdapter(adapter2);
                    });
                }



            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

}