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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import jess.williams.c196_scheduler.R;

public class AssessmentDetail extends AppCompatActivity {
    Repository repo = new Repository(getApplication());
    EditText editAssessmentTitle;
    int assessmentID;
    int courseID;
    String assessmentTitle;
    String assessmentType;
    String assessmentStart;
    String assessmentEnd;
    String selectedCourse;
    List<Course> mAllCourses;
    Button assessmentStartBtn;
    Button assessmentEndBtn;
    Spinner courseTitleSpinner;
    Spinner typeSpinner;
    SimpleDateFormat sdf;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        assessmentID=getIntent().getIntExtra("assessmentId", -1);
        courseID=getIntent().getIntExtra("courseId", -1);
        assessmentTitle=getIntent().getStringExtra("title");
        assessmentStart=getIntent().getStringExtra("assessmentStart");
        assessmentEnd=getIntent().getStringExtra("assessmentEnd");
        assessmentType=getIntent().getStringExtra("assessmentType");
        String dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        assessmentStartBtn=findViewById(R.id.assessmentStartBtn);
        assessmentStartBtn.setText(assessmentStart);

        assessmentStartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Date date;
                String startInfo = assessmentStartBtn.getText().toString();
                try{
                    startCalendar.setTime(sdf.parse(startInfo));
                }catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this,startDate,startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        assessmentEndBtn=findViewById(R.id.assessmentEndBtn);
        assessmentEndBtn.setText(assessmentEnd);

        assessmentEndBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Date date;
                String endInfo = assessmentEndBtn.getText().toString();
                try{
                    endCalendar.setTime(sdf.parse(endInfo));
                }catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this,endDate,endCalendar.get(Calendar.YEAR),
                        endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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

        editAssessmentTitle=(EditText) findViewById(R.id.editAssessmentTitle);

        // Course Title Spinner

        courseTitleSpinner=(Spinner) findViewById(R.id.courseTitleSpinner);
        mAllCourses = new ArrayList();
        mAllCourses.addAll(repo.getAllCourses());
        int coursePosition = 0;
        List<String> courseTitles = new ArrayList<>();
        for(Course course : mAllCourses){
            courseTitles.add(course.getCourseTitle());
            if(course.getCourseID() == courseID){
                selectedCourse = course.getCourseTitle();
            }
        }

        ArrayAdapter<String> courseArrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, courseTitles);
        courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        courseTitleSpinner.setAdapter(courseArrayAdapter);
        coursePosition = courseArrayAdapter.getPosition(selectedCourse);
        courseTitleSpinner.setSelection(coursePosition, false);

        int statusPosition = 0;
        String[] assessmentTypeArray = {"Objective", "Performance"};
        if(assessmentType == "Objective"){
            statusPosition = 1;
        }else if(assessmentType == "Performance"){
            statusPosition = 2;
        }

        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,assessmentTypeArray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setSelection(statusPosition);

        editAssessmentTitle.setText(assessmentTitle);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void updateLabel(){
        String dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);

        assessmentStartBtn.setText(sdf.format(startCalendar.getTime()));
        assessmentEndBtn.setText(sdf.format(endCalendar.getTime()));
    }

    public void onSave(int view){
        Assessment assessment;
        String title = editAssessmentTitle.getText().toString();
        String type = typeSpinner.getSelectedItem().toString();
        String start = assessmentStartBtn.getText().toString();
        String end = assessmentEndBtn.getText().toString();

        if(assessmentID == -1){
            assessment = new Assessment(courseID, title, type, start, end);
            repo.insert(assessment);
            finish();
        } else {
            assessment = new Assessment(assessmentID, courseID, title, type, start, end);
            repo.update(assessment);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveAssessment:
                onSave(assessmentID);
                finish();
                Toast.makeText(AssessmentDetail.this, "Assessment Saved", Toast.LENGTH_LONG).show();
                return true;
            case R.id.deleteAssessment:
                Assessment assessment = new Assessment();
                onDelete(assessment);
                finish();
                Toast.makeText(AssessmentDetail.this, "Assessment Deleted", Toast.LENGTH_LONG).show();
                return true;
            case R.id.assessmentStartAlert:
                String startDateTxt=assessmentStartBtn.getText().toString();
                Date aStartDate = null;
                try{
                    aStartDate=sdf.parse(startDateTxt);
                }catch(ParseException e){
                    e.printStackTrace();
                }
                Long saTrigger = aStartDate.getTime();
                Intent saIntent = new Intent(AssessmentDetail.this, MyReceiver.class);
                assessmentTitle = editAssessmentTitle.getText().toString();
                saIntent.putExtra("key", assessmentTitle + " starts today.");
                PendingIntent saSender = PendingIntent.getBroadcast(AssessmentDetail.this,MainActivity.numAlert++,saIntent,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,saTrigger,saSender);
                Toast.makeText(AssessmentDetail.this, "Alert Added", Toast.LENGTH_LONG).show();
                return true;
            case R.id.assessmentEndAlert:
                String endDateTxt=assessmentEndBtn.getText().toString();
                Date endDate = null;
                try{
                    endDate=sdf.parse(endDateTxt);
                }catch(ParseException e){
                    e.printStackTrace();
                }
                Long endTrigger = endDate.getTime();
                Intent eaIntent = new Intent(AssessmentDetail.this, MyReceiver.class);
                eaIntent.putExtra("key", assessmentTitle + " ends today.");
                PendingIntent eaSender = PendingIntent.getBroadcast(AssessmentDetail.this,MainActivity.numAlert++,eaIntent,0);
                AlarmManager eaAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                eaAlarmManager.set(AlarmManager.RTC_WAKEUP,endTrigger,eaSender);
                Toast.makeText(AssessmentDetail.this, "Alert Added", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onDelete(Assessment assessment) {
        assessment.setAssessmentID(assessmentID);
        repo.delete(assessment);
    }
}
