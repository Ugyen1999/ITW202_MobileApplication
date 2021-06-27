package com.example.creditbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ChangePassword extends AppCompatActivity {
    EditText curPsd, newPsd, conPsd;
    Button save, cancel;
    private DatabaseReference reference;
    private String s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        curPsd = findViewById(R.id.edit_current);
        newPsd = findViewById(R.id.edit_newpassword);
        conPsd = findViewById(R.id.edit_conpassword);
        save = findViewById(R.id.btn_save);
        cancel = findViewById(R.id.btn_cancel);

        String userID = getIntent().getStringExtra("userid");

        s1 = userID;
        System.out.println("SSSSSS " +s1);
//        Log.d("passd", s1);

    }

    public void changePassword(View view) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String oldPasswordFromUser = curPsd.getText().toString().trim();
        String newPasswordFormUser = newPsd.getText().toString().trim();
        reference = FirebaseDatabase.getInstance().getReference("Customer");
        Query checkUser = reference.orderByChild("customerid").equalTo(s1);


        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String oldPasswordDB = snapshot.child(s1).child("customerpassword").getValue(String.class);
                    if(oldPasswordDB.equals(oldPasswordFromUser)){

                        curPsd.setError(null);
                        curPsd.setEnabled(false);

                        if(!validatePassword()){
                            progressDialog.dismiss();
                            return;
                        }
                        else{
                            progressDialog.dismiss();
                            reference.child(s1).child("customerpassword").setValue(newPasswordFormUser);
                            Toast.makeText(getApplicationContext(), "Password Updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    else{
                        progressDialog.dismiss();
                        curPsd.setError("Wrong password");
                        curPsd.requestFocus();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"No such User",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }
    private boolean validatePassword(){
        String val = newPsd.getText().toString().trim();
        String val1 = conPsd.getText().toString().trim();
//        final Pattern PASSWORD_PATTERN =
//                Pattern.compile("^" +
//                        "(?=.*[0-9])" +         //at least 1 digit
//                        "(?=.*[a-z])" +         //at least 1 lower case letter
//                        "(?=.*[A-Z])" +         //at least 1 upper case letter
//                        ".{4,}" +               //at least 4 characters
//                        "$");
        if(val.isEmpty()){
            newPsd.setError("Password is Empty");
            newPsd.requestFocus();
            return false;
        }
        else if(val1.isEmpty()){
            conPsd.setError("Confirm Password is Empty");
            conPsd.requestFocus();
            return false;
        }
        else if(!val.isEmpty() && val1.isEmpty()){
            conPsd.setError("Confirm Password is Empty");
            conPsd.requestFocus();
            return false;
        }
        else if(val.length() < 6) {
            newPsd.setError("Password is too short, it should be more than 6 digits");
            newPsd.requestFocus();
            return false;
        }
        else if(!val.equals(val1)){
            conPsd.setError("Confirm Password is didn't match");
            conPsd.requestFocus();
            return false;
        }
        return true;
    }

    public void cancel(View view) {
        Intent intent = new Intent(this,ChangePassword.class);
        startActivity(intent);
        finish();
    }
}

