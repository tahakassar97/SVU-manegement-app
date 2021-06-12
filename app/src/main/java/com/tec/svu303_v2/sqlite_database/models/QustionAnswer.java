package com.tec.svu303_v2.sqlite_database.models;

public class QustionAnswer {
    private int questionId;
    private String questionTitle;
    private int grade;
    private int answerId;
    private String answerTitle;
    private int status;

    public QustionAnswer(int questionId, String questionTitle, int grade, int answerId, String answerTitle, int status) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.grade = grade;
        this.answerId = answerId;
        this.answerTitle = answerTitle;
        this.status = status;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public int getGrade() {
        return grade;
    }

    public int getAnswerId() {
        return answerId;
    }

    public String getAnswerTitle() {
        return answerTitle;
    }

    public int getStatus() {
        return status;
    }
}
