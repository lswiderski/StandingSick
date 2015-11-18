package com.neufrin.standingsick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Button b = (Button)findViewById(R.id.PasswordButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();
            }
        });
    }
    public void checkPassword()
    {
        EditText eT = (EditText)findViewById(R.id.PasswordEditText);
        if(eT.getText().toString().equalsIgnoreCase("admin123"))
        {
            goToAdmin();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
            goToMenu();
        }
    }
    public void goToAdmin()
    {
        Intent i = new Intent(this,AdminActivity.class);
        startActivity(i);
    }
    public void goToMenu()
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
