package com.hdk.subway;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {

//  csv list..
    List<String[]> dataList; //전체 데이터양
    List<String> SUBWAY_ID = new ArrayList<>(); //data[0]
    List<String> STATN_ID = new ArrayList<>();  //data[1]
    List<String> STATN_NM = new ArrayList<>();  //data[2]
    List<String> lineName = new ArrayList<>();//data[3]
//    String subway_id;
//    String statn_id;
//    String statn_nm;
//    String line_name;

    String tvline1, tvline2;

    ArrayList<Item1> items1 = new ArrayList<>();
    ArrayList<Item2> items2 = new ArrayList<>();

    RecyclerView recyclerView1, recyclerView2;
    MyTabRecyclerAdapter1 adapter1;
    MyTabRecyclerAdapter2 adapter2;

    String line;

    TextView line1, line2;
    String stationName;

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
    String statnFid;
    String statnTid;


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

            }// switch..
//        1001:1호선, 1002:2호선, 1003:3호선, 1004:4호선, 1005:5호선
//        1006:6호선, 1007:7호선, 1008:8호선, 1009:9호선, 1061:중앙선
//        1063:경의중앙선, 1065:공항철도, 1067:경춘선, 1075:수의분당선
//        1077:신분당선, 1092:우이신설선

//        AssetManager assetManager = getActivity().getAssets(); // 사용 x


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
                AssetManager assetManager = getActivity().getAssets(); // 이거

                try {
                    InputStream inputStream = assetManager.open("StationCode.csv");
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    dataList = new ArrayList<>();

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] data = line.split(",");
                        dataList.add(data);
                    }

                    bufferedReader.close();

                    for(String[] data : dataList){
//                SUBWAY_ID.add(data[0]);
                        STATN_ID.add(data[1]);
                        STATN_NM.add(data[2]);
//                lineName.add(data[3]);

                    }

                } catch (Exception e){
                    e.printStackTrace();
                }

//                URL url = new URL(serverUrl);
//                InputStream is = url.openStream();
                InputStream is = assetManager.open("json/station"); // 이거

                InputStreamReader isr = new InputStreamReader(is);

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(isr).getAsJsonObject();
                JsonArray realtimeArrivalList = jsonObject.getAsJsonArray("realtimeArrivalList");

                Gson gson = new Gson();

                Handler handler = new Handler(Looper.getMainLooper());

                for (JsonElement element : realtimeArrivalList) {
                    StationItem stationItem = gson.fromJson(element, StationItem.class);

                    subwayId = stationItem.getsubwayId();

                    statnFid = stationItem.getstatnFid();
                    statnTid = stationItem.getstatnTid();

                    if(line.equals(subwayId)) {
                            statnNm = stationItem.getstatnNm();
                            btrainSttus = stationItem.getbtrainSttus();
                            barvlDt = stationItem.getbarvlDt();
                            bstatnNm = stationItem.getbstatnNm();
                            recptnDt = stationItem.getrecptnDt();
                            arvlMsg2 = stationItem.getarvlMsg2();
                            arvlMsg3 = stationItem.getarvlMsg3();
                            arvlCd = stationItem.getarvlCd();

//                        if(true){
//                            items1.add(new Item1(trainLineNm, statnNm, btrainSttus, barvlDt, bstatnNm, recptnDt, arvlMsg2, arvlMsg3, arvlCd, subwayList));
//                        }
//                        if(true){
//                            items2.add(new Item2(trainLineNm, statnNm, btrainSttus, barvlDt, bstatnNm, recptnDt, arvlMsg2, arvlMsg3, arvlCd, subwayList));
//                        }

                        for(int i = 0; i < dataList.size(); i++){
                                if(STATN_ID.get(i).equals(statnFid)){
                                    tvline1 = STATN_NM.get(i);
                                }
                                if(STATN_ID.get(i).equals(statnTid)){
                                    tvline2 = STATN_NM.get(i);
                                }
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    line1.setText(tvline1 + " 방향");
                                    line2.setText(tvline2 + " 방향");
                                }
                            });


                    }// if....

                }// for....

                    getActivity().runOnUiThread(() -> {
//                        Toast.makeText(getContext(), subwayList, Toast.LENGTH_SHORT).show();
                        adapter1 = new MyTabRecyclerAdapter1(getContext(), items1);
                        adapter2 = new MyTabRecyclerAdapter2(getContext(), items2);
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