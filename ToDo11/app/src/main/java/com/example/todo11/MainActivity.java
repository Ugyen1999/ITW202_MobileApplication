 package com.example.todo11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button change;
    private String[] mColorArray = {"red","teal_200","white","black","purple_200","purple_500","pink","cyan","brown"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv1);
        change = findViewById(R.id.btn);

        if (savedInstanceState !=null){
            textView.setTextColor(savedInstanceState.getInt("color"));
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("color", textView.getCurrentTextColor());
    }

    public void change(View view) {
        Random random = new Random();
        String colorName=mColorArray[random.nextInt(20)];

        int colorResourceName = getResources().getIdentifier(colorName, "color",getApplicationContext().getPackageName());
        int colorRes = ContextCompat.getColor(this,colorResourceName);
        textView.setTextColor(colorRes);
    }
}