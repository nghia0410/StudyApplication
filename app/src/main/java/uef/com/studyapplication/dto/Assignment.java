package uef.com.studyapplication.dto;

public class Assignment {
    private String Course;
    private String Level;
    private String Youtube;

    private String CreateTime;
    private String StartDate;
    private String EndDate;
    private String StartTime;
    private String EndTime;

    private String Answer;
    private String SubmitTime;
    private int NumAttachments;

    public String getStartDate() {return StartDate;}
    public void setStartDate(String startDate) {StartDate = startDate;}
    public String getStartTime() {return StartTime;}
    public void setStartTime(String startTime) {StartTime = startTime;}
    public String getEndDate() {return EndDate;}
    public void setEndDate(String endDate) {EndDate = endDate;}
    public String getEndTime() {return EndTime;}
    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public int getNumAttachments() {return NumAttachments;}
    public void setNumAttachments(int numAttachments) {NumAttachments = numAttachments;}
    public String getAnswer() {return Answer;}
    public String getCreateTime() { return CreateTime; }
    public String getSubmitTime() { return SubmitTime; }
    public String getCourse() {return Course;}
    public void setCourse(String Course ) { this.Course = Course; }
    public String getYoutube() {return Youtube;}
    public void setYoutube(String Youtube ) { this.Youtube = Youtube; }
    public String getLevel() {
        return Level;
    }
    public void setLevel(String Level) {
        this.Level = Level;
    }

    public Assignment(String Course) {
        this.Course = Course;
    }
    public Assignment() {

    }
    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

}


