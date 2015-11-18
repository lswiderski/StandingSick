package com.neufrin.standingsick;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        db = new DatabaseHandler(this);
       Long sessionId = getIntent().getLongExtra("sessionId", -1);
        TextView tv = (TextView)findViewById(R.id.result);
        tv.setText("");
        content = "";
        for(UserAnswerViewModel q:db.getUserAnswer(sessionId.intValue()))
        {
            content = content + "\n" + q.getQuestion() + ":" + q.getAnswer();
        }
        tv.setText(content);
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

        Button bsave=(Button)findViewById(R.id.SaveButton);
        View.OnClickListener l3 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveResult();
            }
        };
        bsave.setOnClickListener(l3);
    }

    public void goBack()
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void SendResult()
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "medical report");
        i.putExtra(Intent.EXTRA_TEXT, content);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ResultActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
    public void SaveResult()
    {
        if(!isExternalStorageWritable())
        {
            Toast.makeText(ResultActivity.this, "Cannot Save the file", Toast.LENGTH_SHORT).show();
            return;
        }
        File path = getFileStorageDir("StandingSick");
        File report = new File(path,"medical_report.txt");
        try {
            FileOutputStream f = new FileOutputStream(report);
            PrintWriter pw = new PrintWriter(f);
            pw.println(content);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(ResultActivity.this, "Error", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(ResultActivity.this, "Report saved at \\Documents\\StandingSick\\medical_report.txt", Toast.LENGTH_SHORT).show();
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    public File getFileStorageDir(String filePath) {
        // Get the directory for the user's public documents directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), filePath);

        return file;
    }
}
