package com.example.creditbook;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class myadapter extends FirebaseRecyclerAdapter<CustomerHelperClass, myadapter.myviewholder>
{
    public myadapter(@NonNull FirebaseRecyclerOptions<CustomerHelperClass> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final CustomerHelperClass model)
    {
        holder.customerid.setText(model.getcustomerid());
        holder.customername.setText(model.getcustomername());
        holder.customerdepartment.setText(model.getcustomerdepartment());
        holder.customeremail.setText(model.getcustomeremail());

        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mview.getContext(),crudfunction.class);
                intent.putExtra("userid",model.getcustomerid());
                intent.putExtra("username",model.getcustomername());
                intent.putExtra("customeremail",model.getcustomeremail());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.mview.getContext().startActivity(intent);
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.customerid.getContext());
                builder.setTitle("Delete Account");
                builder.setMessage("Are you sure that this Account has no credit?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Customer")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.users,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView delete,mview;
        TextView customername,customerid,customerdepartment,customeremail;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            customerid=(TextView)itemView.findViewById(R.id.coursetext);
            customername=(TextView)itemView.findViewById(R.id.emailtext);
            customerdepartment=(TextView)itemView.findViewById(R.id.customerdepartment);
            customeremail=(TextView)itemView.findViewById(R.id.customeremail);

            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
            mview=(ImageView)itemView.findViewById(R.id.view);
        }
    }

}

