package com.neufrin.standingsick;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public void startSurvey()
    {
        Intent i = new Intent(this,SurveyActivity.class);
        Date d = new Date();
        i.putExtra("session", d.getTime());
        startActivity(i);
    }
    public void goToHistory()
    {
        Intent i = new Intent(this,HistoryActivity.class);
        startActivity(i);
    }
    public void goToAdmin()
    {
        Intent i = new Intent(this,AdminActivity.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b=(Button)findViewById(R.id.button_start);
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSurvey();
            }
        };
        b.setOnClickListener(l);

        Button bh=(Button)findViewById(R.id.button_history);
        View.OnClickListener lh = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHistory();
            }
        };
        bh.setOnClickListener(lh);

        Button ba=(Button)findViewById(R.id.button_admin);
        View.OnClickListener la = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAdmin();
            }
        };
        ba.setOnClickListener(la);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
