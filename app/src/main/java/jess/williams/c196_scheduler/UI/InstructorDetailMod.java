package jess.williams.c196_scheduler.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Instructor;
import jess.williams.c196_scheduler.R;

public class InstructorDetailMod extends AppCompatActivity {
    Repository repo = new Repository(getApplication());

    int courseID;
    int instructorID;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_detail);
        instructorID=getIntent().getIntExtra("instructorId", -1);
        instructorName=getIntent().getStringExtra("instructorName");
        instructorPhone=getIntent().getStringExtra("instructorPhone");
        instructorEmail=getIntent().getStringExtra("instructorEmail");
        editInstructorName=(EditText) findViewById(R.id.editInstructorName);
        editInstructorPhone=(EditText) findViewById(R.id.editInstructorPhone);
        editInstructorEmail=(EditText) findViewById(R.id.editInstructorEmail);

        editInstructorName.setText(instructorName);
        editInstructorPhone.setText(instructorPhone);
        editInstructorEmail.setText(instructorEmail);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_instructor_mod_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveInstructor:
                onSave(courseID);
                Toast.makeText(InstructorDetailMod.this, "Instructor Saved", Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.deleteInstructor:
                Instructor instructor = new Instructor();
                onDelete(instructor);
                Toast.makeText(InstructorDetailMod.this, "Instructor Deleted", Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.cancel:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSave(int view) {
        Instructor instructor;
        instructorName = editInstructorName.getText().toString();
        instructorEmail = editInstructorEmail.getText().toString();
        instructorPhone = editInstructorPhone.getText().toString();

        if (instructorID == -1) {
            instructor = new Instructor(instructorName, instructorPhone, instructorEmail);
            repo.insert(instructor);
            finish();
        }else{
            instructor = new Instructor(instructorID, instructorName, instructorPhone, instructorEmail);
            repo.update(instructor);
            finish();
        }
    }
    private void onDelete(Instructor instructor){
        instructor.setInstructorID(instructorID);
        repo.delete(instructor);

    }
}
