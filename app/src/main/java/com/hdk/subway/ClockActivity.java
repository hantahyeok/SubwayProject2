package com.hdk.subway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClockActivity extends AppCompatActivity {

    TextView tv, t;
    ViewPager2 pager;
    StationLineNum stationLineNum;
    List<String> list;
    MyPagerAdapter adapter;
    TabLayout tabbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        tabbar = findViewById(R.id.tabbar);
        tv = findViewById(R.id.tv);
        t = findViewById(R.id.t);
//        pager = findViewById(R.id.pager);
//        adapter = new MyPagerAdapter(list);
//        pager.setAdapter(adapter);

        MySingleton singleton = MySingleton.getInstance();
        String stationName = singleton.getData();
        tv.setText(stationName);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        stationNum(stationName);

    }

    void stationNum(String stationName){

        new Thread(){
            @Override
            public void run() {
                String apiKey = "50794a697674616839374849626e77";
                String serverUrl = "http://openapi.seoul.go.kr:8088/" +
                        apiKey + "/json/" + "SearchSTNBySubwayLineInfo/1/5/%20/" + stationName;

                try {
                    URL url = new URL(serverUrl);
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    Gson gson = new Gson();
                    stationLineNum = gson.fromJson(isr, StationLineNum.class);
//
////                    (stationLineNum.SearchSTNBySubwayLineInfo.row).forEach( str -> {
////                        list.add(stationLineNum.SearchSTNBySubwayLineInfo.row.toString());
////                    });

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> t.setText("새로운 텍스트"));
//
                    runOnUiThread(() -> {

                        Toast.makeText(ClockActivity.this, "HHHH", Toast.LENGTH_SHORT).show();

////                        pager.setAdapter(new MyPagerAdapter(list));
////                        TabLayoutMediator mediator = new TabLayoutMediator(tabbar, pager, ((tab, position) -> tab.setText(list.get(position)) ));
////
////                        mediator.attach();
                    });
//

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}