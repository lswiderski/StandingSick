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
    private Long actualQuestionId;
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
        actualQuestionId = questions.get(actualQuestionIterator).getQuestion().getId();
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

        //db.AddQuestion(new Question("Do you have a fever?"));
        //db.AddQuestion(new Question("Do you have a headache?"));
        //db.AddAnswer(new Answer("Yes", 0L));
        //db.AddAnswer(new Answer("No", 0L));
        //db.AddAnswer(new Answer("Yes", 1L));
        //db.AddAnswer(new Answer("No", 1L));
        //TextView tv = (TextView)findViewById(R.id.textView1);
        //tv.setText("");
        //for(QuestionBundle q:db.GetQuestionBundles())
        //{
        //    tv.setText(tv.getText() + "\n" + q.getQuestion().getId() + " " + q.getQuestion().getContent());
        //    for(Answer a:q.GetAnswers())
        //    {
        //        tv.setText(tv.getText()+ "\n"+a.getId() + " " +a.getContent() + " " +a.getQId());
        //    }
        //}
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
            db.addUserAnswer(ua);
        }
    }
    public void nextQuestion()
    {
        saveAnswer();

        if(actualQuestionIterator<questionsCount-1)
        {
            if(selectedAnswer!=-1)
            {
                actualQuestionIterator++;
                actualQuestionId = questions.get(actualQuestionIterator).getQuestion().getId();
            }

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
