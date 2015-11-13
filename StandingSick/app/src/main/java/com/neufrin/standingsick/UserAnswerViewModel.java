package com.neufrin.standingsick;

/**
 * Created by neufrin on 13.11.2015.
 */
public class UserAnswerViewModel {
    private String Question;
    private String Answer;
    private Long SessionId;

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public Long getSessionId() {
        return SessionId;
    }

    public void setSessionId(Long sessionId) {
        SessionId = sessionId;
    }
}
