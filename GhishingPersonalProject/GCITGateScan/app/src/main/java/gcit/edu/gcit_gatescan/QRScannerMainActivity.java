package gcit.edu.gcit_gatescan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerMainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TabLayout tabLayout;

    DatabaseReference data= FirebaseDatabase.getInstance().getReference("UserInfo");


    Button scan_btn, button;

    public static TextView mtxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_scanner_main_activity);



        String currentUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference=data.child(currentUser);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String Email= String.valueOf(snapshot.child("Email").getValue());
                String Name = String.valueOf(snapshot.child("Name").getValue());
                String Contact = String.valueOf(snapshot.child("Contact").getValue());

                navigationView = (NavigationView) findViewById(R.id.nav_view);

                View headerView = navigationView.getHeaderView(0);
                TextView name = (TextView) headerView.findViewById(R.id.name);
                TextView email = (TextView) headerView.findViewById(R.id.email);
                TextView contact = (TextView) headerView.findViewById(R.id.contact);
                name.setText(Name);
                email.setText(Email);
                contact.setText(Contact);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        toolbar = findViewById(R.id.main_toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.opennavDrawer,
                R.string.closenavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        tabLayout = findViewById(R.id.layout);

        scan_btn = (Button) findViewById(R.id.qr_btn);
        button=findViewById(R.id.button2);
        mtxt = (TextView) findViewById(R.id.qr_text);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QRscannerEntry.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),QRscannerExit.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_Distance:

        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}




}