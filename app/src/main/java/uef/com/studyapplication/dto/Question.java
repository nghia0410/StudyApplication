package uef.com.studyapplication.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

//public class Question implements Serializable {
//    private String question;
//    private List<String> options;
//    private int answer;
//
//    public Question() {
//    }
//
//    public Question(String question, List<String> options, int answer) {
//        this.question = question;
//        this.options = options;
//        this.answer = answer;
//    }
//
//    public String getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(String question) {
//        this.question = question;
//    }
//
//    public List<String> getOptions() {
//        return options;
//    }
//
//    public void setOptions(List<String> options) {
//        this.options = options;
//    }
//
//    public int getAnswer() {
//        return answer;
//    }
//
//    public void setAnswer(int answer) {
//        this.answer = answer;
//    }
//
//    @Override
//    public String toString() {
//        return "Question{" +
//                "question='" + question + '\'' +
//                ", options=" + options.toString() +
//                ", answer=" + answer +
//                '}';
//    }
////
////    protected Question(Parcel in) {
////        question = in.readString();
////        options = in.createStringArray();
////        answer = in.readInt();
////    }
////
////    @Override
////    public int describeContents() {
////        return 0;
////    }
////
////    @Override
////    public void writeToParcel(Parcel dest, int flags) {
////        dest.writeString(question);
////        dest.writeArray(options);
////        dest.writeInt(answer);
////    }
////
////    public static final Parcelable.Creator<Question> CREATOR = new Creator<Question>() {
////        @Override
////        public Question createFromParcel(Parcel in) {
////            return new Question(in);
////        }
////
////        @Override
////        public Question[] newArray(int size) {
////            return new Question[size];
////        }
////    };
//}
public class Question implements Parcelable {
    private String question;
    private List<String> options;
    private List<Integer> answer;
    private String type;

    public Question() {
    }

    public Question(String question, List<String> options, List<Integer> answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public Question(String question,  List<String>  options, List<Integer> answer, String type) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.type = type;
    }

    protected Question(Parcel in) {
        question = in.readString();
        options = new ArrayList<>();
        in.readList(options, String.class.getClassLoader());
        answer = new ArrayList<>();
        in.readList(answer, Integer.class.getClassLoader());
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeList(options);
        dest.writeList(answer);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", options=" + (options.toString()) +
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
