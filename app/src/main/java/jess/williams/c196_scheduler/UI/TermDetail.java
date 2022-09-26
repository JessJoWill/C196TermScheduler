package jess.williams.c196_scheduler.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Assessment;
import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.Entity.Term;
import jess.williams.c196_scheduler.R;

public class TermDetail extends AppCompatActivity {
    Repository repo = new Repository(getApplication());
    EditText editTitle;
    int termID;
    String title;
    String termStart;
    String termEnd;
    Button termStartBtn;
    Button termEndBtn;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        termStart=getIntent().getStringExtra("termStart");
        termEnd=getIntent().getStringExtra("termEnd");
        termStartBtn=findViewById(R.id.termStartBtn);
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        termStartBtn.setText(termStart);

        termStartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Date date;
                String startInfo = termStartBtn.getText().toString();
                try{
                    startCalendar.setTime(sdf.parse(startInfo));
                } catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this,startDate,startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        termEndBtn=findViewById(R.id.termEndBtn);
        termEndBtn.setText(termEnd);

        termEndBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String endInfo = termEndBtn.getText().toString();
                try{
                    endCalendar.setTime(sdf.parse(endInfo));
                } catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this,endDate,endCalendar.get(Calendar.YEAR),
                        endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }
        };

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, monthOfYear);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }
        };

        editTitle=(EditText) findViewById(R.id.editTermTitle);
        termID=getIntent().getIntExtra("id", -1);
        title=getIntent().getStringExtra("title");
        editTitle.setText(title);

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

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        termStartBtn.setText(sdf.format(startCalendar.getTime()));
        termEndBtn.setText(sdf.format(endCalendar.getTime()));
    }

    public void onSave(int view) {
        Term term;
        String start = termStartBtn.getText().toString();
        String end = termEndBtn.getText().toString();
        String title = editTitle.getText().toString();

        if(termID == -1){
            term = new Term(title, start, end);
            repo.insert(term);
            finish();
        }
        else{
            term = new Term(termID, title, start, end);
            repo.update(term);
            finish();
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
                finish();
                Toast.makeText(TermDetail.this, "Term Saved", Toast.LENGTH_LONG).show();
                return true;
            case R.id.deleteTerm:
                List<Course> mAssociatedCourses = repo.getAssociatedCourses(termID);
                if(mAssociatedCourses.size() > 0){
                    Toast.makeText(TermDetail.this, "Cannot delete term with associated courses.", Toast.LENGTH_LONG).show();
                }else {
                    Term term = new Term();
                    onDelete(term);
                    Toast.makeText(TermDetail.this, "Term Deleted", Toast.LENGTH_LONG).show();
                    finish();
                    return true;
                }
            case R.id.addCourse:
                addCourse();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onDelete(Term term) {
        term.setTermID(termID);
        repo.delete(term);
        finish();
    }

    private void addCourse() {
        Intent toAddCourse = new Intent(TermDetail.this, CourseDetail.class);
        toAddCourse.putExtra("termId", termID);
           startActivity(toAddCourse);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAssociatedCourses(termID);
        final TermDetailAdapter adapter = new TermDetailAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setmCourses(courses);
    }
}
