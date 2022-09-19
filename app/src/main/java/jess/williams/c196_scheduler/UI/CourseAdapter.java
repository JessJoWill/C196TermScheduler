package jess.williams.c196_scheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.Entity.Term;
import jess.williams.c196_scheduler.R;

public class CourseAdapter extends RecyclerView.Adapter<jess.williams.c196_scheduler.UI.CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.recyclerTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mAssociatedCourses.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("termId", current.getTermID());
                    intent.putExtra("title", current.getCourseTitle());
                    intent.putExtra("courseStart", current.getCourseStart());
                    intent.putExtra("courseEnd", current.getCourseEnd());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("courseNotes", current.getCourseNotes());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mAssociatedCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public jess.williams.c196_scheduler.UI.CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_list_item, parent, false);
        return new jess.williams.c196_scheduler.UI.CourseAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull jess.williams.c196_scheduler.UI.CourseAdapter.CourseViewHolder holder, int position) {
        if (mAssociatedCourses != null) {
            Course current = mAssociatedCourses.get(position);
            String title = current.getCourseTitle();
            holder.courseItemView.setText(title);
        } else {
            holder.courseItemView.setText("No course selected");
        }
    }

    public void setmCourses(List<Course> courses) {
        mAssociatedCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssociatedCourses != null) {
            return mAssociatedCourses.size();
        } else {
            return 0;
        }
    }
}
