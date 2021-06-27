package com.example.creditbook;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static java.lang.Thread.sleep;

public class splashpage extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3100;
    Animation topAnime,bottomAnime;
    private ImageView imageView;
    private TextView mlogoname;
    private TextView mtext;
    private TextView mtext2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashpage);

        topAnime = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnime = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        imageView =findViewById(R.id.splash);
        mlogoname= findViewById(R.id.gcit);
        mtext = findViewById(R.id.textView9);
        mtext2 =findViewById(R.id.textView10);

        imageView.setAnimation(topAnime);
        mlogoname.setAnimation(topAnime);
        mtext.setAnimation(bottomAnime);
        mtext2.setAnimation(bottomAnime);

        RelativeLayout relativeLayout = findViewById(R.id.splashlayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashpage.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_SCREEN);

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    sleep(3000);
//                    Intent i = new Intent(splashpage.this, MainActivity.class);
//                    startActivity(i);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
    }
}
