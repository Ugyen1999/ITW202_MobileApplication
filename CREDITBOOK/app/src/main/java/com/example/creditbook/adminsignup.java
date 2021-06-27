package com.example.creditbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class adminsignup extends AppCompatActivity {
    private EditText mfullname,memail,mpassword,phonenumber,liscene_number;
    private Button adminregister;
    private ProgressDialog mLoadingBar;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsignup);

        mfullname = findViewById(R.id.adminname);
        memail = findViewById(R.id.adminemail);
        mpassword = findViewById(R.id.adminpassword);
        phonenumber = findViewById(R.id.adminphone);
        liscene_number = findViewById(R.id.adminliscene);
        adminregister = findViewById(R.id.adminbutton);

        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(adminsignup.this);
        adminregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Admin");

                String email = memail.getText().toString();
                String name = mfullname.getText().toString();
                String password = mpassword.getText().toString();
                String liscenenumber = liscene_number.getText().toString();
                String phone = phonenumber.getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name, email, password, liscenenumber, phone);
                reference.child(liscenenumber).setValue(helperClass);

            }
        });
    }

        private void checkCrededentials() {
            String email = memail.getText().toString();
            String name = mfullname.getText().toString();
            String password = mpassword.getText().toString();
            String liscenenumber= liscene_number.getText().toString();
            String phone = phonenumber.getText().toString();
            final  Pattern BPONE_NUMBER = Pattern.compile("[1][7][0-9]{6}",Pattern.CASE_INSENSITIVE);
            String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (name.isEmpty() || name.length() < 3) {
                mfullname.setError("Username is cannot be empty");
                mfullname.requestFocus();
            }
            else if (phone.isEmpty() || phone.length() < 8) {
                phonenumber.setError("Phone Number is Required");
                phonenumber.requestFocus();

            }

            if(!BPONE_NUMBER.matcher(phone).matches()){
                phonenumber.setError("Invalid Phone Number");
                phonenumber.requestFocus();
            }


            else if (email.isEmpty()) {
                memail.setError("Email is empty or Invalid");
                memail.requestFocus();
            }
//            else if(!email.matches(checkEmail)){
//                memail.setError("Please Enter Valid Email Address");
//                memail.requestFocus();
//            }


            else if (password.isEmpty() || password.length() < 6) {
                mpassword.setError("Password is empty or Invalid");
                mpassword.requestFocus();
            }
            else if (liscenenumber.isEmpty() || liscenenumber.length() < 7) {
                liscene_number.setError( "Liscene Number is empty or Invalid");
                liscene_number.requestFocus();
            }

            else{
                    mLoadingBar.setTitle("Signing Up");
                    mLoadingBar.setMessage("Please wait while Signing Up");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(adminsignup.this, "Successful registration,Check your email to verify", Toast.LENGTH_SHORT).show();
                                        mLoadingBar.dismiss();
                                        Intent intent= new Intent(adminsignup.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(adminsignup.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            }

                            else{
                                Toast.makeText(adminsignup.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

            }


        }


}

