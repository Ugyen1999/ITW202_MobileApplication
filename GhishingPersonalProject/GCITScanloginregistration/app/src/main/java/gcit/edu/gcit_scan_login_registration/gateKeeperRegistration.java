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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;



public class gateKeeperRegistration extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private EditText g_userName,g_email, g_contact, g_NewPassword,g_confirmPassword;
    private Button gbtnregister;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_keeper_registration);
        g_userName=findViewById(R.id.gusername);
        g_email=findViewById(R.id.gemail);
        g_contact=findViewById(R.id.gcontact);
        g_NewPassword=findViewById(R.id.gnewpassword);
        g_confirmPassword=findViewById(R.id.gconfirmpassword);
        gbtnregister=findViewById(R.id.gbtnregister);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(gateKeeperRegistration.this);
        firebaseAuth = FirebaseAuth.getInstance();


        gbtnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gateRegistration();

            }
        });


    }



    public void gateRegistration() {
        String gname = g_userName.getText().toString().trim();
        String gemail = g_email.getText().toString().trim();
        String gcontact = g_contact.getText().toString().trim();
        String gnewpassword = g_NewPassword.getText().toString().trim();
        String gconfirmpassword = g_confirmPassword.getText().toString().trim();

        if (gname.isEmpty()) {
            g_userName.setError("Provide your Name to proceed");
            g_userName.requestFocus();
            return;
        }
        if (gemail.isEmpty()) {
            g_email.setError("Please Provide Your Email");
            g_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(gemail).matches()) {
            g_email.setError("Please provide Valid Email");
            g_email.requestFocus();
            return;
        }
        if (gcontact.isEmpty()) {
            g_contact.setError("Please provide Your contact NUmber:");
            g_contact.requestFocus();
            return;

        }
        if (gnewpassword.isEmpty()) {
            g_NewPassword.setError("PLease provide Password");
            g_NewPassword.requestFocus();
            return;
        }
        if (gnewpassword.length() < 6) {
            g_NewPassword.setError("Provide Strong Password");
            g_NewPassword.requestFocus();
            return;

        }
        if (!gconfirmpassword.equals(gnewpassword)) {
            g_confirmPassword.setError("Password didn't match");
            g_confirmPassword.requestFocus();
            return;
        } else {
            progressDialog.setTitle("Registering");
            progressDialog.setMessage("Hold for a moment!!! \nWhile checking Your Credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(gemail, gnewpassword)
                    .addOnCompleteListener(gateKeeperRegistration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                securityPush fuck = new securityPush(gname,gemail,gcontact);
                                FirebaseDatabase.getInstance().getReference("SecurityGuard")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(fuck).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(gateKeeperRegistration.this, "You are successfully registered as Security Guard!", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), userLogin.class));
                                            progressDialog.dismiss();

                                        } else {
                                            Toast.makeText(gateKeeperRegistration.this, "Fail to Register!!! Please Try Again", Toast.LENGTH_LONG).show();
                                        }


                                    }
                                });


                            } else {
                                Toast.makeText(gateKeeperRegistration.this, "Fail to register! Try again", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }


                        }
                    });
        }
    }


}
