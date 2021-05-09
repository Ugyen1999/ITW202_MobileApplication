package com.example.to_do12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static final String LOG = MainActivity.class.getName();
    FloatingActionButton floatingActionButton;
    String list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.callFloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrderdActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
            }
        });
    }

    public void donut(View view) {
        String donut = "You ordered a donut";
        list = donut;
        Toast.makeText(this,"You ordered a donut",Toast.LENGTH_SHORT).show();
    }

    public void ice_cream(View view) {
        String ice = "You ordered a ice cream";
        list = ice;
        Toast.makeText(this,"You ordered a ice cream",Toast.LENGTH_SHORT).show();
    }

    public void froyo(View view) {
        String froyo = "You ordered a froyo";
        list = froyo;
        Toast.makeText(this,"You ordered a froyo",Toast.LENGTH_SHORT).show();
    }
}