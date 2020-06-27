package com.example.pankajnotereminder.db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pankajnotereminder.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterForTodo extends RecyclerView.Adapter<AdapterForTodo.TodoHolder> {

    private OnItemClickListener listener;
    private List<Notes> notes = new ArrayList<>();


    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item_list, parent, false);

        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Notes currentNotes = notes.get(position);
        holder.mTitle.setText(currentNotes.getTitle());
        holder.mDescription.setText(currentNotes.getDescription());
        holder.mPriority.setText(String.valueOf(currentNotes.getPriority()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setTodo(List<Notes> notes) {
        this.notes = notes;
        notifyDataSetChanged();

    }

    public Notes getNoteAt(int position) {
        return notes.get(position);
    }

    class TodoHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, mDescription, mPriority;


        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mDescription = itemView.findViewById(R.id.tv_description);
            mPriority = itemView.findViewById(R.id.tv_Priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();  //position where we need to click
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position)); //acquired the position
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Notes notes);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;


    }

}

