package jess.williams.c196_scheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import jess.williams.c196_scheduler.Entity.Term;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM terms")
    List<Term> getAllTerms();

   @Query("SELECT termTitle FROM terms WHERE termID = :termID")
    String getCurrentTermTitle(int termID);
}
