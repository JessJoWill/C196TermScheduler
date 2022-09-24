package jess.williams.c196_scheduler.UI;

import android.app.Dialog;
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
import jess.williams.c196_scheduler.Entity.Course_Note;
import jess.williams.c196_scheduler.R;

public class CourseNotesList extends AppCompatActivity {
    Repository repo = new Repository(getApplication());
    int courseId;
    String noteTxt;
    EditText editNewNote;
    FloatingActionButton floatingSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);

        courseId = getIntent().getIntExtra("courseId", -1);

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

    void showNewNoteDialog(){
        final Dialog dialog = new Dialog(CourseNotesList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_add_note);

        editNewNote = dialog.findViewById(R.id.editNewNote);
        floatingSaveBtn = dialog.findViewById(R.id.floatingSaveBtn);

        floatingSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onSave(){
        Course_Note courseNote;
        noteTxt = String.valueOf(editNewNote.getText());
        //courseID = getIntent().getIntExtra("courseID", -1);

        courseNote = new Course_Note(courseId, noteTxt);
        repo.insert(courseNote);

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
                showNewNoteDialog();
                return true;
            case R.id.cancel:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    }
