package jess.williams.c196_scheduler.Database;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private InstructorDAO mInstructorDAO;
    private NoteDAO mNoteDAO;
    private List<Term> mAllTerms;
    private List<Assessment> mAllAssessments;
    public List<Course> mAssociatedCourses;
    public List<Assessment> mAssociatedAssessments;
    public List<Instructor> mAllInstructors;
    private String termTitle;
    private String courseTitle;
    public List<Course_Note> mAssociatedNotes;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        SchedulerDBBuilder db= SchedulerDBBuilder.getDatabase(application);
        mTermDAO=db.termDAO();
        mCourseDAO=db.courseDAO();
        mAssessmentDAO=db.assessmentDAO();
        mInstructorDAO=db.instructorDAO();
        mNoteDAO=db.noteDAO();
    }

    // -----------------------------
    // Term queries
    // -----------------------------
    public List<Term>getAllTerms() {
        databaseExecutor.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public String getCurrentTermTitle(int termID){
        databaseExecutor.execute(()->{
            termTitle=mTermDAO.getCurrentTermTitle(termID);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return termTitle;
    }

    public void insert(Term term){
        databaseExecutor.execute(() -> {
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    // ---------------------
    // Course queries
    // ---------------------
    public List<Course>getAssociatedCourses(int termID) {
        databaseExecutor.execute(()->{
            mAssociatedCourses=mCourseDAO.getAssociatedCourses(termID);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAssociatedCourses;
    }

    public String getCurrentCourseTitle(int courseID){
        databaseExecutor.execute(()->{
            courseTitle=mCourseDAO.getCurrentCourseTitle(courseID);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return courseTitle;
    }

    public void insert(Course course){
        databaseExecutor.execute(() -> {
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    // ---------------------------
    // Course_Note queries
    // ---------------------------
    public List<Course_Note>getAssociatedNotes(int courseID) {
        databaseExecutor.execute(()->{
            mAssociatedNotes=mNoteDAO.getAssociatedNotes(courseID);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAssociatedNotes;
    }

    public void insert(Course_Note courseNote){
        databaseExecutor.execute(() -> {
            mNoteDAO.insert(courseNote);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course_Note courseNote){
        databaseExecutor.execute(()->{
            mNoteDAO.delete(courseNote);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    // --------------------------
    // Assessment queries
    // --------------------------
    public List<Assessment>getAllAssessments() {
        databaseExecutor.execute(()->{
            mAllAssessments=mAssessmentDAO.getAllAssessments();
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<Assessment>getAssociatedAssessments(int courseID) {
        databaseExecutor.execute(()->{
            mAssociatedAssessments=mAssessmentDAO.getAssociatedAssessments(courseID);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAssociatedAssessments;
    }

    public void insert(Assessment assessment){
        databaseExecutor.execute(() -> {
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    // -----------------------------
    // Instructor queries
    // -----------------------------
    public List<Instructor>getAllInstructors() {
        databaseExecutor.execute(()->{
            mAllInstructors=mInstructorDAO.getAllInstructors();
        });
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        return mAllInstructors;
    }

    public void insert(Instructor instructor){
        databaseExecutor.execute(() -> {
            mInstructorDAO.insert(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Instructor instructor){
        databaseExecutor.execute(()->{
            mInstructorDAO.update(instructor);
        });
        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Instructor instructor){
        databaseExecutor.execute(()->{
            mInstructorDAO.delete(instructor);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
