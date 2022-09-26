package jess.williams.c196_scheduler.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Assessment;
import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.Entity.Course_Note;
import jess.williams.c196_scheduler.Entity.Term;
import jess.williams.c196_scheduler.R;

public class NotesDetail extends AppCompatActivity {
    Repository repo = new Repository(getApplication());
    int courseID;
    int noteID;
    EditText editNoteSubject;
    EditText editNoteBody;
    String noteSubject;
    String noteBody;
    String selectedCourse;
    Spinner courseSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        courseID=getIntent().getIntExtra("courseId", -1);
        noteID = getIntent().getIntExtra("noteId", -1);
        noteSubject=getIntent().getStringExtra("noteSubject");
        noteBody=getIntent().getStringExtra("noteBody");

        editNoteSubject = findViewById(R.id.editNoteSubject);
        editNoteBody = findViewById(R.id.editNoteBody);

        // Course Spinner

        courseSpinner=(Spinner) findViewById(R.id.courseSpinner);
        List<Course> mAllCourses = new ArrayList();
        mAllCourses.addAll(repo.getAllCourses());
        int coursePosition = 0;
        List<String> courseNames = new ArrayList<>();
        for(Course course : mAllCourses){
            courseNames.add(course.getCourseTitle());
            if(course.getCourseID() == courseID){
                selectedCourse = course.getCourseTitle();
            }
        }

        ArrayAdapter<String> courseArrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, courseNames);
        courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        courseSpinner.setAdapter(courseArrayAdapter);
        coursePosition = courseArrayAdapter.getPosition(selectedCourse);
        courseSpinner.setSelection(coursePosition, false);

        editNoteSubject.setText(noteSubject);
        editNoteBody.setText(noteBody);
    }

    public void onSave(){
        Course_Note courseNote;
        noteSubject = editNoteSubject.getText().toString();
        noteBody = editNoteBody.getText().toString();

        if(noteID == -1) {
            courseNote = new Course_Note(courseID, noteSubject, noteBody);
            repo.insert(courseNote);
        }else{
            courseNote = new Course_Note(noteID, courseID, noteSubject, noteBody);
            repo.update(courseNote);
        }
    }

    private void onDelete(Course_Note courseNote) {
        courseNote.setNoteID(noteID);
        repo.delete(courseNote);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveNote:
                onSave();
                finish();
                Toast.makeText(NotesDetail.this, "Note Saved", Toast.LENGTH_LONG).show();
                return true;
            case R.id.deleteNote:
                Course_Note courseNote = new Course_Note();
                onDelete(courseNote);
                Toast.makeText(NotesDetail.this, "Note Deleted.", Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.shareNote:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TITLE, noteSubject);
                sendIntent.putExtra(Intent.EXTRA_TEXT, noteBody);
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
