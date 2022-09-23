package jess.williams.c196_scheduler.Entity;

import android.app.DatePickerDialog;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termTitle;
    private String termStart;
    private String termEnd;

    @Ignore
    public Term(int termID, String termTitle, String termStart, String termEnd) {
        this.termID = termID;
        this.termTitle = termTitle;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public Term(String termTitle, String termStart, String termEnd) {
        this.termTitle = termTitle;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public Term() {

    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }
}
