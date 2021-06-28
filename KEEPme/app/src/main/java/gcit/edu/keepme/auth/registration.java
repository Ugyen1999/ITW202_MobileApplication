package gcit.edu.keepme.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import gcit.edu.keepme.MainActivity;
import gcit.edu.keepme.R;

public class registration extends AppCompatActivity {
    EditText rUserName,rUserEmail,rUserPass,rUserConfPass;
    Button syncAccount;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fAuth=FirebaseAuth.getInstance();

        rUserName=findViewById(R.id.userName);
        rUserEmail=findViewById(R.id.userEmail);
        rUserPass=findViewById(R.id.password);
        rUserConfPass=findViewById(R.id.confirmPassword);

        syncAccount=findViewById(R.id.createAccount);
        progressBar=findViewById(R.id.progressBar4);

        syncAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uUsername=rUserName.getText().toString();
                String uUserEmail=rUserEmail.getText().toString();
                String uUserPass=rUserPass.getText().toString();
                String uConfpass=rUserConfPass.getText().toString();

                if(uUsername.isEmpty()){
                    rUserName.setError("Name Is Required");
                    rUserName.requestFocus();
                    return;
                }
                if(uUserEmail.isEmpty()){
                    rUserEmail.setError("Email Is Required");
                    rUserEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(uUserEmail).matches()){
                    rUserEmail.setError("Please Provide Valid Email");
                    rUserEmail.requestFocus();
                    return;

                }
                if(uUserPass.isEmpty()){
                    rUserPass.setError("Password Is Required");
                    rUserPass.requestFocus();
                    return;
                }
                if(!uUserPass.equals(uConfpass)){
                    rUserConfPass.setError("Password doesn't match");
                    rUserConfPass.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                AuthCredential credential = EmailAuthProvider.getCredential(uUserEmail,uUserPass);
                fAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(registration.this, "Notes Are Synced", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        FirebaseUser usr=fAuth.getCurrentUser();
                        UserProfileChangeRequest request=new UserProfileChangeRequest.Builder()
                                .setDisplayName(uUsername)
                                .build();
                        usr.updateProfile(request);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registration.this, "Failed To Connect. Try Again", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });


    }

    public void submit(View view) {
    }

    public void back1(View view) {
        Intent intent=new Intent(this,mainpage.class);
        startActivity(intent);
    }
}