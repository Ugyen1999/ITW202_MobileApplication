package edu.gcit.todo_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    public static final String EXTRA_REPLY = "edu.gcit.todo_4_message";
    private TextView mText;
    private EditText nReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nReply = findViewById(R.id.editTextMessage2);
        Intent intent = getIntent();
        mText = findViewById(R.id.textDisplayin2);
        String message = intent.getStringExtra(edu.gcit.todo_4.MainActivity.EXTRA_MESSAGE);
        mText.setText(message);
    }

    public void reply(View view) {
        String message = nReply.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_REPLY,message);
        setResult(RESULT_OK,intent);
        finish();
    }
}