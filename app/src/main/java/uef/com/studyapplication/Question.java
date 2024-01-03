package uef.com.studyapplication;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private String question;
    private List<String> options;
    private int answer;

    public Question() {
    }

    public Question(String question, List<String> options, int answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", options=" + options.toString() +
                ", answer=" + answer +
                '}';
    }
//
//    protected Question(Parcel in) {
//        question = in.readString();
//        options = in.createStringArray();
//        answer = in.readInt();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(question);
//        dest.writeArray(options);
//        dest.writeInt(answer);
//    }
//
//    public static final Parcelable.Creator<Question> CREATOR = new Creator<Question>() {
//        @Override
//        public Question createFromParcel(Parcel in) {
//            return new Question(in);
//        }
//
//        @Override
//        public Question[] newArray(int size) {
//            return new Question[size];
//        }
//    };
}
