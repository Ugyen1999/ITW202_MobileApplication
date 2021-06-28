package gcit.edu.keepme.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gcit.edu.keepme.note.NoteDetails;
import gcit.edu.keepme.R;

public class Adapter  extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> titles;
    List<String> content;
    public  Adapter(List<String> title, List<String> content){
        this.titles=title;
        this.content=content;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.noteTitle.setText(titles.get(position));
        holder.noteContent.setText(content.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), NoteDetails.class);
                i.putExtra("title",titles.get(position));
                i.putExtra("content",content.get(position));
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle,noteContent;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle=itemView.findViewById(R.id.titles);
            noteContent=itemView.findViewById(R.id.content);
            view=itemView;

        }
    }
}
