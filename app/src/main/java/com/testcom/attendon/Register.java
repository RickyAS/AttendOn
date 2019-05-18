package com.testcom.attendon;

import android.content.Intent;
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

public class Register extends AppCompatActivity {
private EditText register_firstname, register_lastname, register_email, register_password;
private TextView register_signin;
private Button register_button;
private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_firstname = findViewById(R.id.register_first);
        register_lastname = findViewById(R.id.register_last);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_button = findViewById(R.id.register_button);
        register_signin = findViewById(R.id.register_signin);
        requestQueue = Volley.newRequestQueue(Register.this);



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
        ss.setSpan(signin,0,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        register_signin.setText(ss);
        register_signin.setMovementMethod(LinkMovementMethod.getInstance());

          register_button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent(Register.this,SignIn.class);
                  StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://upview.000webhostapp.com/attendon/sign_register.php",
                          new Response.Listener<String>()  {
                              @Override
                              public void onResponse(String ServerResponse) {


                                  // Showing response message coming from server.
                                  Toast.makeText(Register.this, ServerResponse, Toast.LENGTH_LONG).show();

                              }
                          },
                          new Response.ErrorListener() {
                              @Override
                              public void onErrorResponse(VolleyError volleyError) {



                                  // Showing error message if something goes wrong.
                                  Toast.makeText(Register.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                              }


                          } ) {
                      @Override
                      protected Map<String, String> getParams() {

                          // Creating Map String Params.
                          Map<String, String> params = new HashMap<String, String>();

                          // Adding All values to Params.
                          params.put("email", register_email.getText().toString().trim());
                          params.put("firstname", register_firstname.getText().toString().trim());
                          params.put("lastname", register_lastname.getText().toString().trim());
                          params.put("password", register_password.getText().toString().trim());

                          return params;
                      }

                  };
                  // Creating RequestQueue.
                  RequestQueue requestQueue = Volley.newRequestQueue(Register.this);

                  // Adding the StringRequest object into requestQueue.
                  requestQueue.add(stringRequest);
                  startActivity(i);
              }
          });
    }
}
