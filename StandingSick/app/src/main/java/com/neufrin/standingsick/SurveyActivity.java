package com.neufrin.standingsick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        DatabaseHandler db = new DatabaseHandler(this);

        //db.AddQuestion(new Question("Do you have a fever?"));
        //db.AddQuestion(new Question("Do you have a headache?"));
        //db.AddAnswer(new Answer("Yes", 0L));
        //db.AddAnswer(new Answer("No", 0L));
        //db.AddAnswer(new Answer("Yes", 1L));
        //db.AddAnswer(new Answer("No", 1L));
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setText("");
        for(QuestionBundle q:db.GetQuestionBundles())
        {
            tv.setText(tv.getText() + "\n" + q.getQuestion().getId() + " " + q.getQuestion().getContent());
            for(Answer a:q.GetAnswers())
            {
                tv.setText(tv.getText()+ "\n"+a.getId() + " " +a.getContent() + " " +a.getQId());
            }
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
