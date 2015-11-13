package com.neufrin.standingsick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        db = new DatabaseHandler(this);

        LinearLayout ll = (LinearLayout)findViewById(R.id.AdminQuestionsLayout);
        List<Question> questions = db.GetQuestions();
        final Button[] b = new Button[questions.size()];
        for(int i=0; i<questions.size();i++)
        {
            b[i]= new Button(this);
            ll.addView(b[i]);
            b[i].setText(questions.get(i).getContent());
            b[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            b[i].setId(questions.get(i).getId().intValue());
            b[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id =v.getId();
                    editQuestion(id);
                }
            });
        }

        Button ba=(Button)findViewById(R.id.backFromAdmin);
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainMenu();
            }
        });
        Button bq=(Button)findViewById(R.id.addNewQuestion);
        bq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewQuestion();
            }
        });
    }

    public void goToMainMenu()
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void addNewQuestion()
    {
        Intent i = new Intent(this,AdminQuestionActivity.class);
        startActivity(i);
    }
    public void editQuestion(int id)
    {
        Intent i = new Intent(this,AdminQuestionActivity.class);
        i.putExtra("questionId",Long.valueOf(id));
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
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
