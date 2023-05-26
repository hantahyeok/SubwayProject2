package com.hdk.subway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClockActivity extends AppCompatActivity {

    TextView tv;
    SubwayData SubwayData;
    ArrayList<String> arraylist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        tv = findViewById(R.id.tv);

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

        stationNum(stationName);

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
                    SubwayData = gson.fromJson(isr, SubwayData.class);



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