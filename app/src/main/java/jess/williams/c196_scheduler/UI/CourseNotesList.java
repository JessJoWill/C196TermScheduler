package jess.williams.c196_scheduler.UI;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.Entity.Course_Note;
import jess.williams.c196_scheduler.R;

public class CourseNotesList extends AppCompatActivity {
    Repository repo = new Repository(getApplication());
    int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);

        courseID = getIntent().getIntExtra("courseId", -1);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.courseNotesRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Course_Note> notes = repo.getAssociatedNotes(courseID);
        final CourseNotesAdapter adapter = new CourseNotesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setmAssociatedNotes(notes);


    }

    public void toNoteDetail(){
        Intent intent = new Intent(CourseNotesList.this, NotesDetail.class);
        intent.putExtra("courseId", courseID);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_notes, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.addNote:
                toNoteDetail();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        RecyclerView recyclerView = findViewById(R.id.courseNotesRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Course_Note> notes = repo.getAssociatedNotes(courseID);
        final CourseNotesAdapter adapter = new CourseNotesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setmAssociatedNotes(notes);
    }
}
