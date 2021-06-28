package gcit.edu.keepme.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gcit.edu.keepme.MainActivity;
import gcit.edu.keepme.R;

public class mainpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
    }

    public void lohin(View view) {
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }

    public void sign(View view) {
        Intent intent=new Intent(this,registration.class);
        startActivity(intent);
    }

    public void back4(View view) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}