package jess.williams.c196_scheduler.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import jess.williams.c196_scheduler.DAO.AssessmentDAO;
import jess.williams.c196_scheduler.DAO.CourseDAO;
import jess.williams.c196_scheduler.DAO.InstructorDAO;
import jess.williams.c196_scheduler.DAO.NoteDAO;
import jess.williams.c196_scheduler.DAO.TermDAO;
import jess.williams.c196_scheduler.Entity.Assessment;
import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.Entity.Course_Note;
import jess.williams.c196_scheduler.Entity.Instructor;
import jess.williams.c196_scheduler.Entity.Term;

@Database(entities={Term.class, Course.class, Assessment.class, Instructor.class, Course_Note.class}, version=9, exportSchema = false)
public abstract class SchedulerDBBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract InstructorDAO instructorDAO();
    public abstract NoteDAO noteDAO();

    private static volatile SchedulerDBBuilder INSTANCE;

    static SchedulerDBBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchedulerDBBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchedulerDBBuilder.class, "mySchedulerDBBuilder.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}