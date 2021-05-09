package com.example.todo_12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ImageView donut, icecream, froyo;
    private FloatingActionButton shoppingcart;
    String msg1;
    public static final String EXTRA_MESSAGE = " com.example.todo_12.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        donut = findViewById(R.id.imageView);
        icecream = findViewById(R.id.imageView2);
        froyo = findViewById(R.id.imageView3);
        shoppingcart = findViewById(R.id.floatingActionButton);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_1:
                Intent intent = new Intent(this, MainActivity2.class);
                intent.putExtra(EXTRA_MESSAGE, msg1);
                startActivity(intent);
                return true;

            case R.id.item_2:
                Toast.makeText(this, "You have selected Favorites", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item_3:
                Toast.makeText(this, "You have selected Place Order", Toast.LENGTH_SHORT).show();
                return true;

            default:
                break;

        }
        return false;
    }

    public void Donut(View view) {
        String msg = "You have ordered Donut!";
        msg1 = msg;
        Toast.makeText(this, "You have ordered Donut!", Toast.LENGTH_SHORT).show();
    }

    public void Icecream(View view) {
        String msg = "You have ordered Ice Cream!";
        msg1 = msg;
        Toast.makeText(this, "You have ordered Ice Cream!", Toast.LENGTH_SHORT).show();
    }

    public void Froyo(View view) {
        String msg = "You have ordered Froyo!";
        msg1 = msg;
        Toast.makeText(this, "You have ordered Froyo!", Toast.LENGTH_SHORT).show();
    }

    public void shoppingcart(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("msg1",msg1);
        startActivity(intent);
    }
}