package com.example.creditbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class customerdashbord extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView mdisplaytotal, customerid;
    String userID, userName;
    DatabaseReference reference;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerdashbord);

        mdisplaytotal=findViewById(R.id.displaytotal);
        customerid=findViewById(R.id.customerid);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        firebaseAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.admin_navigation_drawer_open,R.string.admin_navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navigation);

        String userid = getIntent().getStringExtra("userid");
        String username = getIntent().getStringExtra("username");
        userID = userid;
        userName = username;
        System.out.println("SSSSSS " +userID);

        reference = FirebaseDatabase.getInstance().getReference("Total_Amount");
        Query checkUser = reference.orderByChild("userid").equalTo(userID);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String totalDB = snapshot.child(userID).child("amount").getValue(String.class);
                    customerid.setText("CustomerId: "+userID);
                    mdisplaytotal.setText("Nu "+totalDB);
                }
                else{
                    mdisplaytotal.setText("You don't have credit");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.developer:
                startActivity(new Intent(this,aboutus.class));
                break;

            case R.id.resetpassword:
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                intent.putExtra("userid", userID);
                startActivity(intent);
                break;
            case R.id.logout:
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
        }
        return true;
    }
}