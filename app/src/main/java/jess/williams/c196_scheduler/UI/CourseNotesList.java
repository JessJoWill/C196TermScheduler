package jess.williams.c196_scheduler.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Course_Note;
import jess.williams.c196_scheduler.R;

public class CourseNotesList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);

        int courseId = getIntent().getIntExtra("courseId", -1);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.courseNotesRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Course_Note> notes = repo.getAssociatedNotes(courseId);
        final CourseNotesAdapter adapter = new CourseNotesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setmAssociatedNotes(notes);

    }
}
