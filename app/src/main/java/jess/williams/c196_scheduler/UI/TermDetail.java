package jess.williams.c196_scheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.Entity.Term;
import jess.williams.c196_scheduler.R;

public class TermDetail extends AppCompatActivity {
    Repository repo = new Repository(getApplication());
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    int termID;
    String title;
    String termStart;
    String termEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        editTitle=(EditText) findViewById(R.id.editTermTitle);
        editStart=(EditText) findViewById(R.id.editStartDate);
        editEnd=(EditText) findViewById(R.id.editEndDate);
        termID=getIntent().getIntExtra("id", -1);
        title=getIntent().getStringExtra("title");
        termStart=getIntent().getStringExtra("termStart");
        termEnd=getIntent().getStringExtra("termEnd");
        editTitle.setText(title);
        editStart.setText(termStart);
        editEnd.setText(termEnd);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAssociatedCourses(termID);
        final TermDetailAdapter adapter = new TermDetailAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setmCourses(courses);
    }

    public void onSave(int view) {
        Term term;

        if(termID == -1){
            int newID = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermID() + 1;
            term = new Term(newID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repo.insert(term);
        }
        else{
            term = new Term(termID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repo.update(term);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveTerm:
                onSave(termID);
                return true;
            case R.id.deleteTerm:
                // onDelete(termID);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

 /*   private void onDelete(int view) {
        List<Course> mAssociatedCourses = null;
        repo.getAssociatedCourses(termID);
        if(mAssociatedCourses.size() == 0){
            new AlertDialog.Builder(this)
                    .setTitle("Delete Term")
                    .setMessage("Do you really want to delete " + title + "?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            TermDAO.deleteByTermId(termID);
                            Toast.makeText(TermDetail.this, title + " deleted.", Toast.LENGTH_SHORT).show();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();

        }
    }*/

    private void addCourse() {
        Intent toAddCourse = new Intent(TermDetail.this, CourseDetail.class);
           startActivity(toAddCourse);
    }
}
