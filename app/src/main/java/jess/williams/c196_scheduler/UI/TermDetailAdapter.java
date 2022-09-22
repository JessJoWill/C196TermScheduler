package jess.williams.c196_scheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.Entity.Term;
import jess.williams.c196_scheduler.R;

public class TermDetailAdapter extends RecyclerView.Adapter<TermDetailAdapter.TermDetailViewHolder> {
    class TermDetailViewHolder extends RecyclerView.ViewHolder {
        private final TextView termDetailItemView;

        private TermDetailViewHolder(View itemView) {
            super(itemView);
            termDetailItemView = itemView.findViewById(R.id.recyclerTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mAssociatedCourses.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("courseId", current.getCourseID());
                    intent.putExtra("termId", current.getTermID());
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("courseStart", current.getCourseStart());
                    intent.putExtra("courseEnd", current.getCourseEnd());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("instructorId", current.getInstructorID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mAssociatedCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermDetailAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermDetailAdapter.TermDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_list_item, parent, false);
        return new TermDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermDetailAdapter.TermDetailViewHolder holder, int position) {
        if (mAssociatedCourses.size() > 0) {
            Course current = mAssociatedCourses.get(position);
            String title = current.getCourseTitle();
            holder.termDetailItemView.setText(title);
        } else {
            holder.termDetailItemView.setText("No courses associated");
        }
    }

    public void setmCourses(List<Course> courses) {
        mAssociatedCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssociatedCourses != null){
            return mAssociatedCourses.size();
        } else {
            return 0;
        }
    }
}
