package com.testcom.attendon;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Class1Absence extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class1_absence);
        Intent i = getIntent();
        setTitle(i.getStringExtra("lol"));
    }
}
