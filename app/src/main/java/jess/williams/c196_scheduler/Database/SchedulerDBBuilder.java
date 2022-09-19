package jess.williams.c196_scheduler.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import jess.williams.c196_scheduler.DAO.AssessmentDAO;
import jess.williams.c196_scheduler.DAO.CourseDAO;
import jess.williams.c196_scheduler.DAO.TermDAO;
import jess.williams.c196_scheduler.Entity.Assessment;
import jess.williams.c196_scheduler.Entity.Course;
import jess.williams.c196_scheduler.Entity.Term;

@Database(entities={Term.class, Course.class, Assessment.class}, version=3, exportSchema = false)
public abstract class SchedulerDBBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

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