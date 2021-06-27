package com.example.creditbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class admindashbord extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recview;
    myadapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton msignin;
    FirebaseAuth firebaseAuth;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String licenseNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashbord);
        setTitle("Enter CustomerId...");
        msignin = findViewById(R.id.signin);
        firebaseAuth = FirebaseAuth.getInstance();
        String license = getIntent().getStringExtra("adminliscene");
        licenseNo = license;
        System.out.println("SSSS"+license);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_View);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.admin_navigation_drawer_open,R.string.admin_navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navigation);

        msignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(admindashbord.this, "Register Your Customer", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(admindashbord.this, customersign.class);
                startActivity(intent);
            }
        });

        recview = (RecyclerView) findViewById(R.id.recyclerView);
        recview.setLayoutManager(new LinearLayoutManager(this));
//
        FirebaseRecyclerOptions<CustomerHelperClass> options = new FirebaseRecyclerOptions.Builder<CustomerHelperClass>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Customer"), CustomerHelperClass.class)
                .build();

        adapter = new myadapter(options);
        recview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void processsearch(String s) {
        FirebaseRecyclerOptions<CustomerHelperClass> options = new FirebaseRecyclerOptions.Builder<CustomerHelperClass>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Customer").orderByChild("customerid").startAt(s).endAt(s + "\uf8ff"), CustomerHelperClass.class)
                .build();

        adapter = new myadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.developer:
                startActivity(new Intent(admindashbord.this, aboutus.class));
                break;


            case R.id.resetpassword:
                Intent intent = new Intent(getApplicationContext(),resetpassword.class);
                intent.putExtra("license",licenseNo);
                startActivity(intent);
                break;

            case R.id.logout:
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

        }
        return false;
    }


}


























