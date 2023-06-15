package com.hdk.subway;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView);
        imageView.setImage(ImageSource.resource(R.drawable.subwayway));
//        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);
//        imageView.setMinScale(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);

        imageView.setMaxScale(4f);

        Cursor c;
        SubwayDatabaseHelper myDbHelper = new SubwayDatabaseHelper(MainActivity.this); // Reading SQLite database.
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        c = myDbHelper.query("station", null, null, null, null, null, null); // SQLDataRead


        //Refer to ID value.
        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() { // gesture 디텍팅으로 지하철 위치 읽기
            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                if (imageView.isReady()) {

                    PointF sCoord = imageView.viewToSourceCoord(event.getX(), event.getY());

                    imageView.animateCenter(new PointF(sCoord.x, sCoord.y)).start();


                    int x_cor = (int) sCoord.x;
                    int y_cor = (int) sCoord.y;

                    // Loop for finding the station.
                    if (c.moveToFirst()) {
                        do {

                            if ((x_cor > c.getInt(2)) && (x_cor < c.getInt(4)) && (y_cor > c.getInt(3)) && (y_cor < c.getInt(5))) {
                                String targetStation = c.getString(1); // 유저가 클릭한 지하철역
                                findViewById(R.id.clickFragment).setVisibility(View.VISIBLE);

                                MySingleton singleton = MySingleton.getInstance();
                                singleton.setData(targetStation);

                                //화면 클릭시 좌표를 중심점으로 이동
                                PointF center = imageView.viewToSourceCoord(event.getX(), event.getY());
                                imageView.animateCenter(new PointF(center.x, center.y)).start();

                                Toast.makeText(MainActivity.this, targetStation, Toast.LENGTH_SHORT).show();
                            } // send Station Name (column 1)

                        } while (c.moveToNext());

                    }

                    /////////////////////////////////////////////////////////////////////////////
                }


                return super.onSingleTapUp(event);
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                imageView.set
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        findViewById(R.id.clickFragment).setVisibility(View.INVISIBLE);
                        break;
                }
                return gestureDetector.onTouchEvent(event);
            }
        });


    }// onCreate...
}