package com.hdk.subway;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

    String line;
    List<String> sublist;

    TextView line1, line2;
    String stationName;
    StationItem stationItem;


    String subwayId;

    String trainLineNm;
    String statnNm;
    String btrainSttus;
    String barvlDt;
    String bstatnNm;
    String recptnDt;
    String arvlMsg2;
    String arvlMsg3;
    String arvlCd;
    String subwayList;


    public TabFragment(String stationName, String line){
        this.stationName = stationName;
        this.line = line;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        line1 = v.findViewById(R.id.line1);
        line2 = v.findViewById(R.id.line2);

        recyclerView1 = v.findViewById(R.id.recyelr1);
        recyclerView2 = v.findViewById(R.id.recyelr2);

            switch(line) {
                case "01호선":
                    line = "1001";
                    break;
                case "02호선":
                    line = "1002";
                    break;
                case "03호선":
                    line = "1003";
                    break;
                case "04호선":
                    line = "1004";
                    break;
                case "05호선":
                    line = "1005";
                    break;
                case "06호선":
                    line = "1006";
                    break;
                case "07호선":
                    line = "1007";
                    break;
                case "08호선":
                    line = "1008";
                    break;
                case "09호선":
                    line = "1009";
                    break;
                case "중앙선":
                    line = "1061";
                    break;
                case "경의중앙선":
                    line = "1063";
                    break;
                case "공항철도":
                    line = "1065";
                    break;
                case "경춘선":
                    line = "1067";
                    break;
                case "수의분당선":
                    line = "1075";
                    break;
                case "신분당선":
                    line = "1077";
                    break;
                case "우이신설선":
                    line = "1092";
                    break;

            }
//        1001:1호선, 1002:2호선, 1003:3호선, 1004:4호선, 1005:5호선
//        1006:6호선, 1007:7호선, 1008:8호선, 1009:9호선, 1061:중앙선
//        1063:경의중앙선, 1065:공항철도, 1067:경춘선, 1075:수의분당선
//        1077:신분당선, 1092:우이신설선




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

                    subwayId = stationItem.getsubwayId();

                    if(line.equals(subwayId)){
                        statnNm = stationItem.getstatnNm();
                        btrainSttus = stationItem.getbtrainSttus();
                        barvlDt = stationItem.getbarvlDt();
                        bstatnNm = stationItem.getbstatnNm();
                        recptnDt = stationItem.getrecptnDt();
                        arvlMsg2 = stationItem.getarvlMsg2();
                        arvlMsg3 = stationItem.getarvlMsg3();
                        arvlCd = stationItem.getarvlCd();
                        items.add(new Item1(subwayId, trainLineNm, statnNm, btrainSttus, barvlDt, bstatnNm, recptnDt, arvlMsg2, arvlMsg3, arvlCd, subwayList));
                    }


//                    subwayList = stationItem.getsubwayList();


                }

                    getActivity().runOnUiThread(() -> {
//                        Toast.makeText(getContext(), subwayList, Toast.LENGTH_SHORT).show();

                        adapter1 = new MyTabRecyclerAdapter(getContext(), items);
                        adapter2 = new MyTabRecyclerAdapter(getContext(), items);
                        recyclerView1.setAdapter(adapter1);
                        recyclerView2.setAdapter(adapter2);
                    });



            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

}