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
import com.google.firebase.auth.FirebaseAuth;

public class forgotpasswordadmin extends AppCompatActivity {
    private EditText emailforgotpassword;
    Button forgotpassword;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpasswordadmin);

        emailforgotpassword=findViewById(R.id.adminemailforgotpassword);
        forgotpassword=findViewById(R.id.adminresetpassword);
        mAuth=FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(forgotpasswordadmin.this);

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(emailforgotpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(forgotpasswordadmin.this,"Password reset link is send to your email", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(forgotpasswordadmin.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(forgotpasswordadmin.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }


}