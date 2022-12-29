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

import com.example.vulcanuniversity.CourseActivity;
import com.example.vulcanuniversity.R;
import com.example.vulcanuniversity.database.AppRepository;
import com.example.vulcanuniversity.database.CourseEntity;

import java.util.Date;

public class CourseListAdapter extends ListAdapter<CourseEntity, CourseListAdapter.CourseViewHolder> {
    private OnItemClickListener listener;
    private AppRepository mRepository;

    public CourseListAdapter(CourseActivity CourseActivity){
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<CourseEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<CourseEntity>() {

        @Override
        public boolean areItemsTheSame(@NonNull CourseEntity oldItem, @NonNull CourseEntity newItem) {
            return oldItem.getCourseId() == newItem.getCourseId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CourseEntity oldItem, @NonNull CourseEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getStatus().equals(newItem.getStatus()) &&
                    oldItem.getStartDate().equals(newItem.getStartDate())
                    && oldItem.getEndDate().equals(newItem.getEndDate());
        }
    };

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleItemView;
        private final TextView statusItemView;
        private final TextView startDateItemView;
        private final TextView endDateItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.title);
            statusItemView = itemView.findViewById(R.id.status);
            startDateItemView = itemView.findViewById(R.id.course_startDate);
            endDateItemView = itemView.findViewById(R.id.course_endDate);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CourseEntity Course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
//        if (mCourses != null) {
            CourseEntity current = getItem(position);
            holder.titleItemView.setText(current.getTitle());
            holder.statusItemView.setText(current.getStatus());
            holder.startDateItemView.setText(dateFormat(current.getStartDate()));
            holder.endDateItemView.setText(dateFormat(current.getEndDate()));
//        } else {
//            // Covers the case of data not being ready yet.
//            holder.CourseItemView.setText("No Word");
//        }
    }

    private String dateFormat(Date date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
        return format.format(date);
    }

    public CourseEntity getCourseAt(int position){
        return getItem(position);
    }
}
