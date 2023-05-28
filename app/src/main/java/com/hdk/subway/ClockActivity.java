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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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


    TextView tv, testtext;
    ViewPager2 pager;
    List<String> list;
    MyPagerAdapter adapter;
    TabLayout tabbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        tabbar = findViewById(R.id.tabbar);
        tv = findViewById(R.id.tv);
        testtext = findViewById(R.id.testtext);
        list = new ArrayList<>();

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


        MyThread thread = new MyThread(stationName);
        thread.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    class MyThread extends Thread{

        String stationName;
        public MyThread(String stationName){this.stationName = stationName;};
        @Override
        public void run() {
            String apiKey = "50794a697674616839374849626e77";
            String serverUrl = "http://openapi.seoul.go.kr:8088/" +
                    apiKey + "/json/" + "SearchSTNBySubwayLineInfo/1/5/%20/" + stationName;


            try {
                URL url = new URL(serverUrl);
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(isr).getAsJsonObject();
                JsonObject searchSTNBySubwayLineInfo = jsonObject.getAsJsonObject("SearchSTNBySubwayLineInfo");
                JsonArray rowArray = searchSTNBySubwayLineInfo.getAsJsonArray("row");

                Gson gson = new Gson();
                for (JsonElement element : rowArray) {
                    StationLineNum stationLineNum = gson.fromJson(element, StationLineNum.class);

                    String lineNum = stationLineNum.getLINE_NUM();
                    list.add(lineNum);
                }
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


//                (stationLineNum.SearchSTNBySubwayLineInfo.row).forEach( str -> {
//                        list.add(stationLineNum.SearchSTNBySubwayLineInfo.row.toString());
//                    });


                    runOnUiThread(() -> {

                        testtext.setText(list.toString());
//                        pager.setAdapter(new MyPagerAdapter(list));
//                        TabLayoutMediator mediator = new TabLayoutMediator(tabbar, pager, ((tab, position) -> tab.setText(list.get(position)) ));
//
//                        mediator.attach();
                    });


        }
    }

}// ClockActivity class...