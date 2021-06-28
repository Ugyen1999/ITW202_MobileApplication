package gcit.edu.keepme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import gcit.edu.keepme.model.Note;

public class Search extends AppCompatActivity {

    DatabaseReference mref;
    private ListView listdata;
    private AutoCompleteTextView txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mref= FirebaseDatabase.getInstance().getReference("notes");
        listdata=(ListView)findViewById(R.id.listData);
        txtSearch=(AutoCompleteTextView)findViewById(R.id.txtSearch);
        txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=txtSearch.getText().toString();
                searchUser(name);
            }
        });

        ValueEventListener event=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot Snapshot) {
                populateSearch(Snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mref.addListenerForSingleValueEvent(event);

    }

    private void searchUser(String name) {
    }

    private void populateSearch(DataSnapshot snapshot) {


    }
}