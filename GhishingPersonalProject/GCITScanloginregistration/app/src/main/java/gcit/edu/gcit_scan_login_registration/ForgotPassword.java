package gcit.edu.gcit_scan_login_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ForgotPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText forgot_password;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot_password=findViewById(R.id.resetemail);
        btn=findViewById(R.id.submit_reset);
        mAuth=FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.sendPasswordResetEmail(forgot_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),userLogin.class));
                            Toast.makeText(ForgotPassword.this, "Reset link is sent to your Email", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(ForgotPassword.this, "Please provide valid Email Address", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

    }
}