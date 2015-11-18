package com.neufrin.standingsick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

public class AdminQuestionActivity extends AppCompatActivity {

    DatabaseHandler db;
    Question question;
    List<Answer> answers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_question);
        db = new DatabaseHandler(this);
        Long questionId = getIntent().getLongExtra("questionId", -1);
        final LinearLayout la[];
        final EditText eta[];
        final Button ba[];
        if(questionId!=-1)
        {
            question = db.GetQuestion(questionId.intValue());

            EditText et = (EditText)findViewById(R.id.QuestionTitle);
            et.setText(question.getContent());

            answers = db.getAnswers(question.getId().intValue());
            LinearLayout ll = (LinearLayout)findViewById(R.id.AdminAnswersLayout);
            la = new LinearLayout[answers.size()];
            eta = new EditText[answers.size()];
            ba = new Button[answers.size()];
            for (int i=0;i<answers.size();i++)
            {
                la[i] = new LinearLayout(this);
                ll.addView(la[i]);
                la[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                la[i].setOrientation(LinearLayout.HORIZONTAL);

                eta[i] = new EditText(this);
                la[i].addView(eta[i]);
                eta[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                eta[i].setText(answers.get(i).getContent());

                ba[i] = new Button(this);
                la[i].addView(ba[i]);
                ba[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ba[i].setText("X");
                ba[i].setId(answers.get(i).getId().intValue());
                ba[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        removeAnswer(id);
                    }
                });
            }
        }
        else
        {
            question = new Question();
            question.setContent("");
            question.setId(Long.valueOf(db.getLastQuestionId()+1));
            db.AddQuestion(question);
        }

        Button back=(Button)findViewById(R.id.BackFromAdminQuestions);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndGoToAdminMenu();
            }
        });

        Button addAnswer=(Button)findViewById(R.id.AddAnswer);
        addAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnswer();
            }
        });

        Button rq=(Button)findViewById(R.id.RemoveQuestion);
        rq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeQuestion();
            }
        });
    }
    public void removeQuestion()
    {
        db.removeQuestion(question.getId().intValue());
        goToAdminMenu();
    }
    public void removeAnswer(int id)
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.AdminAnswersLayout);

        Button Xbutton = (Button)findViewById(id);
        ll.removeView((ViewGroup) Xbutton.getParent());
        db.removeAnswer(id);

    }
    public void addAnswer()
    {
        Answer ans = new Answer();
        ans.setContent("");
        ans.setQId(question.getId());
        db.AddAnswer(ans);

        LinearLayout ll = (LinearLayout)findViewById(R.id.AdminAnswersLayout);
        LinearLayout la = new LinearLayout(this);
        ll.addView(la);
        la.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        la.setOrientation(LinearLayout.HORIZONTAL);

        EditText eta = new EditText(this);
        la.addView(eta);
        eta.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));

        Button ba = new Button(this);
        la.addView(ba);
        ba.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ba.setText("X");
        ba.setId(db.getLastAnswerId());
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                removeAnswer(id);
            }
        });

    }
    public void saveAndGoToAdminMenu()
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.AdminAnswersLayout);
        int buttons = ll.getChildCount();
        for (int i=0;i<buttons;i++)
        {
            Answer answer = new Answer();
            answer.setQId(question.getId());
            LinearLayout la = (LinearLayout)ll.getChildAt(i);
            answer.setContent(((EditText)la.getChildAt(0)).getText().toString());
            answer.setId(Long.valueOf(((Button) la.getChildAt(1)).getId()));
            db.updateAnswer(answer);
        }

        question.setContent(((EditText) findViewById(R.id.QuestionTitle)).getText().toString());
        db.updateQuestion(question);
        goToAdminMenu();


    }
    public void goToAdminMenu() {
        Intent i = new Intent(this,AdminActivity.class);
        startActivity(i);
    }


}
