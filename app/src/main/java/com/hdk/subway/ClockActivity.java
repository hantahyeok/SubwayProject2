package com.hdk.subway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class ClockActivity extends AppCompatActivity {

    TextView tv;
    String result;

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
        String apiKey = "50794a697674616839374849626e77";
        String serverUrl = "http://openapi.seoul.go.kr:8088/" +
                apiKey + "/json/" + "SearchSTNBySubwayLineInfo/1/5/%20/" + stationName;

        try{
            URL url = new URL(serverUrl);

            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null){
                buffer.append(line + "\n");
                line = reader.readLine();
            }

            String jsonData = buffer.toString();

            Toast.makeText(this, "jasonData", Toast.LENGTH_SHORT).show();


        }catch(Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}