package com.neufrin.standingsick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        db = new DatabaseHandler(this);
       Long sessionId = getIntent().getLongExtra("sessionId", -1);
        TextView tv = (TextView)findViewById(R.id.result);
        tv.setText("");
        for(UserAnswerViewModel q:db.getUserAnswer(sessionId.intValue()))
        {
            tv.setText(tv.getText() + "\n" + q.getQuestion() + ": " + q.getAnswer());
        }

        Button bb=(Button)findViewById(R.id.BackButton);
        View.OnClickListener l1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        };
        bb.setOnClickListener(l1);
        Button bs=(Button)findViewById(R.id.SendButton);
        View.OnClickListener l2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendResult();
            }
        };
        bs.setOnClickListener(l2);
    }

    public void goBack()
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void SendResult()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
