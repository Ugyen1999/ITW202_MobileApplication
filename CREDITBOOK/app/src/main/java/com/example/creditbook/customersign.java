package com.example.creditbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class customersign extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner mdepartment;
    private TextView mname, memail,mid,mpassword;
    Button msignup;
    String spin;

    private ProgressDialog mLoadingBar;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customersign);

        mdepartment=findViewById(R.id.selectdep);


        if(mdepartment != null){
            mdepartment.setOnItemSelectedListener(this);
        }

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.department, android.R.layout.simple_dropdown_item_1line);
        if(mdepartment != null){
            mdepartment.setAdapter(arrayAdapter);
        }



        mname=findViewById(R.id.cname);
        memail=findViewById(R.id.cemail);
        mid=findViewById(R.id.staffidstudentid);
        mpassword=findViewById(R.id.cpassword);

        msignup=findViewById(R.id.signinbutton2);



        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(customersign.this);



        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Customer");

                String email = memail.getText().toString();
                String name = mname.getText().toString();
                String password = mpassword.getText().toString();

                String customerid = mid.getText().toString();

               CustomerHelperClass helperClass = new CustomerHelperClass(name, email, password, spin, customerid);
                reference.child(customerid).setValue(helperClass);

            }
        });
    }

    private void checkCrededentials() {
        String email = memail.getText().toString();
        String name = mname.getText().toString();
        String password = mpassword.getText().toString();
        String customerid = mid.getText().toString();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (name.isEmpty() || name.length() < 3) {
            mname.setError( "Username is empty");
            mname.requestFocus();
        }

        else if (customerid.isEmpty()) {
            mid.setError("Customerid is empty");
            mid.requestFocus();
        }
        else if (email.isEmpty()) {
            memail.setError( "email is required");
            memail.requestFocus();
        }
//        if(!email.matches(checkEmail)){
//            memail.setError("Please Enter Valid Email Address");
//            memail.requestFocus();
//        }
        else if (password.isEmpty() || password.length() < 6) {
            mpassword.setError("Password is not valid and cannot be empty");
            mpassword.requestFocus();
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
                                    Toast.makeText(customersign.this, "Registration Successful,Check your email to verify", Toast.LENGTH_SHORT).show();
                                    mLoadingBar.dismiss();
                                    Intent intent= new Intent(customersign.this,admindashbord.class);
                                    intent.putExtra("customerid",customerid);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(customersign.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    else{
                        Toast.makeText(customersign.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }


    private void displayToast(String pick_up) {
        Toast.makeText(getApplicationContext(),pick_up,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String message = parent.getItemAtPosition(position).toString();
        spin=message;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
