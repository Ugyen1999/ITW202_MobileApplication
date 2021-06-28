package gcit.edu.keepme.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import gcit.edu.keepme.MainActivity;
import gcit.edu.keepme.R;
import gcit.edu.keepme.Splash;
import gcit.edu.keepme.forgotpass;

public class login extends AppCompatActivity {

    EditText IEmail, IPassword;
    Button loginNow;
    TextView forgetPass;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ImageView img;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        IEmail = findViewById(R.id.email);
        IPassword = findViewById(R.id.lPassword);
        loginNow = findViewById(R.id.LoginBtn);
        img=findViewById(R.id.back);
        spinner=findViewById(R.id.progressBar6);

        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        showWarning();

        forgetPass = findViewById(R.id.forgotPassword);

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = IEmail.getText().toString();
                String mPassword = IPassword.getText().toString();
                if (mEmail.isEmpty()) {
                    IEmail.setError("Email Is Required");
                    IEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                    IEmail.setError("Please Provide Valid Email");
                    IEmail.requestFocus();
                    return;

                }
                if (mPassword.isEmpty()) {
                    IPassword.setError("Password Is Required");
                    IPassword.requestFocus();
                    return;
                }

                spinner.setVisibility(View.VISIBLE);
                if(fAuth.getCurrentUser().isAnonymous()){
                    FirebaseUser user= fAuth.getCurrentUser();

                    fStore.collection("notes").document(user.getUid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(login.this, "All Temporary Notes Are Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(login.this, "Temporary User Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                fAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(login.this, "Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Login Failed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

    }

    private void showWarning() {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("Linking Existing Account Will Delete All The Temporary Notes. Create New Account To Save The Notes")
                .setPositiveButton("Save Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), registration.class));
                        finish();
                    }
                }).setNegativeButton("Its Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        warning.show();

    }

    public void back3(View view) {
        Intent intent = new Intent(this, mainpage.class);
        startActivity(intent);
    }


    public void forgotpassword(View view) {
        startActivity(new Intent(login.this,forgotpass.class));
    }
}