package com.hdk.subway;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TabFragment extends Fragment{

    TextView colorgreen;

    Timer time;

    Handler handler = new Handler(Looper.getMainLooper());

    List<String> go = new ArrayList<>();

    ArrayList<Item1> items1 = new ArrayList<>();
    ArrayList<Item2> items2 = new ArrayList<>();

    RecyclerView recyclerView1, recyclerView2;
    MyTabRecyclerAdapter1 adapter1;
    MyTabRecyclerAdapter2 adapter2;

    String line;
    //막차..
    String last;
    //운행종료 1,2
    TextView finish1, finish2;
    //방면 1,2
    TextView line1, line2;
    String stationName;

    String subwayId;

    String trainLineNm;
    String btrainSttus;
    String recptnDt;
    String arvlMsg2;
    String arvlCd;

    public TabFragment(String stationName, String line){
        this.stationName = stationName;
        this.line = line;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        line1 = v.findViewById(R.id.line1);
        line2 = v.findViewById(R.id.line2);

        finish1 = v.findViewById(R.id.finish1);
        finish2 = v.findViewById(R.id.finish2);

        recyclerView1 = v.findViewById(R.id.recyelr1);
        recyclerView2 = v.findViewById(R.id.recyelr2);

        colorgreen = v.findViewById(R.id.arvlMsg2);

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
                case "경의선":
                    line = "1063";
                    break;
                case "공항철도":
                    line = "1065";
                    break;
                case "경춘선":
                    line = "1067";
                    break;
                case "수인분당선":
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
//        dataSubwayThread.start();
        time = new Timer();

        time.scheduleAtFixedRate(dataSubwayThread,0,10000);

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        time.cancel();
    }

    class DataSubwayThread extends TimerTask {
        // /옆에 역명 넣기
        String serverUrl = "http://swopenapi.seoul.go.kr/api/subway/416678437474616837356359705349/json/realtimeStationArrival/0/100/" + stationName;

        String[] goline2; // 2번째 방향 나타내기

        @SuppressLint("SuspiciousIndentation")
        @Override
        public void run() {
            try {

//                AssetManager assetManager = getActivity().getAssets(); // 이거
//                InputStream is = assetManager.open("json/station"); // 이거

                URL url = new URL(serverUrl);
                InputStream is = url.openStream();

                InputStreamReader isr = new InputStreamReader(is);

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(isr).getAsJsonObject();
                JsonArray realtimeArrivalList = jsonObject.getAsJsonArray("realtimeArrivalList");

                Gson gson = new Gson();

                go.clear();
                items1.clear();
                items2.clear();

                if(realtimeArrivalList != null){
                    for (JsonElement element : realtimeArrivalList) {
                        StationItem stationItem = gson.fromJson(element, StationItem.class);

                        //지하철 호선ID
                        subwayId = stationItem.getsubwayId();
                        //도착지 방면
                        trainLineNm = stationItem.gettrainLineNm();
                        //열차 종류
                        btrainSttus = stationItem.getbtrainSttus();
                        //도착메세지
                        arvlMsg2 = stationItem.getarvlMsg2();
                        //도착정보를 생성한 시각
                        recptnDt = stationItem.getrecptnDt();

                        //진입이라는 글씨는 초록색으로
//                        if(arvlMsg2.contains("진입")){
//                            colorgreen.setTextColor();
//                        }


                        //도착코드 (0:진입, 1:도착, 2:출발, 3:전역출발, 4:전역진입, 5:전역도착, 99:운행중)
                        arvlCd = stationItem.getarvlCd(); // 아직은 사용 X

                        if(line.equals(subwayId)) { //호선 분리

                            String[] str = trainLineNm.split(" - ");
                            String[] next = str[1].split("방면");
                            if(trainLineNm.contains("막차")){
                                last = "막차";
                            }
                            go.add(next[0]);

                            String finishline = str[0];

                            ///////////////////////////////////// 24시간제 -> 12시간제로 변환
                            String time = recptnDt.substring(11);
                            String[] timeParts = time.split(":"); // 시간, 분, 초를 분리
                            int hour = Integer.parseInt(timeParts[0]);
                            int minute = Integer.parseInt(timeParts[1]);
                            int second = Integer.parseInt(timeParts[2]);

                            String formattedTime;

                            if (hour < 12) {
                                formattedTime = String.format("오후 %d시 %d분 %d초", hour, minute, second);
                            } else {
                                if (hour == 12) {
                                    formattedTime = String.format("오전 %d시 %d분 %d초", hour, minute, second);
                                } else {
                                    hour -= 12;
                                    formattedTime = String.format("오후 %d시 %d분 %d초", hour, minute, second);
                                }
                            }

                            recptnDt = formattedTime;
                            ///////////////////////////////////// 24시간제 -> 12시간제로 변환

                            if(trainLineNm.contains(go.get(0))){
                                items1.add(new Item1(finishline, btrainSttus, last, arvlMsg2, recptnDt));
                            }

                            if(!trainLineNm.contains(go.get(0))){
                                items2.add(new Item2(finishline, btrainSttus, last, arvlMsg2, recptnDt));
                                String[] str1 = trainLineNm.split(" - ");
                                goline2 = str1[1].split("방면");
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(go.get(0) != null){
                                        line1.setText(go.get(0) + " 방면");
                                    }
                                    if(goline2 != null){
                                        line2.setText(goline2[0] + " 방면");
                                    }

                                }
                            });

                        }// if....

                    }// for....
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            getActivity().runOnUiThread(() -> {

                if(items1.isEmpty()){
                    startAnimation1();
                }
                if(items2.isEmpty()){
                    startAnimation2();
                }

                adapter1 = new MyTabRecyclerAdapter1(getContext(), items1);
                adapter2 = new MyTabRecyclerAdapter2(getContext(), items2);
                recyclerView1.setAdapter(adapter1);
                recyclerView2.setAdapter(adapter2);

            });


        }// Thread run...

        void startAnimation1(){
            finish1.setVisibility(View.VISIBLE);
            finish1.setAlpha(1.0f);

            // 값 애니메이터를 생성합니다.
            ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f, 1.0f);
            animator.setDuration(3000); // 애니메이션 지속 시간 (2초)
            animator.setRepeatCount(ValueAnimator.INFINITE); // 무한 반복 설정
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // 애니메이션 갱신 시마다 텍스트의 알파 값을 설정합니다.
                    float alpha = (float) animation.getAnimatedValue();
                    finish1.setAlpha(alpha);
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animation) {
                    // 애니메이션이 반복될 때마다 초기 알파 값을 설정합니다.
                    finish1.setAlpha(1.0f);
                }
            });

            animator.start(); // 애니메이션을 시작합니다.
        }

        void startAnimation2(){
            finish2.setVisibility(View.VISIBLE);
            finish2.setAlpha(1.0f);

            // 값 애니메이터를 생성합니다.
            ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f, 1.0f);
            animator.setDuration(3000); // 애니메이션 지속 시간 (2초)
            animator.setRepeatCount(ValueAnimator.INFINITE); // 무한 반복 설정
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // 애니메이션 갱신 시마다 텍스트의 알파 값을 설정합니다.
                    float alpha = (float) animation.getAnimatedValue();
                    finish2.setAlpha(alpha);
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animation) {
                    // 애니메이션이 반복될 때마다 초기 알파 값을 설정합니다.
                    finish2.setAlpha(1.0f);
                }
            });

            animator.start(); // 애니메이션을 시작합니다.
        }
    }

}