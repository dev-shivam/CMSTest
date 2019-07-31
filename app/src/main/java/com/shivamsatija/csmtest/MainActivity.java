package com.shivamsatija.csmtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((EditText) findViewById(R.id.et_link)).setText("http://student-mobile.classplus.co/token=eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjYyNywibmFtZSI6IlNoYXJpcSBTdHUiLCJlbWFpbCI6InNoYXJAZy5jb20iLCJtb2JpbGUiOiI5MTk4OTk4MDU5OTMiLCJzdHVkZW50SWQiOjMzNSwiYmF0Y2hUZXN0SWQiOjE3MDksInRpbWVzdGFtcCI6MTU2Mzk1Mjg0MjY2MiwidGVzdElkIjoiNWQyYzRhYTA4NGY3ZjgzY2I2OWU1Y2Y5IiwibnVtYmVyT2ZBdHRlbXB0cyI6MTAwLCJpYXQiOjE1NjM5NTI4NDIsImV4cCI6MTU2NTY4MDg0Mn0._5-W-9nYrA3Dl4iWykD-gFY_gcqR5MWquf0xHMB00SJ2klF77MdP1tHoar4vhhnX&testId=5d2c4aa084f7f83cb69e5cf9&user_email=shar@g.com&user_id=1709&studentTestId=5d3806fb7d8b7a6c6bd7de06");

        findViewById(R.id.btn_open_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CMSWebviewActivity.class);
                intent.putExtra(CMSWebviewActivity.PARAM_CMS_URL,
                        ((EditText) findViewById(R.id.et_link)).getText().toString());
                startActivity(intent);
            }
        });
    }
}
