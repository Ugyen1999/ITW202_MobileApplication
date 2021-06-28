package gcit.edu.gcit_gatescan;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class userLogin extends AppCompatActivity {
    private EditText uLoginEmail, uloginPassword;
    private Button ubtnlogin;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        uLoginEmail=findViewById(R.id.uemailLogin);
        uloginPassword=findViewById(R.id.upassswordlogin);
        ubtnlogin=findViewById(R.id.ubtnLogin);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(userLogin.this);
        ubtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginCheck();
            }
        });

    }

    private void LoginCheck() {
        String email=uLoginEmail.getText().toString().trim();
        String password=uloginPassword.getText().toString().trim();
        if (email.isEmpty()){
            uLoginEmail.setError("Please enter your Email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            uLoginEmail.setError("Please provide valid Email");
            return;
        }
        else if (password.isEmpty()){
            uloginPassword.setError("Please provide Password");
            return;
        }
        else {
            progressDialog.setTitle("Logging In");
            progressDialog.setMessage("We are checking the Credentials\n\n Please bear with us for a moment!");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        if (user.isEmailVerified()){
                            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("SecurityGuard");
                            Query check=databaseReference.orderByChild("Email").equalTo(email);
                            check.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        startActivity(new Intent(getApplicationContext(),SecurityDashBoard.class));
                                        progressDialog.dismiss();
                                        Toast.makeText(userLogin.this, "You are in Security Guard's Dash Board", Toast.LENGTH_LONG).show();

                                    }else{
                                        startActivity(new Intent(getApplicationContext(),QRScannerMainActivity.class));
                                        progressDialog.dismiss();
                                        Toast.makeText(userLogin.this, "You are in user's Dashboard", Toast.LENGTH_LONG).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }else{
                            progressDialog.dismiss();
                            user.sendEmailVerification();
                            Toast.makeText(userLogin.this, "You are Logging in for first time\n" +
                                    " Please check your email to verify that its you", Toast.LENGTH_LONG).show();
                        }

                    }
                    else {
                        Toast.makeText(userLogin.this, "Fail to Login! Check your Credentials",
                                Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }

                }
            });
        }
    }


//    public void userRegisteration(View view) {
//        startActivity(new Intent(getApplicationContext(),userRegistration.class));
//    }
//
//
//    public void goToForgetFromUser(View view) {
//        startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
//    }
//
//    public void securityRegisteration(View view) {
//        startActivity(new Intent(getApplicationContext(),gateKeeperRegistration.class));
//    }
}