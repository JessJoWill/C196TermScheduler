package jess.williams.c196_scheduler.UI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jess.williams.c196_scheduler.Entity.Course_Note;
import jess.williams.c196_scheduler.R;

public class CourseNotesAdapter extends RecyclerView.Adapter<jess.williams.c196_scheduler.UI.CourseNotesAdapter.CourseNotesViewHolder> {
    class CourseNotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNotesView;

        private CourseNotesViewHolder(View itemView) {
            super(itemView);
            courseNotesView = itemView.findViewById(R.id.recyclerTextView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course_Note current = mAssociatedNotes.get(position);
                    Intent intent = new Intent(context, NotesDetail.class);
                    intent.putExtra("noteId", current.getNoteID());
                    intent.putExtra("courseId", current.getCourseID());
                    intent.putExtra("noteSubject", current.getNoteSubject());
                    intent.putExtra("noteBody", current.getNoteBody());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course_Note> mAssociatedNotes;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseNotesAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_list_item, parent, false);
        return new CourseNotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseNotesViewHolder holder, int position) {
        if (mAssociatedNotes.size() > 0) {
            Course_Note current = mAssociatedNotes.get(position);
            String noteBody = current.getNoteBody();
            String noteSubject = current.getNoteSubject();
            holder.courseNotesView.setText(noteSubject + ": " +noteBody);
        } else {
            holder.courseNotesView.setText("No notes added");
        }
    }

    public void setmAssociatedNotes(List<Course_Note> courseNotes) {
        mAssociatedNotes = courseNotes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssociatedNotes != null) {
            return mAssociatedNotes.size();
        } else {
            return 0;
        }
    }

}
