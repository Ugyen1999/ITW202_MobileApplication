package com.gcit.todo_17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mScoreTextview1,mScoreTextview2;
    private int mScore1,mScore2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScoreTextview1 = findViewById(R.id.score1);
        mScoreTextview2 = findViewById(R.id.score2);
    }

    public void decrease(View view) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.Team1_decrease:
                mScore1--;
                mScoreTextview1.setText(String.valueOf(mScore1));
                break;

            case R.id.Team2_decrease:
                mScore2--;
                mScoreTextview2.setText(String.valueOf(mScore2));
        }
    }

    public void increase(View view) {
        int viewI =view.getId();
        switch (viewI){
            case R.id.Team1_increase:
                mScore1++;
                mScoreTextview1.setText(String.valueOf(mScore1));
                break;

            case R.id.Team2_increase:
                mScore2++;
                mScoreTextview2.setText(String.valueOf(mScore2));


        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nigh_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}