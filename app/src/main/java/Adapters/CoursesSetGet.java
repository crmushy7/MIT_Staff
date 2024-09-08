package Adapters;

public class CoursesSetGet {
    private String courseName;
    private String courseDuration;
    private String courseID;
    private String courseDescription;

    public CoursesSetGet(String courseName, String courseDuration, String courseID, String courseDescription) {
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.courseID = courseID;
        this.courseDescription = courseDescription;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
}
