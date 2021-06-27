package com.example.creditbook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class add extends AppCompatActivity {
    private EditText mdate,mcreditamount,madditem;
    TextView getMdisplaycustomername;
    private ProgressDialog mLoadingBar;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button mbuttun;
    ImageView datebutton;
    String userID, userName,userEmail;

    boolean[] selectitem;
    ArrayList<Integer>itemlist=new ArrayList<>();
    String[] itemArray={"KopiBalay", "FriedRice", "Water","WINKIN", "WaiWai","AluCurry","PlaneRice",
    "Aluchop","Chana","Payzzey","MoMo","Boiled Egg","peach wine","Mango juice","chicken Curry", "Beaf Curry", "PorkCurry"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mdate=findViewById(R.id.currentdate);
        mcreditamount=findViewById(R.id.creditamount);
        mbuttun=findViewById(R.id.addcredit);
        datebutton=findViewById(R.id.button);


        getMdisplaycustomername = findViewById(R.id.displaycustomername);



        madditem=findViewById(R.id.additem);
        selectitem = new boolean[itemArray.length];

        madditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(add.this);

                builder.setTitle("Select Item");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(itemArray, selectitem, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            itemlist.add(which);
                            Collections.sort(itemlist);
                        }
                        else {
                            itemlist.remove(which);


                        }

                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for(int j=0; j<itemlist.size(); j++){
                            stringBuilder.append(itemArray[itemlist.get(j)]);

                            if(j !=itemlist.size()-1){
                                stringBuilder.append(", ");
                            }

                        }
                        madditem.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int j=0; j<selectitem.length; j++){
                            selectitem[j]=false;

                            itemlist.clear();

                            madditem.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

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
        mLoadingBar = new ProgressDialog(add.this);



        mbuttun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAmount()){
                    return;
                }

                SendEmail();
                Intent intent=new Intent(add.this,admindashbord.class);
                startActivity(intent);
                finish();


//                final DatabaseReference myrootf=FirebaseDatabase.getInstance().getReferenceFromUrl("https://gcitzakhangtsideb-default-rtdb.firebaseio.com/Customer");

                String addamount = mcreditamount.getText().toString();
                String date = mdate.getText().toString();
                reference = FirebaseDatabase.getInstance().getReference("Total_Amount");
                Query checkUser = reference.orderByChild("userid").equalTo(userID);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String totalDB = snapshot.child(userID).child("amount").getValue(String.class);
                            int newTotal = Integer.valueOf(totalDB) + Integer.valueOf(addamount);
                            reference.child(userID).child("amount").setValue(String.valueOf(newTotal));
                            Toast.makeText(getApplicationContext(),"The credit has been added successfully",Toast.LENGTH_SHORT).show();
                            mcreditamount.setText("");
                            mdate.setText("");
                        }

                        else{
                            AddcreditHelperClass helperClass = new AddcreditHelperClass(addamount,date,userID,userName);
                            reference.child(userID).setValue(helperClass);
                            System.out.println("Hello ERROR3");
                            mcreditamount.setText("");
                            mdate.setText("");
                            Toast.makeText(getApplicationContext(),"Started to make credit, Thank you",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });



    }

    private void SendEmail() {

        String useremail = getIntent().getStringExtra("customeremail");
        userEmail=useremail;


        String additem =madditem.getText().toString();
        String addamount = mcreditamount.getText().toString();
        String message="Purchased Item are: "+additem + "\n"+"Total Cost: Nu "+ addamount;

        String date = mdate.getText().toString();

        String description="Your GCIT ཟ་ཁང་རྩིས་དེབ།  Account has been added with: Nu "+addamount+"  on  "+date;

        //email,then subject,and then mmessage

        JavaMailAPI javaMailAPI = new JavaMailAPI(this,useremail,description, message);
        javaMailAPI.execute();

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

    public void Date(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void processResultPicker(int year, int month, int dayOfMonth) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(dayOfMonth);
        String year_string = Integer.toString(year);

        String date_message = (month_string + "/" + day_string + "/" +year_string);
//        Toast.makeText(getApplicationContext(), "Date: " +date_message, Toast.LENGTH_SHORT).show();
        mdate.setText(date_message);
    }


}