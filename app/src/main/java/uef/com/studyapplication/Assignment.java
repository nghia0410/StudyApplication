package uef.com.studyapplication;

public class Assignment {
    private String Course;
    private String Level;
    private String Youtube;


    public int getNumAttachments() {
        return NumAttachments;
    }
    public void setNumAttachments(int numAttachments) {
        NumAttachments = numAttachments;
    }
    private int NumAttachments;
    public String getAnswer() {
        return Answer;
    }

//    public void setAnswer(String answer) {
//        Answer = answer;
//    }
    private String Answer;
    private String SubmitTime,CreateTime;
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


