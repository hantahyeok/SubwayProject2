package com.hdk.subway;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    ArrayList<Item> items = new ArrayList<>();
    ArrayList<StationItem> stationItems2 = new ArrayList<>();
    RecyclerView recyclerView1, recyclerView2;
    MyTabRecyclerAdapter adapter1, adapter2;

    TextView tv1, tv2;
    String stationName;

    List<String> list;

    public TabFragment(String stationName){
        this.stationName = stationName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        tv1 = v.findViewById(R.id.tv1);
        tv2 = v.findViewById(R.id.tv2);

        recyclerView1 = v.findViewById(R.id.recyelr1);
        recyclerView2 = v.findViewById(R.id.recyelr2);

        list = new ArrayList<>();


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

                    items.add(new Item(subwayId, trainLineNm, statnNm, btrainSttus, barvlDt, bstatnNm, recptnDt, arvlMsg2, arvlMsg3, arvlCd));

//                    stationItems1.add(new StationItem());




                    getActivity().runOnUiThread(() -> {
                        tv1.setText(list.toString());

                        adapter1 = new MyTabRecyclerAdapter(getContext(), items);
//                        adapter2 = new MyTabRecyclerAdapter(getContext(), stationItems2);
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