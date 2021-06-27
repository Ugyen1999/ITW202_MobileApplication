package com.example.creditbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        TextView textView = (TextView) findViewById(R.id.more1);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textView1 = (TextView) findViewById(R.id.more2);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textView2 = (TextView) findViewById(R.id.more3);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textView3 = (TextView) findViewById(R.id.more4);
        textView3.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textView4 = (TextView) findViewById(R.id.more5);
        textView4.setMovementMethod(LinkMovementMethod.getInstance());

    }

}



