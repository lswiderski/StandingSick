package com.neufrin.standingsick;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by neufrin on 13.11.2015.
 */
public class QuestionBundle {

    private Question question;
    private List<Answer> answers;

    public QuestionBundle()
    {
        answers = new LinkedList<>();
    }
    public void AddAnswer(Answer answer)
    {
        answers.add(answer);
    }
    public List<Answer> GetAnswers()
    {
        return answers;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
