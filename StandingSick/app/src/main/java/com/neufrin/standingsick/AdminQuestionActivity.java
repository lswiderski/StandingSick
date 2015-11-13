package com.neufrin.standingsick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        if(questionId!=-1)
        {
            question = db.GetQuestion(questionId.intValue());

            EditText et = (EditText)findViewById(R.id.QuestionTitle);
            et.setText(question.getContent());

            answers = db.getAnswers(question.getId().intValue());
            LinearLayout ll = (LinearLayout)findViewById(R.id.AdminAnswersLayout);

            for (int i=0;i<answers.size();i++)
            {
                LinearLayout la = new LinearLayout(this);
                ll.addView(la);
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ll.setOrientation(LinearLayout.HORIZONTAL);

                EditText eta = new EditText(this);
                la.addView(eta);
                eta.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                eta.setText(answers.get(i).getContent());

                Button ba = new Button(this);
                la.addView(ba);
                ba.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ba.setText("X");
                ba.setId(answers.get(i).getId().intValue());
                ba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        removeAnswer(id);
                    }
                });

            }



        }

        Button ba=(Button)findViewById(R.id.BackFromAdminQuestions);
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAdminMenu();
            }
        });
    }
    public void removeAnswer(int id)
    {

    }
    public void goToAdminMenu()
    {
        //TODO Save changes to db

        Intent i = new Intent(this,AdminActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_question, menu);
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
