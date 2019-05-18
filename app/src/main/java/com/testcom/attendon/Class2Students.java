package com.testcom.attendon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.testcom.attendon.model.StudentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Class2Students extends AppCompatActivity {
    private TextView classcode, empty;
    private Button classend, classdismiss, classremove, classqr;
    private ProgressDialog progressDialog;
    private ProgressBar bar;
    private RecyclerView recyclerView;

    ArrayList<StudentModel> listStudent = new ArrayList<StudentModel>();
    RequestQueue requestQueueStudent;

    private String code ="";
    private String classname="";
    private String name ="";
    private String email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class2_students);

        recyclerView = (RecyclerView) findViewById(R.id.student_recycle);
        bar = findViewById(R.id.student_prog);
        empty = findViewById(R.id.student_empty);

        classcode = findViewById(R.id.student_classcode);
        classend = findViewById(R.id.student_endclass);
        classdismiss = findViewById(R.id.student_dismiss);
        classremove = findViewById(R.id.student_remove);
        classqr = findViewById(R.id.student_qr);

        Intent i = getIntent();
        setTitle(i.getStringExtra("class_title"));
        classname = i.getStringExtra("class_title");
        code=i.getStringExtra("class_code");
        getStudentData();




        classcode.setText("Class Code : "+code);
        progressDialog = new ProgressDialog(Class2Students.this);


        classqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Class2Students.this, QR_Show.class);
                i.putExtra("class_code",code);
                i.putExtra("class_title", classname);
                startActivity(i);
            }
        });




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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://upview.000webhostapp.com/attendon/owned_class_delete.php",
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


    private void getStudentData(){
        listStudent.clear();
        requestQueueStudent = Volley.newRequestQueue(Class2Students.this);
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST,
                "https://upview.000webhostapp.com/attendon/owned_class_student.php",
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("owned_class_student");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                name = jsonObject.getString("username");
                                email = jsonObject.getString("email");
                                StudentModel m = new StudentModel(name, email, code);
                                listStudent.add(m);
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(Class2Students.this));
                            Class2StudentsAdapter end = new Class2StudentsAdapter(Class2Students.this);
                            end.setListStudent(listStudent);
                            if(hasil.isNull(0)){
                                recyclerView.setVisibility(View.GONE);
                                bar.setVisibility(View.GONE);
                                empty.setVisibility(View.VISIBLE);
                            }

                            else {
                                recyclerView.setVisibility(View.VISIBLE);
                                bar.setVisibility(View.GONE);
                                empty.setVisibility(View.GONE);
                            }
                            recyclerView.setAdapter(end);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        jor.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueueStudent.add(jor);

    }

    }




