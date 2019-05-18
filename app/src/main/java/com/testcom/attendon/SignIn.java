package com.testcom.attendon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class SignIn extends AppCompatActivity {
private EditText sign_email, sign_userpassword;
private TextView sign_signup;
private Button sign_button;
private RequestQueue requestQueue;

private String email="";
private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        sign_email = (EditText) findViewById(R.id.sign_email);
        sign_userpassword = (EditText) findViewById(R.id.sign_userpassword);
        sign_signup = findViewById(R.id.sign_signup);
        sign_button =  findViewById(R.id.sign_button);
        requestQueue = Volley.newRequestQueue(SignIn.this);

        email = sign_email.getText().toString().trim();
        password = sign_userpassword.getText().toString().trim();

        SharedPreferences check = getSharedPreferences("alldata", Context.MODE_PRIVATE);
        if (check.getString("logged", "").equals("logged")) {
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);
        }

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
                System.out.println("mmmmmmmmmmmmmmmmmmm"+sign_email.getText().toString().trim());
                SharedPreferences enter = getSharedPreferences("alldata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = enter.edit();
                editor.putString("logged", "logged");
                editor.putString("email", sign_email.getText().toString().trim());
                editor.apply();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://upview.000webhostapp.com/attendon/sign_login.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {

                                if(ServerResponse.equalsIgnoreCase("Data Matched")) {

                                    // If response matched then show the toast.
                                    Toast.makeText(SignIn.this, ServerResponse, Toast.LENGTH_LONG).show();

                                    // Finish the current Login activity.
                                    finish();

                                    // Opening the user profile activity using intent.
                                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                                    intent.putExtra("email", sign_email.getText().toString().trim());
                                    startActivity(intent);
                                }
                                else {

                                    // Showing Echo Response Message Coming From Server.
                                    Toast.makeText(SignIn.this, ServerResponse, Toast.LENGTH_LONG).show();

                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                // Showing error message if something goes wrong.
                                Toast.makeText(SignIn.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {


                        Map<String, String> params = new HashMap<String, String>();

                        params.put("email", email);
                        params.put("password", password);

                        return params;
                    }

                };
                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(SignIn.this);

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);
            }
        });
    }
}
