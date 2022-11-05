package com.example.db10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.db10.Listener.OnNoteClickListener;
import com.example.db10.Model.Note;
import com.example.db10.R;
import com.example.db10.Utlis.NoteDateUtlis;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Note> allNotes;
    private boolean multiChooseMode = false;
    OnNoteClickListener listener;

    public NoteListAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_note_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Note n = allNotes.get(position);

        if (n.getTitle() == null) {
            holder.txt_note_title.setText(n.getContent());
        }else{
            holder.txt_note_title.setText(n.getTitle());
        }

        holder.txt_note_date.setText(NoteDateUtlis.dateFromLong(n.getDate()));

        if(multiChooseMode){
            holder.checkbox_note.setVisibility(View.VISIBLE);
            holder.checkbox_note.setChecked(n.isChecked());
        }else{
            holder.checkbox_note.setVisibility(View.GONE);
            holder.checkbox_note.setChecked(n.isChecked());
        }

        holder.setOnNoteClickListener(listener);
    }

    public void setAllNotes(List<Note> notes){
        allNotes = notes;
        notifyDataSetChanged();
    }

    public void setMultiChooseMode(boolean mode){
        this.multiChooseMode = mode;
        notifyDataSetChanged();
    }

    public Note getAdapterNoteByPosition(int position){
        return allNotes.get(position);
    }

    public void setOnNoteClickListener(OnNoteClickListener listener){
        this.listener = listener;
    }

    public List<Note> getAllNotes(){
        return allNotes;
    }

    @Override
    public int getItemCount() {
        if(allNotes != null)
            return allNotes.size();
        else
            return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txt_note_title;
        TextView txt_note_date;
        CheckBox checkbox_note;
        OnNoteClickListener listener;

        void setOnNoteClickListener(OnNoteClickListener onNoteClickListener){
            this.listener = onNoteClickListener;
        };

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_note_title = itemView.findViewById(R.id.txt_note_title);
            txt_note_date = itemView.findViewById(R.id.txt_note_date);
            checkbox_note = itemView.findViewById(R.id.checkbox_note);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onLongClick(v,getAdapterPosition());
            return false;
        }
    }
}
