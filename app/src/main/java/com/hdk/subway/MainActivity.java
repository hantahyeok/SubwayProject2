package com.hdk.subway;

import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP;
import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE;
import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.SCALE_TYPE_CUSTOM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    boolean click = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tv);
        Animation tranlateinAnim;
        Animation tranlateoutAnim;

        LinearLayout li = findViewById(R.id.li);

        //anim 폴더의 애니메이션을 가져와서 준비
        tranlateinAnim = AnimationUtils.loadAnimation(this,R.anim.up_in);
        tranlateoutAnim = AnimationUtils.loadAnimation(this,R.anim.up_out);

        //페이지 슬라이딩 이벤트가 발생했을때 애니메이션이 시작 됐는지 종료 됐는지 감지할 수 있다.
        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();

//        tranlateinAnim.setAnimationListener(animListener);
        tranlateoutAnim.setAnimationListener(animListener);

//        //페이지 슬라이딩 이벤트가 발생했을때 애니메이션이 시작 됐는지 종료 됐는지 감지할 수 있다.
//        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
//
//        tranlateLeftAnim.setAnimationListener(animListener);
//        tranlateRightAnim.setAnimationListener(animListener);

//        ExtendedFloatingActionButton fab = findViewById(R.id.fab);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, SearchActivity.class);
//                startActivity(i);
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//            }
//        });

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView);
        imageView.setImage(ImageSource.resource(R.drawable.subwayway));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        imageView.setOnImageEventListener(new SubsamplingScaleImageView.OnImageEventListener() {
                            @Override
                            public void onReady() {
                                imageView.setMinimumScaleType(SCALE_TYPE_CENTER_CROP);
                                imageView.setMaxScale(4f);

                            }

                            @Override
                            public void onImageLoaded() {
                                imageView.setMinimumScaleType(SCALE_TYPE_CENTER_CROP);
                                imageView.setMaxScale(4f);
                            }

                            @Override
                            public void onPreviewLoadError(Exception e) {

                            }

                            @Override
                            public void onImageLoadError(Exception e) {

                            }

                            @Override
                            public void onTileLoadError(Exception e) {

                            }

                            @Override
                            public void onPreviewReleased() {

                            }
                        });
                    }
                });
            }
        });
        thread.start();



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
//                                PointF center = imageView.viewToSourceCoord(event.getX(), event.getY());
//                                imageView.animateCenter(new PointF(center.x, center.y)).withDuration(500).start();
                                imageView.animateCenter(new PointF(sCoord.x, sCoord.y))
                                        .withDuration(500)
                                        .withEasing(SubsamplingScaleImageView.EASE_OUT_QUAD)
                                        .withInterruptible(false)
                                        .start();

                                // 선택한 역명 애니메이션 작업
                                tv.setText(c.getString(1));
                                li.setVisibility(View.VISIBLE);
                                li.startAnimation(tranlateinAnim);
                                click = true;


//                                Toast.makeText(MainActivity.this, targetStation, Toast.LENGTH_SHORT).show();
                                // send Station Name (column 1)
                            }

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
                        if(click == true){
                            li.startAnimation(tranlateoutAnim);
                            click = false;
                        }

                        break;
                }


                return gestureDetector.onTouchEvent(event);
            }
        });


    }// onCreate...


    private class SlidingPageAnimationListener implements Animation.AnimationListener{

        LinearLayout li = findViewById(R.id.li);

        @Override
        public void onAnimationStart(Animation animation) {

        }

        public void onAnimationEnd(Animation animation){
//            if(isPageOpen){
//                page.setVisibility(View.INVISIBLE);
//
//                button.setText("열기");
//                isPageOpen = false;
//            }else{
//                button.setText("닫기");
//                isPageOpen = true;
//            }
            li.setVisibility(View.INVISIBLE);

        }


        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


}