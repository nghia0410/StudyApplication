package uef.com.studyapplication;

import java.util.Date;

public class RowItem {
    private String course;
    private String youtube;
    private int bgcolors;
    private String submitdate;
    private String level;
    private String date;
    private int mListid;

    public RowItem(String course, int i, String submitTime) {
        this.course = course;
        this.bgcolors = i;
        this.date = submitTime;
    }

    public int getListid() {
        return mListid;
    }

    public void setListid(int listid) {
        mListid = listid;
    }

    public RowItem(){
    };
    private Date DayNotification;
    public RowItem(String course, String youtube,int bgcolors) {
        this.course = course;
        this.youtube = youtube;
        this.bgcolors = bgcolors;
    }

    public RowItem(String course, int bgcolors, Date dayNotification) {
        this.course = course;
        this.bgcolors = bgcolors;
        DayNotification = dayNotification;
    }
    public int getBgcolors() { return bgcolors; }
    public void setBgcolors(int bgcolors) { this.bgcolors = bgcolors; }
    public String getYoutube () {
        return youtube;
    }
    public void setYoutube(String youtube) { this.youtube = youtube;  }
    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public String getLevel() {return level;}
    public void setLevel(String level) {this.level = level;}
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date;}
    public String getSubmitdate() { return submitdate; }
    public void setSubmitdate(String submitdate) { this.submitdate = submitdate; }
}

