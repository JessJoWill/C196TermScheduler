package jess.williams.c196_scheduler.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    int courseID;
    int termID;
    int instructorID;
    String courseStart;
    String courseEnd;
    String courseTitle;
    String termTitle;
    String statusStr;
    TextView instructorEmail;
    TextView instructorPhone;
    String email;
    String phone;
    Button courseStartBtn;
    Button courseEndBtn;
    Spinner statusSpinner;
    Spinner instructorSpinner;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        courseID=getIntent().getIntExtra("courseId", -1);
        termID=getIntent().getIntExtra("termId", -1);
        courseTitle=getIntent().getStringExtra("courseTitle");
        courseStart=getIntent().getStringExtra("courseStart");
        courseEnd=getIntent().getStringExtra("courseEnd");
        statusStr=getIntent().getStringExtra("courseStatus");
        instructorID=getIntent().getIntExtra("instructorId",-1);
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        courseStartBtn=findViewById(R.id.courseStartBtn);
        courseStartBtn.setText(courseStart);

        courseStartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Date date;
                String startInfo = courseStartBtn.getText().toString();
                try{
                    startCalendar.setTime(sdf.parse(startInfo));
                } catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this,startDate,startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseEndBtn=findViewById(R.id.courseEndBtn);
        courseEndBtn.setText(courseEnd);

        courseEndBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Date date;
                String endInfo = courseEndBtn.getText().toString();
                try{
                    endCalendar.setTime(sdf.parse(endInfo));
                } catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this,endDate,endCalendar.get(Calendar.YEAR),
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

        editCourseTitle=(EditText) findViewById(R.id.editCourseTitle);
        editTermTitle=(EditText) findViewById(R.id.editTermTitle);

        termTitle = repo.getCurrentTermTitle(termID);

        int statusPosition = 0;
        String[] courseStatus = {"In Progress", "Completed", "Dropped", "Plan to Take"};
        for(int i=0; i<4; i++){
            if(courseStatus[i] == statusStr);
            statusPosition = i;
        }
        // Status Spinner
        statusSpinner=(Spinner) findViewById(R.id.statusSpinner);
        ArrayAdapter<String> statusAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,courseStatus);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setSelection(statusPosition);

        // Instructor Spinner

        instructorSpinner=(Spinner) findViewById(R.id.instructorSpinner);
        List<Instructor> mAllInstructors = new ArrayList();
        mAllInstructors.addAll(repo.getAllInstructors());
        int instructorPosition = 0;
        List<String> instructorNames = new ArrayList<>();
        for(Instructor instructor : mAllInstructors){
            instructorNames.add(instructor.getInstructorName());
            if(instructor.getInstructorID() == instructorID){
                instructorPosition = instructorID - 1;
                email = instructor.getInstructorEmail();
                phone = instructor.getInstructorPhone();
            }
        }

        ArrayAdapter<String> instructorArrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, instructorNames);
        instructorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        instructorSpinner.setAdapter(instructorArrayAdapter);
        instructorSpinner.setSelection(instructorPosition, false);

        editCourseTitle.setText(courseTitle);
        editTermTitle.setText(termTitle);
        instructorEmail = (TextView) findViewById(R.id.instructorEmailTxt);
        instructorPhone = (TextView) findViewById(R.id.instructorPhoneTxt);

        getInstructorDetails();
        instructorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                instructorID = instructorSpinner.getSelectedItemPosition();

                Instructor currentInstructor = mAllInstructors.get(instructorID);
                email = currentInstructor.getInstructorEmail();
                phone = currentInstructor.getInstructorPhone();
                getInstructorDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                instructorEmail.setText("");
                instructorPhone.setText("");
            }
        });


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

    public void getInstructorDetails(){
        instructorEmail.setText(email);
        instructorPhone.setText(phone);
    }

    private void updateLabel(){
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        courseStartBtn.setText(sdf.format(startCalendar.getTime()));
        courseEndBtn.setText(sdf.format(endCalendar.getTime()));
    }

    public void onSave(int view){
        Course course;
        String start = courseStartBtn.getText().toString();
        String end = courseEndBtn.getText().toString();

        if(courseID == -1){
            course = new Course(termID, courseTitle, start, end, statusStr,instructorID);
            repo.insert(course);
            finish();
        } else {
            course = new Course(courseID, termID, courseTitle, start, end,statusStr,instructorID);
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
            case R.id.courseNotes:
                courseNotes();
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

    private void courseNotes(){
        Intent toCourseNotes = new Intent(CourseDetail.this, CourseNotesList.class);
        toCourseNotes.putExtra("courseId", courseID);
        toCourseNotes.putExtra("courseId", termID);
        toCourseNotes.putExtra("courseId", courseTitle);
        toCourseNotes.putExtra("courseId", courseStart);
        toCourseNotes.putExtra("courseId", courseEnd);
        toCourseNotes.putExtra("courseId", statusStr);
        toCourseNotes.putExtra("courseId", instructorID);
        startActivity(toCourseNotes);
    }
}
