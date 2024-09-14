package Adapters;

public class RequestsSetGet {
    private String requestID;
    private String studentName;
    private String studentID;
    private String courseName;
    private String requestTime;
    private String requestStatus;

    public RequestsSetGet(String requestID, String studentName, String studentID, String courseName, String requestTime, String requestStatus) {
        this.requestID = requestID;
        this.studentName = studentName;
        this.studentID = studentID;
        this.courseName = courseName;
        this.requestTime = requestTime;
        this.requestStatus = requestStatus;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
