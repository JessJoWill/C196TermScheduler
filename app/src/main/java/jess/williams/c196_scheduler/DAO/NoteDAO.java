package jess.williams.c196_scheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import jess.williams.c196_scheduler.Entity.Course_Note;

@Dao
public interface NoteDAO {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(Course_Note courseNote);

        @Update
        void update(Course_Note courseNote);

        @Delete
        void delete(Course_Note courseNote);

        @Query("SELECT * FROM notes WHERE courseID = :courseID")
        List<Course_Note> getAssociatedNotes(int courseID);
    }

