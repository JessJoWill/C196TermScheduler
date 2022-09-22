package jess.williams.c196_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jess.williams.c196_scheduler.Database.Repository;
import jess.williams.c196_scheduler.Entity.Assessment;
import jess.williams.c196_scheduler.Entity.Course;
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

        Repository repo=new Repository(getApplication());
        /*Instructor instructor1 = new Instructor("Oliver Clarke", "555-664-3674", "oclarke@school.edu");
        Instructor instructor2 = new Instructor("Charlotte Hansen", "555-623-4912", "chansen@school.org");
        Instructor instructor3 = new Instructor("Steven Black", "555-623-8267", "sblack@school.org");
        Instructor instructor4 = new Instructor("Sydney Burns", "555-664-4067", "sburns@school.org");
        Instructor instructor5 = new Instructor("Jayne Davidson", "555-664-3846", "jdavidson@school.org");
        repo.insert(instructor1);
        repo.insert(instructor2);
        repo.insert(instructor3);
        repo.insert(instructor4);
        repo.insert(instructor5);
        Term term1 = new Term("First Term", "2022-05-01", "2022-10-31");
        Term term2 = new Term("Second Term", "2022-11-01", "2023-4-30");
        Term term3 = new Term("Third Term", "2023-05-01", "2023-10-31");
        Term term4 = new Term("Fourth Term", "2023-11-01", "2024-04-30");
        repo.insert(term1);
        repo.insert(term2);
        repo.insert(term3);
        repo.insert(term4);
        Course course1 = new Course(1, "Introduction to IT", "2022-05-01", "2022-06-30", "completed", 5);
        Course course2 = new Course(1, "Scripting and Programming - Foundations", "2022-07-01", "2022-08-31", "completed", 2);
        Course course3 = new Course(1, "Web Development Foundations", "2022-09-01", "2022-10-31", "in progress", 3);
        Course course4 = new Course(2, "Network and Security", "2022-11-01", "2022-12-31", "plan to take", 1);
        Course course5 = new Course(2, "Data Structures and Algorithms I", "2023-01-01", "2023-02-28", "plan to take", 4);
        Course course6 = new Course(2, "IT Foundations", "2023-03-01", "2023-04-30", "plan to take", 1);
        Course course7 = new Course(3, "IT Applications", "2023-05-01", "2023-06-30", "plan to take", 3);
        Course course8 = new Course(3, "Scripting and Programming - Applications", "2023-07-01", "2023-08-31", "plan to take", 5);
        Course course9 = new Course(3, "Data Management - Foundations", "2023-09-01", "2023-10-31", "plan to take", 2);
        Course course10 = new Course(4, "Data Management - Applications", "2023-11-01", "2023-12-31", "plan to take", 3);
        Course course11 = new Course(4, "Advanced Data Management", "2024-01-01", "2024-02-28", "plan to take", 5);
        Course course12 = new Course(4, "Business of IT - Project Management", "2024-03-01", "2024-04-30", "plan to take", 4);
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
        Assessment assessment1 = new Assessment(1,"C182-O","objective","2022-06-30","2022-06-30");
        Assessment assessment2 = new Assessment(2,"C173-O","objective","2022-08-31","2022-08-31");
        Assessment assessment3 = new Assessment (3,"C779-O","objective","2022-10-31","2022-10-31");
        Assessment assessment4 = new Assessment(4,"C172-O","objective","2022-12-31","2022-12-31");
        Assessment assessment5 = new Assessment(5,"C949-O","objective","2023-02-28","2023-02-28");
        Assessment assessment6 = new Assessment(6,"C393-O","objective","2023-04-30","2023-04-30");
        Assessment assessment7 = new Assessment(7,"C394-O","objective","2023-06-30","2023-06-30");
        Assessment assessment8 = new Assessment(8,"C867-P","performance","2023-08-31","2023-08-31");
        Assessment assessment9 = new Assessment(9,"C175-O","objective","2023-10-31","2023-10-31");
        Assessment assessment10 = new Assessment(10,"C170-P","performance","2023-12-31","2023-12-31");
        Assessment assessment11 = new Assessment(11,"C191-P","performance","2024-02-28","2024-02-28");
        Assessment assessment12 = new Assessment(12,"C176-O","objective","2024-04-30","2024-04-30");
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
        repo.insert(assessment12);*/
    }
}