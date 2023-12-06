package uef.com.studyapplication;

import java.util.Date;

public class RowItem {
    private String course;
    private String desc;
    private int bgcolors;
    private String submitdate;
    public int getListid() {
        return mListid;
    }

    public void setListid(int listid) {
        mListid = listid;
    }

    private int mListid;
//    private String date;
//    private String submitdate;
    private String level;
    public RowItem(){

    };
    private Date DayNotification;

    public RowItem(String course, String desc,int bgcolors) {
        this.course = course;
        this.desc = desc;
        this.bgcolors = bgcolors;
    }

    public RowItem(String course, int bgcolors, Date dayNotification) {
        this.course = course;
        this.bgcolors = bgcolors;
        DayNotification = dayNotification;
    }

//    public RowItem(String topic, int i, String startTime) {
//        this.title = topic;
//        this.bgcolors = i;
//        this.date = startTime;
//    }
//    public RowItem(String topic, String submitdate,String answer) {
//        this.title = topic;
//        this.submitdate = submitdate;
//        this.desc = answer;
//    }
//
//
//    public Date getDayNotification() {
//        return DayNotification;
//    }
//
//    public void setDayNotification(Date dayNotification) {
//        DayNotification = dayNotification;
//    }

//    public LocalTime getTimeNotification() {
//        return TimeNotification;
//    }
//
//    public void setTimeNotification(LocalTime timeNotification) {
//        TimeNotification = timeNotification;
//    }

    public int getBgcolors() { return bgcolors; }
    public void setBgcolors(int bgcolors) { this.bgcolors = bgcolors; }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = "Try it !!!";
    }
    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }
//    public String getDate() { return date; }
//    public void setDate(String date) { this.date = date;}
    public String getLevel() {return level;}
    public void setLevel(String level) {this.level = level;}
    public String getSubmitdate() { return submitdate; }

    public void setSubmitdate(String submitdate) { this.submitdate = submitdate; }
}

