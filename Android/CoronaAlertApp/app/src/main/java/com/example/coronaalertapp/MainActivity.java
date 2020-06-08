package com.example.coronaalertapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.coronaalertapp.util.ApiExplorer;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

public class MainActivity extends AppCompatActivity {

    String TAG = "===";

    LabeledSwitch labeledSwitch;

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


        Point device = new Point();
        getWindowManager().getDefaultDisplay().getSize(device);
        Log.d(TAG, "device.x : " + device.x);
        // gif corona
        ImageView iv_gif_corona = findViewById(R.id.iv_gif_corona);
        Glide.with(this).load(R.raw.corona).into(iv_gif_corona);

        new Thread(new GifAnimationThread(iv_gif_corona, device.x + 450f)).start();

        // gif running man
        ImageView iv_gif_runningman = findViewById(R.id.iv_gif_runningman);
        Glide.with(this).load(R.raw.runningman2).into(iv_gif_runningman);
        new Thread(new GifAnimationThread(iv_gif_runningman, device.x + 400f)).start();


        // alert service

        Intent serviceIntent = new Intent(getApplicationContext(), CoronaLocationNearAlertService.class);
//        serviceIntent.putExtra("mapList", mapList);
//        startService(serviceIntent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_main_alert_toggle, menu);
        MenuItem switchOnOffItem = menu.findItem(R.id.switchId);
        switchOnOffItem.setActionView(R.layout.layout_alert_toggle);

        // alert toggle
        labeledSwitch = switchOnOffItem.getActionView().findViewById(R.id.alert_toggle);
        labeledSwitch.setOn(true);
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {

            }
        });


        return true;
    }

    class GifAnimationThread implements Runnable {
        ImageView iv_gif;
        float x;

        public GifAnimationThread() {

        }

        public GifAnimationThread(ImageView iv_gif, float x) {
            this.iv_gif = iv_gif;
            this.x = x;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Entered while()");
                    ObjectAnimator animation = ObjectAnimator.ofFloat(iv_gif, "translationX", x);
                    animation.setDuration(12000);
                    animation.start();
                    animation.setRepeatCount(-1);
                }
            });


        }
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
