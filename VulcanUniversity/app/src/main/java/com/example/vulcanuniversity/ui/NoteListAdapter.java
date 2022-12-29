package com.example.vulcanuniversity.ui;

import android.icu.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vulcanuniversity.NoteActivity;
import com.example.vulcanuniversity.R;
import com.example.vulcanuniversity.database.NoteEntity;

import java.util.Date;

public class NoteListAdapter extends ListAdapter<NoteEntity, NoteListAdapter.NotesViewHolder> {
    private OnItemClickListener listener;

    public NoteListAdapter(NoteActivity NotesActivity){
        super(DIFF_CALLBACK);
    }
    public static final String NOTE_ID_KEY = "note_id_key";
    private static final DiffUtil.ItemCallback<NoteEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<NoteEntity>() {

        @Override
        public boolean areItemsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            return oldItem.getText().equals(newItem.getText()) && oldItem.getDate().equals(newItem.getDate());
        }
    };

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView note_date;
        private final TextView textItemView;

        private NotesViewHolder(View itemView) {
            super(itemView);
            note_date = itemView.findViewById(R.id.note_date);
            textItemView = itemView.findViewById(R.id.note_text);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(NoteEntity Notes);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_note, parent, false);
        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        NoteEntity current = getItem(position);
        holder.note_date.setText(dateFormat(current.getDate()));
        holder.textItemView.setText(current.getText());
    }


    private String dateFormat(Date date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
        return format.format(date);
    }

    public NoteEntity getNoteAt(int position){
        return getItem(position);
    }
}
