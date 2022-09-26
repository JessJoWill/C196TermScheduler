package jess.williams.c196_scheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Insert;
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
    String noteSubject;
    String noteBody;

    @Ignore
    public Course_Note(int noteID, int courseID, String noteSubject, String noteBody) {
        this.noteID = noteID;
        this.courseID = courseID;
        this.noteSubject = noteSubject;
        this.noteBody = noteBody;
    }

    public Course_Note(int courseID, String noteSubject, String noteBody) {
        this.courseID = courseID;
        this.noteSubject = noteSubject;
        this.noteBody = noteBody;
    }

    public Course_Note() {

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

    public String getNoteSubject() {
        return noteSubject;
    }

    public void setNoteSubject(String noteSubject) {
        this.noteSubject = noteSubject;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }
}
