package uef.com.studyapplication.dto;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class UserAssignment extends NewAssignment {
    private String submitTime;
    private Integer points;

    public UserAssignment() {
    }

    public UserAssignment(String uuid,
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
                          List<String> attachments,
                          String submitTime,
                          Integer points
    ) {
        super(uuid, course, createTime, level, numAttachments, startDate, endDate, startTime, endTime, youtube, questions, submitter, attachments);
        this.submitTime = submitTime;
        this.points = points;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserAssignment{" +
                "submitTime='" + submitTime + '\'' +
                "} " + super.toString();
    }

    public UserAssignment(NewAssignment assignment, String submitTime) {
        super(
                assignment.getUuid(),
                assignment.getCourse(),
                assignment.getCreateTime(),
                assignment.getLevel(),
                assignment.getNumAttachments(),
                assignment.getStartDate(),
                assignment.getEndDate(),
                assignment.getStartTime(),
                assignment.getEndTime(),
                assignment.getYoutube(),
                assignment.getQuestions(),
                assignment.getSubmitter(),
                assignment.getAttachments()
        );
        this.submitTime = submitTime;
    }

    public UserAssignment(NewAssignment assignment) {
        super(
                assignment.getUuid(),
                assignment.getCourse(),
                assignment.getCreateTime(),
                assignment.getLevel(),
                assignment.getNumAttachments(),
                assignment.getStartDate(),
                assignment.getEndDate(),
                assignment.getStartTime(),
                assignment.getEndTime(),
                assignment.getYoutube(),
                new ArrayList<>(),
                assignment.getSubmitter(),
                assignment.getAttachments()
        );
        List<Question> questions = new ArrayList<>();
//        questions.addAll(assignment.getQuestions());
//        questions.forEach(question -> {
//            question.getAnswer().clear();
//        });

        for (Question question : assignment.getQuestions()) {
            questions.add(new Question(question.getQuestion(),question.getOptions(),new ArrayList<>(question.getAnswer()),question.getType())); // Assuming a deep copy constructor for Question
        }
        setQuestions(questions);
        this.submitTime = null;
    }

    public Integer getPoints() {
        if(points == null) return 0;
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void calculatePoints(List<Question> questions) {
        setPoints(0);
        ///Báo lỗi khi cả 2 ds không đều
        assert (questions.size() == getQuestions().size());
        for (int i = 0; i < questions.size(); i++) {
            List<Integer> questionAnswers = questions.get(i).getAnswer();
            List<Integer> userAnswers = getQuestions().get(i).getAnswer();
            Log.d("QUESTION ANSWER",questionAnswers.toString());
            Log.d("USER ANSWER",userAnswers.toString());
            boolean matched = compareLists(questionAnswers, userAnswers);
            if (matched)
                points += 1;
        }
    }

    public String getSubmitTime() {
        if(submitTime == null) return "";
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    ///So sánh 2 danh sách câu trả lời
    private static boolean compareLists(List<Integer> questionAnswers, List<Integer> userAnswer) {
        // Nếu số lượng phần tử khác nhau, danh sách không giống nhau
        if (questionAnswers.size() != userAnswer.size()) {
            return false;
        }

        // Sắp xếp danh sách để so sánh
        List<Integer> sortedList1 = new ArrayList<>(questionAnswers);
        List<Integer> sortedList2 = new ArrayList<>(userAnswer);
        sortedList1.sort(Integer::compareTo);
        sortedList2.sort(Integer::compareTo);

        // So sánh danh sách đã sắp xếp
        return sortedList1.equals(sortedList2) && sortedList2.equals(sortedList1);
    }
}

