package com.gcit.todo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    public static final String LOG_TAG= MainActivity.class.getSimpleName();
    public static final String EXTRA_REPLY = "com.gcit.todo4.reply";
    private TextView textView;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textView2);
        editText = findViewById(R.id.MessageReply);

        Intent obj = getIntent();
        String msg = obj.getStringExtra(MainActivity.EXTRA_MESSAGE);
        textView.setText(msg);
    }

    public void reply(View view) {
        String message = editText.getText().toString();

        Intent Rintent = new Intent();
        Rintent.putExtra(EXTRA_REPLY, message);
        setResult(RESULT_OK, Rintent);
        finish();
    }
}