package com.hdk.subway;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar tb = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(tb);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

    }

    void goToMainActivity(){;
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}