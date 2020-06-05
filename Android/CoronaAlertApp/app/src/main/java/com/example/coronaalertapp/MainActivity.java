package com.example.coronaalertapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.coronaalertapp.util.ApiExplorer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    CoronaLocationNearAlertService myService;
    ServiceConnection sconn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CoronaLocationNearAlertService.MyBinder mb = (CoronaLocationNearAlertService.MyBinder) service;
            myService = mb.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

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
