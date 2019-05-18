package com.testcom.attendon;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.testcom.attendon.model.YourClassModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Class1 extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton join;
    private ProgressDialog progressDialog;
    private ProgressBar bar;
    private EditText joincode;
    private TextView empty;

    private SharedPreferences alldata;
    private String code="";
    private String name="";
    private String time="";
    private String start_time="";
    private String end_time="";
    private String date="";
    private String desc="";

    ArrayList<YourClassModel> listClass1 = new ArrayList<YourClassModel>();
    RequestQueue requestQueueClass1;

    private Dialog di;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class1, container,false);
        alldata = getActivity().getSharedPreferences("alldata", Context.MODE_PRIVATE);

        getClass1Data();
        di = new Dialog(getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.class1_recycle);
        bar = view.findViewById(R.id.class1_prog);
        empty = view.findViewById(R.id.class1_empty);

        join = (FloatingActionButton) view.findViewById(R.id.class1_join);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                di.requestWindowFeature(Window.FEATURE_NO_TITLE);
                di.setContentView(R.layout.dialog_join);
                joincode = (EditText) di.findViewById(R.id.join_code);
                Button btn1 = (Button) di.findViewById(R.id.join_cancel);
                Button btn2 = (Button) di.findViewById(R.id.join_join);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        di.dismiss();
                    }
                });

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        joinClass1(joincode.getText().toString().trim());
                    }
                });
                di.show();
            }
        });
        return view;
    }



    private void getClass1Data() {
        listClass1.clear();
        requestQueueClass1 = Volley.newRequestQueue(getContext());
        Map<String, String> params = new HashMap<>();
        params.put("email", alldata.getString("email", "fail"));
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST,
                "https://upview.000webhostapp.com/attendon/your_class.php",
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("your_class");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                code = jsonObject.getString("class_code");
                                name = jsonObject.getString("class_name");
                                desc = jsonObject.getString("class_description");
                                date = jsonObject.getString("class_date");
                                start_time = jsonObject.getString("class_start");
                                end_time = jsonObject.getString("class_end");

                                time = start_time +"-"+end_time;
                                YourClassModel m = new YourClassModel(code,name,time,date,desc);
                                listClass1.add(m);
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            Class1Adapter end = new Class1Adapter(getContext());
                            end.setListYourClass(listClass1);
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
                            DividerItemDecoration itemDecor = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                            recyclerView.addItemDecoration(itemDecor);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.getMessage());
                    }
                }
        );
        jor.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueueClass1.add(jor);

    }

    private void joinClass1(final String codeclass) {
        progressDialog = new ProgressDialog(getActivity());
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://upview.000webhostapp.com/attendon/your_class_join.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
                        Toast.makeText(getActivity(), ServerResponse, Toast.LENGTH_LONG);
                        di.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("classcode", codeclass);
                params.put("email", alldata.getString("email", "fail"));
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }

}
