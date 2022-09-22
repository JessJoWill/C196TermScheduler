package jess.williams.c196_scheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Assessment;
import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.Entity.Instructor;
import jess.williams.c196_scheduler.Entity.Term;
import jess.williams.c196_scheduler.R;

public class CourseDetail extends AppCompatActivity {
    Repository repo = new Repository(getApplication());
    EditText editCourseTitle;
    EditText editTermTitle;
    EditText editStartDate;
    EditText editEndDate;
    Spinner statusSpinner;
    Spinner instructorSpinner;

    int courseID;
    int termID;
    String courseTitle;
    String termTitle;
    String startDate;
    String endDate;
    int instructorID;
    String statusStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        editCourseTitle=(EditText) findViewById(R.id.editCourseTitle);
        editTermTitle=(EditText) findViewById(R.id.editTermTitle);
        editStartDate=(EditText) findViewById(R.id.editStartDate);
        editEndDate=(EditText) findViewById(R.id.editEndDate);
        statusSpinner=(Spinner) findViewById(R.id.statusSpinner);
        instructorSpinner=(Spinner) findViewById(R.id.instructorSpinner);
        courseID=getIntent().getIntExtra("courseId", -1);
        termID=getIntent().getIntExtra("termId", -1);
        courseTitle=getIntent().getStringExtra("courseTitle");
        startDate=getIntent().getStringExtra("courseStart");
        endDate=getIntent().getStringExtra("courseEnd");
        statusStr=getIntent().getStringExtra("courseStatus");
        instructorID=getIntent().getIntExtra("instructorId", -1);

        String termTitle = repo.getCurrentTermTitle(termID);

        int position = 0;
        String[] courseStatus = {"In Progress", "Completed", "Dropped", "Plan to Take"};
        for(int i=0; i<4; i++){
            if(courseStatus[i] == statusStr);
            position = i;
        }
        // Status Spinner
        Spinner statusSpinner=(Spinner) findViewById(R.id.statusSpinner);
        ArrayAdapter<String> statusAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,courseStatus);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setSelection(position);

        // Instructor Spinner

        Spinner instructorSpinner=(Spinner) findViewById(R.id.instructorSpinner);
        List<Instructor> mAllInstructors = new ArrayList<>();


        // ****************************************
        // ****************************************
        // Fucking method isn't returning anything
        // ****************************************
        // ****************************************


        repo.getAllInstructors();
        ArrayAdapter<Instructor> instructorArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mAllInstructors);
        instructorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        instructorSpinner.setAdapter(instructorArrayAdapter);
        // instructorSpinner.setSelection(instructorID);

        editCourseTitle.setText(courseTitle);
        editTermTitle.setText(termTitle);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Assessment> assessments = repo.getAssociatedAssessments(courseID);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setmAssessments(assessments);
    }



    public void onSave(int view){
        Course course;

        if(courseID == -1){
            course = new Course(termID, courseTitle, startDate, endDate, statusStr,instructorID);
            repo.insert(course);
            finish();
        } else {
            course = new Course(courseID, termID, courseTitle, startDate, endDate,statusStr,instructorID);
            repo.update(course);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveCourse:
                onSave(courseID);
                finish();
                return true;
            case R.id.deleteCourse:
                Course course = new Course();
                onDelete(course);
                finish();
                return true;
            case R.id.addAssessment:
                addAssessment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onDelete(Course course) {
        course.setCourseID(courseID);
        repo.delete(course);
    }

    private void addAssessment() {
        Intent toAddAssessment = new Intent(CourseDetail.this, AssessmentDetail.class);
        startActivity(toAddAssessment);
    }
}
