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

import com.example.vulcanuniversity.MentorActivity;
import com.example.vulcanuniversity.R;
import com.example.vulcanuniversity.database.AppRepository;
import com.example.vulcanuniversity.database.MentorEntity;

import java.util.Date;

public class MentorListAdapter extends ListAdapter<MentorEntity,MentorListAdapter.MentorViewHolder> {

    private OnItemClickListener listener;
    private AppRepository mRepository;

    public MentorListAdapter(MentorActivity MentorActivity) {
        super(DIFF_CALLBACK);}
    private static final DiffUtil.ItemCallback<MentorEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<MentorEntity>() {

        @Override
        public boolean areItemsTheSame(@NonNull MentorEntity oldItem, @NonNull MentorEntity newItem) {
            return oldItem.getMentorId() == newItem.getMentorId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MentorEntity oldItem, @NonNull MentorEntity newItem) {
            return oldItem.getName().equals(newItem.getName()) &&  oldItem.getPhoneNumber().equals(newItem.getPhoneNumber()) &&
                    oldItem.getEmail().equals(newItem.getEmail());
        }

    };


        class MentorViewHolder extends RecyclerView.ViewHolder {
            private final TextView nameItemView;
            private final TextView phoneNumberItemView;
            private final TextView emailItemView;

            private MentorViewHolder(View itemView) {
                super(itemView);
                nameItemView = itemView.findViewById(R.id.mentor_name);
                phoneNumberItemView = itemView.findViewById(R.id.mentor_phoneNumber);
                emailItemView = itemView.findViewById(R.id.mentor_email);

                itemView.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                });
            }
        }

        public interface OnItemClickListener {
            void onItemClick(MentorEntity mentor);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }


        @Override
        public MentorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_mentor, parent, false);
            return new MentorViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MentorViewHolder holder, int position) {
//        if (mCourses != null) {
            MentorEntity current = getItem(position);
            holder.nameItemView.setText(current.getName());
            holder.phoneNumberItemView.setText(current.getPhoneNumber());
            holder.emailItemView.setText(current.getEmail());
//        } else {
//            // Covers the case of data not being ready yet.
//            holder.CourseItemView.setText("No Mentor");
//        }
        }

        private String dateFormat(Date date) {
            DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
            return format.format(date);
        }

        public MentorEntity getMentorAt(int position) {
            return getItem(position);
        }
}

