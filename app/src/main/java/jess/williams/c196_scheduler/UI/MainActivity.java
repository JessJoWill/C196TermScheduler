package jess.williams.c196_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Assessment;
import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.Entity.Course_Note;
import jess.williams.c196_scheduler.Entity.Instructor;
import jess.williams.c196_scheduler.Entity.Term;
import jess.williams.c196_scheduler.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void begin(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);

        /*Repository repo=new Repository(getApplication());
        Instructor instructor1 = new Instructor("Oliver Clarke", "555-664-3674", "oclarke@school.edu");
        Instructor instructor2 = new Instructor("Charlotte Hansen", "555-623-4912", "chansen@school.org");
        Instructor instructor3 = new Instructor("Steven Black", "555-623-8267", "sblack@school.org");
        Instructor instructor4 = new Instructor("Sydney Burns", "555-664-4067", "sburns@school.org");
        Instructor instructor5 = new Instructor("Jayne Davidson", "555-664-3846", "jdavidson@school.org");
        repo.insert(instructor1);
        repo.insert(instructor2);
        repo.insert(instructor3);
        repo.insert(instructor4);
        repo.insert(instructor5);
        Term term1 = new Term("First Term", "5/1/22", "10/31/22");
        Term term2 = new Term("Second Term", "11/1/22", "4/30/23");
        Term term3 = new Term("Third Term", "5/1/23", "10/31/23");
        Term term4 = new Term("Fourth Term", "11/1/23", "4/30/24");
        repo.insert(term1);
        repo.insert(term2);
        repo.insert(term3);
        repo.insert(term4);
        Course course1 = new Course(1, "Introduction to IT", "5/1/22", "6/30/22", "completed", 5);
        Course course2 = new Course(1, "Scripting and Programming - Foundations", "7/1/22", "8/31/22", "completed", 2);
        Course course3 = new Course(1, "Web Development Foundations", "9/1/22", "10/31/22", "in progress", 3);
        Course course4 = new Course(2, "Network and Security", "11/1/22", "12/31/22", "plan to take", 1);
        Course course5 = new Course(2, "Data Structures and Algorithms I", "1/1/23", "2/28/23", "plan to take", 4);
        Course course6 = new Course(2, "IT Foundations", "3/1/23", "4/30/23", "plan to take", 1);
        Course course7 = new Course(3, "IT Applications", "5/1/23", "6/30/23", "plan to take", 3);
        Course course8 = new Course(3, "Scripting and Programming - Applications", "7/1/23", "8/31/23", "plan to take", 5);
        Course course9 = new Course(3, "Data Management - Foundations", "9/1/23", "10/31/23", "plan to take", 2);
        Course course10 = new Course(4, "Data Management - Applications", "11/1/23", "12/31/23", "plan to take", 3);
        Course course11 = new Course(4, "Advanced Data Management", "1/1/24", "2/28/24", "plan to take", 5);
        Course course12 = new Course(4, "Business of IT - Project Management", "3/1/24", "4/30/24", "plan to take", 4);
        repo.insert(course1);
        repo.insert(course2);
        repo.insert(course3);
        repo.insert(course4);
        repo.insert(course5);
        repo.insert(course6);
        repo.insert(course7);
        repo.insert(course8);
        repo.insert(course9);
        repo.insert(course10);
        repo.insert(course11);
        repo.insert(course12);
        Assessment assessment1 = new Assessment(1,"C182-O","objective","6/30/22","6/30/22");
        Assessment assessment2 = new Assessment(2,"C173-O","objective","8/31/22","8/31/22");
        Assessment assessment3 = new Assessment (3,"C779-O","objective","8/31/22","8/31/22");
        Assessment assessment4 = new Assessment(4,"C172-O","objective","12/31/22","12/31/22");
        Assessment assessment5 = new Assessment(5,"C949-O","objective","2/28/23","2/28/23");
        Assessment assessment6 = new Assessment(6,"C393-O","objective","4/30/23","4/30/23");
        Assessment assessment7 = new Assessment(7,"C394-O","objective","6/30/23","6/30/23");
        Assessment assessment8 = new Assessment(8,"C867-P","performance","8/31/23","8/31/23");
        Assessment assessment9 = new Assessment(9,"C175-O","objective","10/31/23","10/31/23");
        Assessment assessment10 = new Assessment(10,"C170-P","performance","12/31/23","12/31/23");
        Assessment assessment11 = new Assessment(11,"C191-P","performance","2/28/24","2/28/24");
        Assessment assessment12 = new Assessment(12,"C176-O","objective","4/30/24","4/30/24");
        repo.insert(assessment1);
        repo.insert(assessment2);
        repo.insert(assessment3);
        repo.insert(assessment4);
        repo.insert(assessment5);
        repo.insert(assessment6);
        repo.insert(assessment7);
        repo.insert(assessment8);
        repo.insert(assessment9);
        repo.insert(assessment10);
        repo.insert(assessment11);
        repo.insert(assessment12);
        Course_Note courseNote1 = new Course_Note(1, "Lorem ipsum dolor sit amet consectetur adipiscing elit, urna consequat felis vehicula class ultricies mollis dictumst, aenean non a in donec nulla. Phasellus ante pellentesque erat cum risus consequat imperdiet aliquam, integer placerat et turpis mi eros nec lobortis taciti, vehicula nisl litora tellus ligula porttitor metus.");
        Course_Note courseNote2 = new Course_Note(2, "Note for Course 2");
        Course_Note courseNote3 = new Course_Note(3, "Note for Course 3");
        Course_Note courseNote4 = new Course_Note(4, "Note for Course 4");
        Course_Note courseNote5 = new Course_Note(5, "Note for Course 5");
        Course_Note courseNote6 = new Course_Note(6, "Note for Course 6");
        Course_Note courseNote7 = new Course_Note(7, "Note for Course 7");
        Course_Note courseNote8 = new Course_Note(8, "Note for Course 8");
        Course_Note courseNote9 = new Course_Note(9, "Note for Course 9");
        Course_Note courseNote10 = new Course_Note(10, "Note for Course 10");
        Course_Note courseNote11 = new Course_Note(11, "Note for Course 11");
        Course_Note courseNote12 = new Course_Note(12, "Note for Course 12");
        repo.insert(courseNote1);
        repo.insert(courseNote2);
        repo.insert(courseNote3);
        repo.insert(courseNote4);
        repo.insert(courseNote5);
        repo.insert(courseNote6);
        repo.insert(courseNote7);
        repo.insert(courseNote8);
        repo.insert(courseNote9);
        repo.insert(courseNote10);
        repo.insert(courseNote11);
        repo.insert(courseNote12);*/
    }
}