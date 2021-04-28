package com.example.todo_9;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private TextView ResultView;

    private calculator CalculateOperation;
    private static final String TAG = "Calculator.getSimpleName()";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalculateOperation = new calculator();
        editText1 = findViewById(R.id.et1);
        editText2 = findViewById(R.id.et2);
        ResultView = findViewById(R.id.tv1);

    }

    public void add(View view) {
        String operandOne = editText1.getText().toString();
        String operandTwo = editText2.getText().toString();

        double Total_number =CalculateOperation.add(Double.valueOf(operandOne),Double.valueOf(operandTwo));
        ResultView.setText(String.valueOf(Total_number));


    }

    public void sub(View view) {
        String operandOne = editText1.getText().toString();
        String operandTwo = editText2.getText().toString();

        double Total_value = CalculateOperation.sub(Double.valueOf(operandOne),Double.valueOf(operandTwo));
        ResultView.setText(String.valueOf(Total_value));

    }

    public void mul(View view) {
        String operandOne = editText1.getText().toString();
        String operandTwo = editText2.getText().toString();

        double Total_value =CalculateOperation.mul(Double.valueOf(operandOne),Double.valueOf(operandTwo));
        ResultView.setText(String.valueOf(Total_value));

    }

    public void div(View view) {
        String operandOne = editText1.getText().toString();
        String operandTwo = editText2.getText().toString();

        double value =CalculateOperation.div(Double.valueOf(operandOne),Double.valueOf(operandTwo));
        ResultView.setText(String.valueOf(value));

    }
}