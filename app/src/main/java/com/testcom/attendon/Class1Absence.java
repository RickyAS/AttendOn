package com.testcom.attendon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.testcom.attendon.model.AbsenceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Class1Absence extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ProgressBar bar;
    private RecyclerView recyclerView;
    private SharedPreferences alldata;
    private TextView empty;

    private String code ="";
    private String date ="";
    private String record ="";
    ArrayList<AbsenceModel> listAbsence= new ArrayList<AbsenceModel>();
    RequestQueue requestQueueAbsence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class1_absence);

       alldata = getSharedPreferences("alldata", Context.MODE_PRIVATE);


        Intent i = getIntent();
        setTitle(i.getStringExtra("class_title"));
        code = i.getStringExtra("class_code");

        recyclerView = (RecyclerView) findViewById(R.id.absence_recycle);
        bar = findViewById(R.id.absence_prog);
        empty = findViewById(R.id.absence_empty);
        getAbsenceData();
    }

    private void getAbsenceData(){
        progressDialog = new ProgressDialog(Class1Absence.this);
        listAbsence.clear();

        requestQueueAbsence = Volley.newRequestQueue(Class1Absence.this);
        Map<String, String> params = new HashMap<>();
        params.put("email",   alldata.getString("email", "fail"));
        params.put("code",code);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST,
                "https://upview.000webhostapp.com/attendon/your_class_absence.php",
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("your_class_absence");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                date = jsonObject.getString("class_date");
                                record = jsonObject.getString("class_record");
                                AbsenceModel m = new AbsenceModel(date,record);
                                listAbsence.add(m);
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(Class1Absence.this));
                            Class1AbsenceAdapter end = new Class1AbsenceAdapter(Class1Absence.this);
                            end.setListAbsence(listAbsence);
                            recyclerView.setAdapter(end);
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
        requestQueueAbsence.add(jor);
    }


}
