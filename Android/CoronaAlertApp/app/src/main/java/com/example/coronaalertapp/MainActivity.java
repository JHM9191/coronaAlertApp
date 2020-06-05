package com.example.coronaalertapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coronaalertapp.util.ApiExplorer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Thread(new ApiExplorer(getString(R.string.corona_kr_api_key))).start();


    }


    public void onImgClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_nearby:
                intent = new Intent(this, NearbyActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_search:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_stat:
                intent = new Intent(this, StatisticsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_diet:
                break;
        }

    }
}
