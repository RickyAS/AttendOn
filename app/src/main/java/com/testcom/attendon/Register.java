package com.testcom.attendon;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {
EditText register_firstname, register_lastname, register_username, register_email, register_password;
TextView register_signin;
Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_firstname = findViewById(R.id.register_first);
        register_lastname = findViewById(R.id.register_last);
        register_username = findViewById(R.id.register_username);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_button = findViewById(R.id.create_create);
        register_signin = findViewById(R.id.register_signin);


        SpannableString ss = new SpannableString("Already have an AttendOn account?");

        ClickableSpan signin = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(Register.this,SignIn.class);
                startActivity(i);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(signin,1,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        register_signin.setText(ss);
        register_signin.setMovementMethod(LinkMovementMethod.getInstance());

          register_button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent(Register.this,SignIn.class);
                  startActivity(i);
              }
          });
    }
}
