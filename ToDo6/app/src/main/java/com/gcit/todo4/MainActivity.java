package com.gcit.todo4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG=MainActivity.class.getSimpleName();
    public final static String EXTRA_MESSAGE = "com.gcit.todo4.MESSAGE";
    private static final int TEXT_REQUEST = 1;
    private EditText editText;
    private TextView Tv1;
    private TextView Tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"=======");
        Log.d(LOG_TAG,"onCreate");
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText1);
        Tv1 = findViewById(R.id.Received_Reply);
        Tv2 = findViewById(R.id.Replied_Message);
        if(savedInstanceState!=null){
            boolean isVisible=savedInstanceState.getBoolean("reply_state");
            if(isVisible){
                Tv1.setVisibility(View.VISIBLE);
                Tv2.setText(savedInstanceState.getString("reply_message"));
                Tv2.setVisibility(View.VISIBLE);

            }
        }
    }

    public void SEND(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivityForResult(intent,TEXT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent obj) {
        super.onActivityResult(requestCode, resultCode, obj);
        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_OK){
                String message = obj.getStringExtra(MainActivity2.EXTRA_REPLY);
                Tv1.setVisibility(View.VISIBLE);
                Tv2.setText(message);
                Tv2.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(Tv1.getVisibility()==View.VISIBLE);
        outState.putBoolean("reply_state",true);
        outState.putString("reply_message",Tv2.getText().toString());
    }
}