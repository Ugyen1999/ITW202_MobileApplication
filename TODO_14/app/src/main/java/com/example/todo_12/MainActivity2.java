package com.example.todo_12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RadioButton Sameday, Nextday, Pickup;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Sameday = (RadioButton) findViewById(R.id.radioButton);
        Nextday = (RadioButton) findViewById(R.id.radioButton2);
        Pickup = (RadioButton) findViewById(R.id.radioButton3);
        textview = (TextView) findViewById(R.id.textView5);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg1");
        textview.setText(msg);

        Spinner spinner = findViewById(R.id.spinner);
        //call setOnItemSelectedListener if spinner is not null
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        //Displaying in the XML file
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.labels_array, android.R.layout.simple_dropdown_item_1line);
        if (spinner != null) {
            spinner.setAdapter(arrayAdapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String message = parent.getItemAtPosition(position).toString();
        displayToast(message);

    }
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        /////

    }
    public void RadioButton(View view) {
        boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButton:
                Toast.makeText(this, "Same day messenger service", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButton2:
                Toast.makeText(this, "Next day ground delivery", Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButton3:
                Toast.makeText(this, "Pick up", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}