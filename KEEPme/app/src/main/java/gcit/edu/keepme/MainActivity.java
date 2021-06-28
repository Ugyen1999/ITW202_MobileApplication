package gcit.edu.keepme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import gcit.edu.keepme.auth.login;
import gcit.edu.keepme.auth.mainpage;
import gcit.edu.keepme.auth.registration;
import gcit.edu.keepme.model.Adapter;
import gcit.edu.keepme.model.Note;
import gcit.edu.keepme.note.AddNote;
import gcit.edu.keepme.note.EditNote;
import gcit.edu.keepme.note.NoteDetails;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    RecyclerView noteLists;
    Adapter adapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    FirebaseAuth fAuth;

    FirestoreRecyclerAdapter<Note,NoteViewHolder> noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fStore=FirebaseFirestore.getInstance();

        fAuth=FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();


        Query query=fStore.collection("notes").document(user.getUid()).collection("myNotes").orderBy("title", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<Note> allNotes= new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class)
                .build();




         noteAdapter=new FirestoreRecyclerAdapter<Note, NoteViewHolder>(allNotes) {
             @Override
             protected void onBindViewHolder(@NonNull  MainActivity.NoteViewHolder noteViewHolder, int i, @NonNull Note note) {
                 noteViewHolder.noteTitle.setText(note.getTitle());
                 noteViewHolder.noteContent.setText(note.getContent());
                 final String docId=noteAdapter.getSnapshots().getSnapshot(i).getId();
                 noteViewHolder.view.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent i=new Intent(v.getContext(), NoteDetails.class);
                         i.putExtra("title",note.getTitle());
                         i.putExtra("content",note.getContent());
                         i.putExtra("noteId",docId);
                         v.getContext().startActivity(i);
                     }
                 });
                 ImageView menuIcon=noteViewHolder.view.findViewById(R.id.menuIcon);
                 menuIcon.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(final View v) {
                         final String docId=noteAdapter.getSnapshots().getSnapshot(i).getId();
                         PopupMenu menu=new PopupMenu(v.getContext(),v);
                         menu.setGravity(Gravity.END);
                         menu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                             @Override
                             public boolean onMenuItemClick(MenuItem item) {
                                 Intent i=new Intent(v.getContext(), EditNote.class);
                                 i.putExtra("title",note.getTitle());
                                 i.putExtra("content",note.getContent());
                                 i.putExtra("noteId",docId);
                                 startActivity(i);
                                 return false;
                             }
                         });
                         menu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                             @Override
                             public boolean onMenuItemClick(MenuItem item) {
                                AlertDialog dia
                             }
                         });
                         menu.show();

                     }
                 });

             }

             @NonNull

             @Override
             public NoteViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                 View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_layout,parent,false);
                 return new NoteViewHolder(view);
             }
         };


        noteLists=findViewById(R.id.notelist);

        drawerLayout=findViewById(R.id.drawer);
        nav_view=findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        noteLists.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        noteLists.setAdapter(noteAdapter);

        View headerView=nav_view.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.userDisplayName);
        TextView userEmail=headerView.findViewById(R.id.userDisplayEmail);

        if(user.isAnonymous()){
            userEmail.setVisibility(View.INVISIBLE);
            username.setText("Temporary User");
        }else{
            userEmail.setText(user.getEmail());
            username.setText(user.getDisplayName());
        }


        FloatingActionButton fab=findViewById(R.id.addNoteFloat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(nav_view.getContext(), AddNote.class));
                overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
       switch (item.getItemId()){
           case R.id.addNote:
               startActivity(new Intent(this,AddNote.class));
               overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
               break;

           case R.id.sync:
               if(user.isAnonymous()){
                   startActivity(new Intent(this, login.class));
                   overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
               }
               else{
                   Toast.makeText(this, "You Are Connected", Toast.LENGTH_SHORT).show();
               }
               break;

           case R.id.logout:
               checkUser();
               break;

           default:
               Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
       }

        return false;
    }

    private void checkUser() {
        if(user.isAnonymous()){
            displayAlert();
        }else{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Splash.class));
            overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
            finish();
        }
    }

    private void displayAlert() {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("You are logged in with Temporary Account. Logging out will Delete all the notes")
                .setPositiveButton("Sync Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), mainpage.class));
                        overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
                        finish();
                    }
                }).setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user=FirebaseAuth.getInstance().getCurrentUser();
                        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(),Splash.class));
                                overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
                                finish();
                            }
                        });
                    }
                });
        warning.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId()==R.id.search){

       }
       else if (item.getItemId()==R.id.notify){
            Intent ov=new Intent(this, Notification.class);
            startActivity(ov);
        }
        return super.onOptionsItemSelected(item);
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle,noteContent;
        View view;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle=itemView.findViewById(R.id.titles);
            noteContent=itemView.findViewById(R.id.content);
            view=itemView;
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        noteAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        noteAdapter.stopListening();
    }
}
