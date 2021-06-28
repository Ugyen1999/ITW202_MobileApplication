package gcit.edu.gcit_gatescan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SecurityDashBoard extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TabLayout tabLayout;
    ViewPager2 pager2;
    ViewPagerAdapter adapter;

    DatabaseReference data= FirebaseDatabase.getInstance().getReference("SecurityGuard");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_dash_board);



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
        setSupportActionBar(toolbar);

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
        pager2 = findViewById(R.id.view_pager);


        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Entry Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Exit Info"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
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
