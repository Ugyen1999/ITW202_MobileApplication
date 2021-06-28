package gcit.edu.gcit_gatescan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapter extends FirebaseRecyclerAdapter<pushDetails,MyAdapter.myviewholder> {



    public MyAdapter(@NonNull @NotNull FirebaseRecyclerOptions<pushDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull MyAdapter.myviewholder holder, int position, @NonNull @NotNull pushDetails model) {
        holder.name.setText(model.name);
        holder.contact.setText(model.contact);
        holder.date.setText(model.date);
        holder.time.setText(model.time);

    }

    @NonNull
    @NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return  new myviewholder(view);

    }

    public static class myviewholder extends RecyclerView.ViewHolder{
        TextView name, contact, date, time;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namea);
            contact = itemView.findViewById(R.id.contacta);
            date = itemView.findViewById(R.id.datea);
            time = itemView.findViewById(R.id.timea);


        }
    }

}
