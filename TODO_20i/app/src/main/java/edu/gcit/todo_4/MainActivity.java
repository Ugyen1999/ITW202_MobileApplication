package edu.gcit.todo_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "edu.gcit.todo_4_message";
    public static final int TEXT_REQUEST = 1;
    private EditText nmessageEditText;
    private TextView nDisplayHeader;
    private TextView nReplyDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nmessageEditText = findViewById(R.id.editTextMessage1);
        nDisplayHeader = findViewById(R.id.replyReceivedMainactivity);
        nReplyDisplay = findViewById(R.id.textDisplayin1);

    }

    public void send(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        String message = nmessageEditText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivityForResult(intent, TEXT_REQUEST);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_OK){
                String reply = data.getStringExtra(MainActivity2.EXTRA_REPLY);
                nDisplayHeader.setVisibility(View.VISIBLE);
                nReplyDisplay.setText(reply);
                nReplyDisplay.setVisibility(View.VISIBLE);
            }
        }
    }



}