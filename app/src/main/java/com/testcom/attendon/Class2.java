package com.testcom.attendon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.testcom.attendon.model.OwnedClassModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Class2 extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton create;

    private String code="";
    private String name="";
    private String time="";
    private String start_time="";
    private String end_time="";
    private String date="";
    private String desc="";

    ArrayList<OwnedClassModel> listClass2 = new ArrayList<OwnedClassModel>();
    RequestQueue requestQueueClass2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class2, container,false);
        getClass2Data();


        recyclerView = (RecyclerView) view.findViewById(R.id.class2_recycle);

        create = (FloatingActionButton) view.findViewById(R.id.class2_create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Class2Create.class);
                startActivity(i);
            }
        });
        return view;
    }



    private void getClass2Data() {
        listClass2.clear();
        requestQueueClass2 = Volley.newRequestQueue(getContext());
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET,
                "https://upview.000webhostapp.com/attendon/owned_class.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("owned_class");
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
                                OwnedClassModel m = new OwnedClassModel(code,name,time,date,desc);
                                listClass2.add(m);
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            Class2Adapter end = new Class2Adapter(getContext());
                            end.setListOwnedClass(listClass2);
                            recyclerView.setAdapter(end);
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
                        Log.e("Volley", "Error");
                    }
                }
        );
        requestQueueClass2.add(jor);

}}

