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

import jess.williams.c196_scheduler.Entity.Assessment;
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
                    final Assessment current = mAssociatedAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("title", current.getAssessmentTitle());
                    intent.putExtra("type", current.getAssessmentType());
                    intent.putExtra("assessmentStart", current.getAssessmentStart());
                    intent.putExtra("assessmentEnd", current.getAssessmentEnd());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssociatedAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mAssociatedAssessments.size() > 0) {
            Assessment current = mAssociatedAssessments.get(position);
            String title = current.getAssessmentTitle();
            holder.courseItemView.setText(title);
        } else {
            holder.courseItemView.setText("No assessments selected");
        }
    }

    public void setmAssessments(List<Assessment> assessments) {
        mAssociatedAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssociatedAssessments != null) {
            return mAssociatedAssessments.size();
        } else {
            return 0;
        }
    }
}
