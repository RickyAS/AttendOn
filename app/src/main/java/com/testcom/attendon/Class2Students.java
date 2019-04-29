package com.testcom.attendon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import androidx.appcompat.app.AppCompatActivity;

public class Class2Students extends AppCompatActivity {
    private TextView classcode;
    private Button classend, classdismiss, classremove;
    private ProgressDialog progressDialog;

    private String code ="";
    private String HttpUrl = "https://upview.000webhostapp.com/attendon/owned_class_delete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class2_students);
        Intent i = getIntent();
        setTitle(i.getStringExtra("class_title"));
        code=i.getStringExtra("class_code");

        classcode = findViewById(R.id.student_classcode);
        classend = findViewById(R.id.student_endclass);
        classdismiss = findViewById(R.id.student_dismiss);
        classremove = findViewById(R.id.student_remove);

        classcode.setText("Class Code : "+code);
        progressDialog = new ProgressDialog(Class2Students.this);


        classend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData();
            }
        });
    }

    private void DeleteData(){

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Deleting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
                        Toast.makeText(Class2Students.this, ServerResponse, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Class2Students.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Class2Students.this, Class2.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("class_code", code);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Class2Students.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

}
