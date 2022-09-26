package jess.williams.c196_scheduler.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.Toast;

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
    int courseID;
    int termID;
    int instructorID;
    String courseStart;
    String courseEnd;
    String courseTitle;
    String statusStr;
    String selectedInstructor;
    String selectedTerm;
    TextView instructorEmail;
    TextView instructorPhone;
    String instructorName;
    String email;
    String phone;
    String dateFormat;
    List<Term> mAllTerms;
    Button courseStartBtn;
    Button courseEndBtn;
    Spinner statusSpinner;
    Spinner instructorSpinner;
    Spinner termSpinner;
    SimpleDateFormat sdf;
    List<Instructor> mAllInstructors = new ArrayList();
    List<String> instructorNames = new ArrayList<>();
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
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        courseStartBtn=findViewById(R.id.courseStartBtn);
        courseStartBtn.setText(courseStart);

        courseStartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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

        // Term Spinner
        termSpinner=(Spinner) findViewById(R.id.termSpinner);
        mAllTerms = new ArrayList();
        mAllTerms.addAll(repo.getAllTerms());
        int termPosition = 0;
        List<String> termNames = new ArrayList<>();
        for(Term term : mAllTerms){
            termNames.add(term.getTermTitle());
            if(term.getTermID() == termID){
                selectedTerm = term.getTermTitle();
            }
        }

        ArrayAdapter<String> termArrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, termNames);
        termArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        termSpinner.setAdapter(termArrayAdapter);
        termPosition = termArrayAdapter.getPosition(selectedTerm);
        termSpinner.setSelection(termPosition, false);


        int statusPosition = 0;
        String[] courseStatus = {"in progress", "completed", "dropped", "plan to take"};
        int stringSize = courseStatus.length;
        for(int i=0; i<stringSize; i++){
            String checkString = courseStatus[i];
            if(Objects.equals(checkString, statusStr)) {
                statusPosition = i;
            }

        }
        // Status Spinner
        statusSpinner=(Spinner) findViewById(R.id.statusSpinner);
        ArrayAdapter<String> statusAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,courseStatus);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setSelection(statusPosition);

        // Instructor Spinner
        instructorSpinner=(Spinner) findViewById(R.id.instructorSpinner);

        mAllInstructors.addAll(repo.getAllInstructors());
        int instructorPosition = 0;

        for(Instructor instructor : mAllInstructors){
            instructorNames.add(instructor.getInstructorName());
            if(instructor.getInstructorID() == instructorID){
                selectedInstructor = instructor.getInstructorName();
                email = instructor.getInstructorEmail();
                phone = instructor.getInstructorPhone();
            }
        }

        ArrayAdapter<String> instructorArrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, instructorNames);
        instructorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        instructorSpinner.setAdapter(instructorArrayAdapter);
        instructorPosition = instructorArrayAdapter.getPosition(selectedInstructor);
        instructorSpinner.setSelection(instructorPosition, false);

        editCourseTitle.setText(courseTitle);

        instructorEmail = (TextView) findViewById(R.id.instructorEmailTxt);
        instructorPhone = (TextView) findViewById(R.id.instructorPhoneTxt);

        getInstructorDetails();

        instructorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedInstructor = instructorSpinner.getSelectedItem().toString();
                for(Instructor instructor : mAllInstructors){
                    if(instructor.getInstructorName() == selectedInstructor){
                        instructorID = instructor.getInstructorID();
                        instructorName = instructor.getInstructorName();
                        email = instructor.getInstructorEmail();
                        phone = instructor.getInstructorPhone();
                        getInstructorDetails();
                    }
                }
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
        statusStr = statusSpinner.getSelectedItem().toString();
        courseTitle = editCourseTitle.getText().toString();


        if(courseID == -1){
            course = new Course(termID, courseTitle, start, end, statusStr,instructorID);
            repo.insert(course);
            finish();
        } else {
            course = new Course(courseID, termID, courseTitle, start, end,statusStr,instructorID);
            repo.update(course);
            finish();
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
                Toast.makeText(CourseDetail.this, "Course Saved", Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.deleteCourse:

                List<Assessment> mAssociatedAssessments = repo.getAssociatedAssessments(courseID);
                if(mAssociatedAssessments.size() > 0){
                    Toast.makeText(CourseDetail.this, "Cannot delete course with associated assessments.", Toast.LENGTH_LONG).show();
                }else {
                    Course course = new Course();
                    onDelete(course);
                    Toast.makeText(CourseDetail.this, "Course Deleted", Toast.LENGTH_LONG).show();
                    finish();
                    return true;
                }
            case R.id.addInstructor:
                addInstructor();
                return true;
            case R.id.modInstructor:
                modInstructor();
                return true;
            case R.id.addAssessment:
                addAssessment();
                return true;
            case R.id.courseNotes:
                courseNotes();
                return true;
            case R.id.courseStartAlert:
                String startDateTxt=courseStartBtn.getText().toString();
                Date startDate = null;
                try{
                    startDate=sdf.parse(startDateTxt);
                }catch(ParseException e){
                    e.printStackTrace();
                }
                Long saTrigger = startDate.getTime();
                Intent saIntent = new Intent(CourseDetail.this, MyReceiver.class);
                courseTitle = editCourseTitle.getText().toString();
                saIntent.putExtra("key", courseTitle + " starts today.");
                PendingIntent saSender = PendingIntent.getBroadcast(CourseDetail.this,MainActivity.numAlert++,saIntent,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,saTrigger,saSender);
                Toast.makeText(CourseDetail.this, "Alert Added", Toast.LENGTH_LONG).show();
                return true;
            case R.id.courseEndAlert:
                String endDateTxt=courseEndBtn.getText().toString();
                Date endDate = null;
                try{
                    endDate=sdf.parse(endDateTxt);
                }catch(ParseException e){
                    e.printStackTrace();
                }
                Long endTrigger = endDate.getTime();
                Intent eaIntent = new Intent(CourseDetail.this, MyReceiver.class);
                eaIntent.putExtra("key", courseTitle + " ends today.");
                PendingIntent eaSender = PendingIntent.getBroadcast(CourseDetail.this,MainActivity.numAlert++,eaIntent,0);
                AlarmManager eaAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                eaAlarmManager.set(AlarmManager.RTC_WAKEUP,endTrigger,eaSender);
                Toast.makeText(CourseDetail.this, "Alert Added", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addInstructor() {
        Intent toAddInstructor = new Intent(CourseDetail.this, InstructorDetailAdd.class);
        toAddInstructor.putExtra("courseId", courseID);
        startActivity(toAddInstructor);
    }

    private void modInstructor() {
        Intent toModInstructor = new Intent(CourseDetail.this, InstructorDetailMod.class);
        toModInstructor.putExtra("courseId", courseID);
        toModInstructor.putExtra("instructorId", instructorID);
        toModInstructor.putExtra("instructorName", instructorName);
        toModInstructor.putExtra("instructorPhone", phone);
        toModInstructor.putExtra("instructorEmail", email);
        startActivity(toModInstructor);
    }

    private void onDelete(Course course) {
        course.setCourseID(courseID);
        repo.delete(course);
    }

    private void addAssessment() {
        if(courseID == -1){
            Toast.makeText(CourseDetail.this, "Save course before adding assessments.", Toast.LENGTH_LONG).show();
        }else {
            Intent toAddAssessment = new Intent(CourseDetail.this, AssessmentDetail.class);
            toAddAssessment.putExtra("courseId", courseID);
            startActivity(toAddAssessment);
        }
    }

    private void courseNotes(){
        Intent toCourseNotes = new Intent(CourseDetail.this, CourseNotesList.class);
        toCourseNotes.putExtra("courseId", courseID);
        toCourseNotes.putExtra("termId", termID);
        toCourseNotes.putExtra("courseTitle", courseTitle);
        toCourseNotes.putExtra("courseStart", courseStart);
        toCourseNotes.putExtra("courseEnd", courseEnd);
        toCourseNotes.putExtra("statusStr", statusStr);
        toCourseNotes.putExtra("instructorID", instructorID);
        startActivity(toCourseNotes);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Assessment> assessments = repo.getAssociatedAssessments(courseID);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setmAssessments(assessments);

        mAllInstructors.clear();
        instructorNames.clear();
        mAllInstructors.addAll(repo.getAllInstructors());
        for(Instructor instructor : mAllInstructors){
            instructorNames.add(instructor.getInstructorName());
            if(instructor.getInstructorID() == instructorID){
                selectedInstructor = instructor.getInstructorName();
                email = instructor.getInstructorEmail();
                phone = instructor.getInstructorPhone();
            }
        }
    }
}
