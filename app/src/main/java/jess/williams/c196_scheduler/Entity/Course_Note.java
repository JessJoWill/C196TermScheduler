package jess.williams.c196_scheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "notes", foreignKeys = {@ForeignKey(entity = Course.class,
        parentColumns = "courseID",
        childColumns = "courseID",
        onDelete = ForeignKey.CASCADE)
})


public class Course_Note {
    @PrimaryKey(autoGenerate = true)
    int noteID;
    int courseID;
    String noteTxt;

    public Course_Note(int courseID, String noteTxt) {
        this.courseID = courseID;
        this.noteTxt = noteTxt;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getNoteTxt() {
        return noteTxt;
    }

    public void setNoteTxt(String noteTxt) {
        this.noteTxt = noteTxt;
    }
}
