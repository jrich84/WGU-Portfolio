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

import com.example.vulcanuniversity.AssessmentActivity;
import com.example.vulcanuniversity.R;
import com.example.vulcanuniversity.database.AssessmentEntity;

import java.util.Date;
import java.util.List;

public class AssessmentListAdapter extends ListAdapter<AssessmentEntity, AssessmentListAdapter.AssessmentViewHolder> {
    private OnItemClickListener listener;
    private List<AssessmentEntity> mAssessments;

    public AssessmentListAdapter(AssessmentActivity AssessmentActivity){
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<AssessmentEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<AssessmentEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull AssessmentEntity oldItem, @NonNull AssessmentEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AssessmentEntity oldItem, @NonNull AssessmentEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getType().equals(newItem.getType())
                    && oldItem.getDueDate().equals(newItem.getDueDate())
                    && oldItem.getCourseId()== newItem.getCourseId();
        }
    };

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleItemView;
        private final TextView typeItemView;
        private final TextView dueDateItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.title);
            typeItemView = itemView.findViewById(R.id.type);
            dueDateItemView = itemView.findViewById(R.id.dueDate);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(AssessmentEntity Assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

//    public AssessmentListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_assessment, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
            AssessmentEntity current = getItem(position);
            holder.titleItemView.setText(current.getTitle());
            holder.typeItemView.setText(current.getType());
            holder.dueDateItemView.setText(dateFormat(current.getDueDate()));
//            how to handle a course
    }

    private String dateFormat(Date date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
        return format.format(date);
    }

    public AssessmentEntity getAssessmentAt(int position){
        return getItem(position);
    }


    public void setAssessments(List<AssessmentEntity> Assessments){
        mAssessments = Assessments;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
//    @Override
//    public int getItemCount() {
//        if (mAssessments != null)
//            return mAssessments.size();
//        else return 0;
//    }
}
