package com.example.coronaalertapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import me.gujun.android.taggroup.TagGroup;

public class SearchActivity extends AppCompatActivity {
    EditText et_search;
    String selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et_search = findViewById(R.id.et_search);


        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(new String[]{"강남역", "이태원", "대구", "수원", "인천", "과천", "가평", "사당역", "역삼역", "홍대입구역"});


        mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                et_search.setText(tag);
                selectedLocation = tag;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.alert);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.app_bar_search:
                if (!et_search.getText().toString().equals("")) {
                    Snackbar.make(findViewById(R.id.search_layout), selectedLocation, Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SearchMapActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(R.id.search_layout), "장소를 선택하세요.", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
