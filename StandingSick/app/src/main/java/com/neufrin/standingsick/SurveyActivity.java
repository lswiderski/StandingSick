package com.neufrin.standingsick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class SurveyActivity extends AppCompatActivity {

    private Session actualSession;
    private List<QuestionBundle> questions;
    private QuestionBundle actualQuestion;
    private int actualQuestionIterator;
    private int questionsCount;
    private int selectedAnswer;

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Date d = new Date();
        d.setTime(getIntent().getLongExtra("session", -1));

        db = new DatabaseHandler(this);
        db.addSession(new Session(d));
        actualSession = db.getLastSession();

        questions = db.GetQuestionBundles();
        questionsCount = questions.size();
        actualQuestionIterator=0;

        selectedAnswer=-1;
        setQuestion();
        Button b=(Button)findViewById(R.id.ButtonNext);
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        };
        b.setOnClickListener(l);
    }
    public void goToResult()
    {
        Intent i = new Intent(this,ResultActivity.class);
        i.putExtra("sessionId",actualSession.getId());
        startActivity(i);
    }
    public void saveAnswer()
    {
        RadioGroup answersGroup = (RadioGroup)findViewById(R.id.SurveyRadio);
        int id = answersGroup.getCheckedRadioButtonId();
        selectedAnswer = id;

        if(id==-1)
        {
            //no answer

        }
        else
        {
            UserAnswer ua = new UserAnswer();
            ua.setSessionId(actualSession.getId());
            ua.setAId((Long.valueOf(selectedAnswer)));
            ua.setQId(actualQuestion.getQuestion().getId());
            ua.setAnswer(((RadioButton)findViewById(answersGroup.getCheckedRadioButtonId())).getText().toString());
            ua.setQuestion(actualQuestion.getQuestion().getContent());
            db.addUserAnswer(ua);
        }
    }
    public void nextQuestion()
    {
        saveAnswer();

        if(actualQuestionIterator<questionsCount-1)
        {
            actualQuestionIterator++;
            setQuestion();
        }
        else
        {
            goToResult();
        }
    }
    public void setQuestion()
    {
        actualQuestion = questions.get(actualQuestionIterator);
        TextView questionTv = (TextView)findViewById(R.id.Question);
        questionTv.setText(actualQuestion.getQuestion().getContent());

        int answersCount = actualQuestion.getAnswers().size();
        RadioGroup answersGroup = (RadioGroup)findViewById(R.id.SurveyRadio);
        answersGroup.removeAllViews();
        answersGroup.clearCheck();
        final RadioButton[] rb = new RadioButton[answersCount];
        for (int i=0;i<answersCount;i++)
        {
            rb[i] = new RadioButton(this);
            answersGroup.addView(rb[i]);
            rb[i].setText(actualQuestion.getAnswers().get(i).getContent());
            rb[i].setId(actualQuestion.getAnswers().get(i).getId().intValue());
        }


    }
}
