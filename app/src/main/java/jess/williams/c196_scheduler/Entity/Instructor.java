package jess.williams.c196_scheduler.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors")
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    int instructorID;
    String instructorName;
    String instructorPhone;
    String instructorEmail;

    @Ignore
    public Instructor(int instructorID, String instructorName, String instructorPhone, String instructorEmail) {
        this.instructorID = instructorID;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
    }

    public Instructor(String instructorName, String instructorPhone, String instructorEmail) {
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
    }
    @Ignore
    public Instructor() {

    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "instructorID=" + instructorID +
                ", instructorName='" + instructorName + '\'' +
                '}';
    }
}
