package com.example.creditbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class
MainActivity extends AppCompatActivity {
    EditText mId, mPassword;
    FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(MainActivity.this);


        mId = (EditText) findViewById(R.id.id);
        mPassword = (EditText) findViewById(R.id.password);
    }

    public void callHomePage(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        if (!validatecustomerid() | !validatePassword()) {
            progressDialog.dismiss();
            return;
        }
        //Admin Condition
        else {
            int countNo = mId.length();
            if(countNo == 7){
                String id = mId.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
                Query checkUser = databaseReference.orderByChild("adminliscene").equalTo(id);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            mId.setError(null);
                            mId.setEnabled(false);
                            String passwordDB = snapshot.child(id).child("adminpassword").getValue(String.class);
                            if (passwordDB.equals(password)) {
                                mPassword.setError(null);
                                mPassword.setEnabled(false);
                                progressDialog.dismiss();
                                String adminlicesneDB = snapshot.child(id).child("adminliscene").getValue(String.class);
                                Intent intent = new Intent(getApplicationContext(), admindashbord.class);
                                intent.putExtra("adminliscene",adminlicesneDB);
                                startActivity(intent);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                mPassword.setError("Wrong password");
                                mPassword.requestFocus();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"No such Account",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            //student and teacher login conditions
            else if(countNo == 8 || countNo==10){
                String customerid = mId.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Customer");
                Query checkUser = databaseReference.orderByChild("customerid").equalTo(customerid);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            mId.setError(null);
                            mId.setEnabled(false);
                            progressDialog.dismiss();
                            String passwordDB = snapshot.child(customerid).child("customerpassword").getValue(String.class);
                            if (passwordDB.equals(password)) {
                                mId.setError(null);
                                mId.setEnabled(false);
                                String customeridDB = snapshot.child(customerid).child("customerid").getValue(String.class);
                                Intent intent = new Intent(getApplicationContext(), customerdashbord.class);
                                intent.putExtra("userid",customeridDB);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(),"LogIn Successful",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                progressDialog.dismiss();
                                mId.setError("Wrong Id");
                                mId.requestFocus();
                                mPassword.setError("Wrong Password");
                                mPassword.requestFocus();
                            }

                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"No such Account",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                });
            }




            else{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"No such Account",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean validatecustomerid(){
        String val = mId.getText().toString().trim();
        if(val.isEmpty()){
            mId.setError("StudentId/ LicenseNo/ StaffId Required!");
            mId.requestFocus();
            return false;
        }
        return true;
    }
    private boolean validatePassword(){
        String val = mPassword.getText().toString().trim();
        if(val.isEmpty()){
            mPassword.setError("Password is Empty");
            mPassword.requestFocus();
        }
        return true;
    }

    public void forgotpassword(View view) {
        Intent intent=new Intent(MainActivity.this,forgotpasswordadmin.class);
        startActivity(intent);
    }

    public void adminsignup(View view) {
        Intent intent=new Intent(MainActivity.this,adminsignup.class);
        startActivity(intent);
    }



}