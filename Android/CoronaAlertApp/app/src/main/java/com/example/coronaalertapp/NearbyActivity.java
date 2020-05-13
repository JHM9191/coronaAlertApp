package com.example.coronaalertapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.coronaalertapp.util.ConvertJSONtoVO;
import com.example.coronaalertapp.util.HttpHandler;
import com.example.coronaalertapp.util.Item;
import com.example.coronaalertapp.vo.CoronaLocationVO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

public class NearbyActivity extends AppCompatActivity {

    String TAG = "===";
    SupportMapFragment sMapFragment;
    GoogleMap gmap;
    LocationManager manager;
    boolean flag = true;
    public static ArrayList<CoronaLocationVO> maplist;
    public static String serverIP = "192.168.43.2:8080";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this,
                permissions, 101);


        String url = "http://" + serverIP + "/coronaAlert/corona.jsp";
        ItemAsync itemAsync = new ItemAsync(url);
        itemAsync.execute();


    }


    class ItemAsync extends AsyncTask<Void, Void, String> {
        String url;
        private LinkedList<Item> list;
        private String TAG = "===";

        public ItemAsync(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            result = HttpHandler.getString(url);
            Log.d(TAG, "result : " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            maplist = ConvertJSONtoVO.convertToCoronaLocation(s);
            Log.d(TAG, "maplist : " + maplist);

            final ArrayList<CoronaLocationVO> locList = maplist;

            displayMap(locList);
        }
    }

    public void displayMap(final ArrayList<CoronaLocationVO> locList) {
        sMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        sMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                LatLng[] latLngList = new LatLng[locList.size()];
                for (int i = 0; i < locList.size(); i++) {
                    LatLng latLng = new LatLng(locList.get(i).getLat(), locList.get(i).getLon());
                    gmap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(locList.get(i).getNo() + ". " + locList.get(i).getName()));
                }
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
//
                gmap.setMyLocationEnabled(true);
                manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                MyLocation myLocation = new MyLocation();
                long minTime = 1;
                float minDistance = 0;
                manager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        minTime,
                        minDistance,
                        myLocation
                );
            }
        });
    }

    class MyLocation implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            LatLng latLng = new LatLng(lat, lon);
//            gmap.addMarker(new MarkerOptions()
//                    .position(latLng));
            while(flag) {
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                flag = false;
            }
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
