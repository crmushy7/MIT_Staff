package Adapters;

public class CoursesSetGet {
    private String courseName;
    private String courseDuration;
    private String courseID;

    public CoursesSetGet(String courseName, String courseDuration, String courseID) {
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.courseID = courseID;
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
}
