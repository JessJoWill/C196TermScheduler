package jess.williams.c196_scheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses", foreignKeys = {@ForeignKey(entity = Term.class,
        parentColumns = "termID",
        childColumns = "termID",
        onDelete = ForeignKey.CASCADE)
})
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private int termID;
    private String courseTitle;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private int instructorID;

    @Ignore
    public Course(int courseID, int termID, String courseTitle, String courseStart, String courseEnd, String courseStatus, int instructorID) {
        this.courseID = courseID;
        this.termID = termID;
        this.courseTitle = courseTitle;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.instructorID = instructorID;
    }

    public Course(int termID, String courseTitle, String courseStart, String courseEnd, String courseStatus, int instructorID) {
        this.termID = termID;
        this.courseTitle = courseTitle;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.instructorID = instructorID;
    }

    public Course() {

    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }
}
