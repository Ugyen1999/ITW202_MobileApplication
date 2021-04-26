 package com.example.todo_8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mwebpageopen ,locationopen,sharetext;
    private Button websitebutton, locationbutton, sharetextbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mwebpageopen = findViewById(R.id.et1);
        locationopen = findViewById(R.id.et2);
        sharetext = findViewById((R.id.et3));

        websitebutton = findViewById(R.id.btn1);
        locationbutton = findViewById(R.id.btn2);
        sharetextbutton = findViewById(R.id.btn3);

    }

    public void openwebsite(View view) {
        String text = mwebpageopen.getText().toString();
        Uri url=Uri.parse(text);
        Intent intent = new Intent(Intent.ACTION_VIEW,url);

        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
        else{
            Log.d("Implicit intent","error");
        }
    }
    public void openlocation(View view) {
        String text2 = locationopen.getText().toString();
        Uri location = Uri.parse("geo:0,07q="+text2);
        Intent intent = new Intent(Intent.ACTION_VIEW,location);

        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
        else{
            Log.d("Implicit intent","error");
        }
    }

    public void sharetext(View view) {
        String text3 = sharetext.getText().toString();

        String mimeType="text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("share this text with: ")
                .setText(text3)
                .startChooser();
    }
}