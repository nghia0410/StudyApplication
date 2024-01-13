package uef.com.studyapplication.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class NewAssignment implements Parcelable {
    public enum AssignmentLevel {
        easy,
        medium,
        hard;


        @NonNull
        @Override
        public String toString() {
            switch (this) {
                case easy:
                    return "Easy";
                case medium:
                    return "Medium";
                case hard:
                    return "Hard";
                default:
                    return "";
            }
        }

        public static AssignmentLevel fromString(String value) {
            switch (value) {
                case "medium":
                case "Medium":
                    return AssignmentLevel.medium;
                case "hard":
                case "Hard":
                    return AssignmentLevel.hard;
                case "easy":
                case "Easy":
                default:
                    return AssignmentLevel.easy;
            }
        }
    }

    public NewAssignment(
            String uuid,
            String course,
            String createTime,
            AssignmentLevel level,
            int numAttachments,
            String startDate,
            String endDate,
            String startTime,
            String endTime,
            String youtube,
            List<Question> questions,
            List<String> submitter,
            List<String> attachments
    ) {
        this.uuid = uuid;
        this.course = course;
        this.createTime = createTime;
        this.level = level;
        this.numAttachments = numAttachments;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.youtube = youtube;
        this.questions = questions;
        this.submitter = submitter;
        this.attachments = attachments;
    }

    ///Assignment của user đã submit

    private String uuid;
    private String course;
    private String createTime;
    private AssignmentLevel level;
    private int numAttachments;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String youtube;
    private List<Question> questions;

    ///Danh sách id của những người đã submit
    private List<String> submitter;

    ///Danh sách các đường dẫn của tệp
    private List<String> attachments;

    public String getDate() {
        return getEndDate() + " (" + getEndTime()+ ")" ;
    }



    public NewAssignment() {
    }

    protected NewAssignment(Parcel in) {
        uuid = in.readString();
        course = in.readString();
        level = AssignmentLevel.fromString(in.readString());
        createTime = in.readString();
        numAttachments = in.readInt();
        startDate = in.readString();
        endDate = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        youtube = in.readString();
        submitter = new ArrayList<>();
        in.readList(submitter, String.class.getClassLoader());
        questions = new ArrayList<>();
        in.readList(questions, Question.class.getClassLoader());
        attachments = new ArrayList<>();
        in.readList(attachments, String.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(course);
        dest.writeString(level.toString());
        dest.writeString(createTime);
        dest.writeInt(numAttachments);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(youtube);
        dest.writeList(submitter);
        dest.writeList(questions);
        dest.writeList(attachments);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewAssignment> CREATOR = new Creator<NewAssignment>() {
        @Override
        public NewAssignment createFromParcel(Parcel in) {
            return new NewAssignment(in);
        }

        @Override
        public NewAssignment[] newArray(int size) {
            return new NewAssignment[size];
        }
    };

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getSubmitter() {
        return submitter;
    }

    public void setSubmitter(List<String> submitter) {
        this.submitter = submitter;
    }

    public List<Question> getQuestions() {
        List<Question> q = new ArrayList<>(questions);
        return q;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getUuid() {
        return uuid;
    }


    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public AssignmentLevel getLevel() {
        return level;
    }

    public void setLevel(AssignmentLevel level) {
        this.level = level;
    }

    public int getNumAttachments() {
        return numAttachments;
    }

    public void setNumAttachments(int numAttachments) {
        this.numAttachments = numAttachments;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "NewAssignment{" +
                "uuid='" + uuid + '\'' +
                ", course='" + course + '\'' +
                ", createTime='" + createTime + '\'' +
                ", level=" + level +
                ", numAttachments=" + numAttachments +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", youtube='" + youtube + '\'' +
                ", questions=" + questions +
                ", submitter=" + submitter +
                ", attachments=" + attachments +
                '}';
    }
}
