package jess.williams.c196_scheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Assessment;
import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.R;

public class AssessmentDetail extends AppCompatActivity {
    Repository repo = new Repository(getApplication());
    EditText editAssessmentTitle;
    EditText editCourseTitle;
    Spinner typeSpinner;
    EditText editAssessmentStart;
    EditText editAssessmentEnd;

    int assessmentID;
    int courseID;
    String assessmentTitle;
    String assessmentType;
    String assessmentStart;
    String assessmentEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
    }




    public void onSave(int view){
        Assessment assessment;

        if(assessmentID == -1){
            assessment = new Assessment(courseID, assessmentTitle, assessmentType, assessmentStart, assessmentEnd);
            repo.insert(assessment);
            finish();
        } else {
            assessment = new Assessment(assessmentID, courseID, assessmentTitle, assessmentType, assessmentStart, assessmentEnd);
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
                return true;
            case R.id.deleteAssessment:
                Assessment assessment = new Assessment();
                onDelete(assessment);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onDelete(Assessment assessment) {
        assessment.setAssessmentID(assessmentID);
        repo.delete(assessment);
    }
}
