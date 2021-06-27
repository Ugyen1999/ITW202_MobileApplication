package com.example.creditbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class crudfunction extends AppCompatActivity {
    Button maddcredit, mclearcredit;
    TextView getMdisplaycustomername;
    String userID, userName,userEmail;

    DatabaseReference reference;

    TextView mdisplaytotal, customerid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudfunction);

        mdisplaytotal=findViewById(R.id.displaytotal);
        customerid=findViewById(R.id.customerid);

        getMdisplaycustomername = findViewById(R.id.displaycustomername);


        String userid = getIntent().getStringExtra("userid");
        String username = getIntent().getStringExtra("username");
        String useremail = getIntent().getStringExtra("customeremail");


        userID = userid;
        userName = username;
        userEmail=useremail;
        getMdisplaycustomername.setText(username);


        maddcredit = findViewById(R.id.addcredit);
        mclearcredit = findViewById(R.id.clearcredit);


        maddcredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(crudfunction.this, add.class);
                intent.putExtra("userid",userID);
                intent.putExtra("username",userName);
                intent.putExtra("customeremail",userEmail);
                startActivity(intent);
            }
        });
        mclearcredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(crudfunction.this, clearcredit.class);
                intent.putExtra("userid",userID);
                intent.putExtra("username",userName);
                intent.putExtra("customeremail",userEmail);
                startActivity(intent);
                finish();
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("Total_Amount");
        Query checkUser = reference.orderByChild("userid").equalTo(userID);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String totalDB = snapshot.child(userID).child("amount").getValue(String.class);
                    mdisplaytotal.setText("Nu "+totalDB);
                }
                else{
                    mdisplaytotal.setText("No Credit");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}