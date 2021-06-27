package com.example.creditbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class  clearcredit extends AppCompatActivity {
    private EditText mdate,mcreditamount;
    TextView getMdisplaycustomername;
    private ProgressDialog mLoadingBar;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button mbuttun;
    ImageView datebutton;
    String userID, userName,userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clearcredit);


        mdate=findViewById(R.id.currentdate);
        mcreditamount=findViewById(R.id.creditamount);
        mbuttun=findViewById(R.id.clearcredit);
        datebutton=findViewById(R.id.button);



        getMdisplaycustomername = findViewById(R.id.displaycustomername);


        String userid = getIntent().getStringExtra("userid");
        String username = getIntent().getStringExtra("username");
        String useremail = getIntent().getStringExtra("customeremail");

        userID = userid;
        userName = username;
        userEmail=useremail;
        getMdisplaycustomername.setText(username);
//        mdisplaycustomerid.setText(userid);
//        mdisplaycustomeremail.setText(useremail);


        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(clearcredit.this);


        mbuttun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAmount()){
                    return;
                }
                String addamount = mcreditamount.getText().toString();
                String date = mdate.getText().toString();
                reference = FirebaseDatabase.getInstance().getReference("Total_Amount");
                Query checkUser = reference.orderByChild("userid").equalTo(userID);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String totalDB = snapshot.child(userID).child("amount").getValue(String.class);
                            if(Integer.valueOf(totalDB).equals(0)){
                                Toast.makeText(getApplicationContext(),"Customer don't have credit, Thank you",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Intent intent=new Intent(clearcredit.this,admindashbord.class);
                                startActivity(intent);
                                finish();

                            }

                            if(Integer.valueOf(totalDB) - Integer.valueOf(addamount)<0){
                                Toast.makeText(getApplicationContext(),"Clear Credit Amount should be less than or equal to Total Credit",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                SendEmail();
                                int newTotal = Integer.valueOf(totalDB) - Integer.valueOf(addamount);
                                reference.child(userID).child("amount").setValue(String.valueOf(newTotal));
                                Toast.makeText(getApplicationContext(), "The credit has been cleared successfully", Toast.LENGTH_SHORT).show();
                                mcreditamount.setText("");
                                mdate.setText("");
                            }

                        }
                        else{
                            mcreditamount.setText("");
                            mdate.setText("");
                            Toast.makeText(getApplicationContext(),"No credit, Thank you",Toast.LENGTH_LONG).show();
                        }



                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    private boolean validateAmount() {
        String addamount = mcreditamount.getText().toString();
        String ndate = mdate.getText().toString();
        if (addamount.isEmpty()) {
            mcreditamount.setError("Amount cannot be empty");
            mcreditamount.requestFocus();
            return false;
        }
        return true;
    }

    private void SendEmail() {
        String useremail = getIntent().getStringExtra("customeremail");
        userEmail=useremail;

        String addamount = mcreditamount.getText().toString();
        String message="Your have cleared: Nu  "+ addamount +"\n"+"Please Check Your Total Credit Amount in GCIT ཟ་ཁང་རྩིས་དེབ། app";

        String date = mdate.getText().toString();

        String description="Your GCIT ཟ་ཁང་རྩིས་དེབ།  Account has been cleared with Nu: "+addamount+"  on  "+date;


        //email,then subject,and then mmessage

        JavaMailAPI javaMailAPI = new JavaMailAPI(this,useremail,description, message);
        javaMailAPI.execute();
    }

    public void Date(View view) {
        DialogFragment newFragment = new DatePickerFragment1();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void ResultPicker(int year, int month, int dayOfMonth) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(dayOfMonth);
        String year_string = Integer.toString(year);

        String date_message = (month_string + "/" + day_string + "/" +year_string);
//        Toast.makeText(getApplicationContext(), "Date: " +date_message, Toast.LENGTH_SHORT).show();
        mdate.setText(date_message);
    }




}