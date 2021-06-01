package com.example.todo_25;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText firstname, lastname, marks , Id;
    Button btnAddData;
    Button btnView;
    Button btnUpdate;
    Button btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        marks = (EditText)findViewById(R.id.marks);
        Id = (EditText)findViewById(R.id.Id);

        btnAddData = (Button)findViewById(R.id.data);
        btnView = (Button)findViewById(R.id.view);
        btnUpdate = (Button)findViewById(R.id.update);
        btnDelete = (Button)findViewById(R.id.delete);

        viewAll();
        update();
        deleteData();

    }
    public void AddData(View view) {
        boolean isInsertion = myDb.insertData(
                Id.getText().toString(),
                firstname.getText().toString(),
                lastname.getText().toString(),
                marks.getText().toString());

        if(isInsertion == true){
            Toast.makeText(MainActivity.this,"Data is inserted",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Fail to insert", Toast.LENGTH_LONG).show();
        }
    }

    public void viewAll(){
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0){
                    showMessage("ERROR","nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Student Id : "+ res.getString(0)+"\n");
                    buffer.append("First Name : "+ res.getString(1)+"\n");
                    buffer.append("Last Name : "+ res.getString(2)+"\n");
                    buffer.append("ITW202 Marks : "+ res.getString(3)+"\n\n");
                }
                //show all data
                showMessage("List of students",buffer.toString());
            }
        });
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void update(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(
                        Id.getText().toString(),
                        firstname.getText().toString(),
                        lastname.getText().toString(),
                        marks.getText().toString());

                if(isUpdate == true){
                    Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void deleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer isDelete = myDb.deleteData(
                        Id.getText().toString());

                if(isDelete > 0){
                    Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Fail to delete", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}