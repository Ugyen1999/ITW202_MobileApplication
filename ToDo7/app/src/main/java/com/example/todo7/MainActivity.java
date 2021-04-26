package com.example.todo7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int count;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
        if(savedInstanceState != null){
            count = savedInstanceState.getInt("mCount");
            mTextView.setText(String.valueOf(count));
        }
    }

    public void count(View view) {
        count++;
        mTextView.setText(String.valueOf(count));
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("mCount",count);
    }
}