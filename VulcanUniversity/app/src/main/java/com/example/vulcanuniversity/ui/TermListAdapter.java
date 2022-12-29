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

import com.example.vulcanuniversity.R;
import com.example.vulcanuniversity.TermActivity;
import com.example.vulcanuniversity.database.AppRepository;
import com.example.vulcanuniversity.database.TermEntity;

import java.util.Date;

public class TermListAdapter extends ListAdapter<TermEntity, TermListAdapter.TermViewHolder> {
    private OnItemClickListener listener;
    private AppRepository mRepository;

    public TermListAdapter(TermActivity termActivity){
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<TermEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<TermEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TermEntity oldItem, @NonNull TermEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TermEntity oldItem, @NonNull TermEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getStartDate().equals(newItem.getStartDate())
                    && oldItem.getEndDate().equals(newItem.getEndDate());
        }
    };

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private final TextView startDateItemView;
        private final TextView endDateItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.title);
            startDateItemView = itemView.findViewById(R.id.startDate);
            endDateItemView = itemView.findViewById(R.id.endDate);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TermEntity term);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

//    public TermListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_term, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
//        if (mTerms != null) {
            TermEntity current = getItem(position);
            holder.termItemView.setText(current.getTitle());
            holder.startDateItemView.setText(dateFormat(current.getStartDate()));
            holder.endDateItemView.setText(dateFormat(current.getEndDate()));
//        } else {
//            // Covers the case of data not being ready yet.
//            holder.termItemView.setText("No Word");
//        }
    }

    private String dateFormat(Date date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
        return format.format(date);
    }

    public TermEntity getTermAt(int position){
        return getItem(position);
    }

}
