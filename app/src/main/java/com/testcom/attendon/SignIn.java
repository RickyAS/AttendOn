package com.testcom.attendon;

import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class SignIn extends AppCompatActivity {
EditText sign_username, sign_userpassword;
TextView sign_signup;
Button sign_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        sign_username = findViewById(R.id.sign_username);
        sign_userpassword = findViewById(R.id.sign_userpassword);
        sign_signup = findViewById(R.id.sign_signup);
        sign_button =  findViewById(R.id.sign_button);


        SpannableString ss = new SpannableString("Don't have an account ?");

        ClickableSpan signup = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(SignIn.this,Register.class);
                startActivity(i);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(signup,0,23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        sign_signup.setText(ss);
        sign_signup.setMovementMethod(LinkMovementMethod.getInstance());

        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
