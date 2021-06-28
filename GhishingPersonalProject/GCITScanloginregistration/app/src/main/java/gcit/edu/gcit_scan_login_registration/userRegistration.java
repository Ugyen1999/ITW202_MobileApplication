package gcit.edu.gcit_scan_login_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class userRegistration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText u_userName,u_email, u_contact, u_NewPassword,u_confirmPassword;
    private Button ubtnregister;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        u_userName=findViewById(R.id.uusername);
        u_email=findViewById(R.id.uemail);
        u_contact=findViewById(R.id.ucontact);
        u_NewPassword=findViewById(R.id.unewpassword);
        u_confirmPassword=findViewById(R.id.uconfirmpassword);
        ubtnregister=findViewById(R.id.ubtnregister);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(userRegistration.this);
        firebaseAuth = FirebaseAuth.getInstance();


        ubtnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegistration();

            }
        });
    }

    private void UserRegistration() {


        String uname = u_userName.getText().toString().trim();
        String uemail = u_email.getText().toString().trim();
        String ucontact = u_contact.getText().toString().trim();
        String unewpassword = u_NewPassword.getText().toString().trim();
        String uconfirmpassword = u_confirmPassword.getText().toString().trim();

        if (uname.isEmpty()) {
            u_userName.setError("Provide your Name to proceed");
            u_userName.requestFocus();
            return;
        }
        if (uemail.isEmpty()) {
            u_email.setError("Please Provide Your Email");
            u_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(uemail).matches()) {
            u_email.setError("Please provide Valid Email");
            u_email.requestFocus();
            return;
        }
        if (ucontact.isEmpty()) {
            u_contact.setError("Please provide Your contact NUmber:");
            u_contact.requestFocus();
            return;

        }
        if (unewpassword.isEmpty()) {
            u_NewPassword.setError("PLease provide Password");
            u_NewPassword.requestFocus();
            return;
        }
        if (unewpassword.length() < 6) {
            u_NewPassword.setError("Provide Strong Password");
            u_NewPassword.requestFocus();
            return;

        }
        if (!uconfirmpassword.equals(unewpassword)) {
            u_confirmPassword.setError("Password didn't match");
            u_confirmPassword.requestFocus();
            return;
        } else {
            progressDialog.setTitle("Registering");
            progressDialog.setMessage("Hold for a moment!!! \nWhile checking Your Credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(uemail, unewpassword)
                    .addOnCompleteListener(userRegistration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userPush userPush = new userPush(uname,uemail,ucontact);
                                FirebaseDatabase.getInstance().getReference("UserInfo")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(userPush).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(userRegistration.this, "You are successfully registered as user!", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), userLogin.class));
                                            progressDialog.dismiss();

                                        } else {
                                            Toast.makeText(userRegistration.this, "Fail to Register!!! Please Try Again", Toast.LENGTH_LONG).show();
                                        }


                                    }
                                });
                            } else {
                                Toast.makeText(userRegistration.this, "Fail to register! Try again", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }


                        }
                    });
        }
    }

    }


