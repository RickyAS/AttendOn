package com.testcom.attendon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.testcom.attendon.model.TampilMahasiswaModel;

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
    private ArrayList<EnterData> enterDataArrayList;
    private FloatingActionButton create;
    private JsonArrayRequest request;
    private TextView txtNim, txtNama, txtFakultas, txtProdi;

    private String nim="";
    private String nama="";
    private String fakultas="";

    ArrayList<TampilMahasiswaModel> listMhs = new ArrayList<TampilMahasiswaModel>();
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class2, container,false);
        getUserData();


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


    void addData(){
        enterDataArrayList = new ArrayList<>();
        enterDataArrayList.add(new EnterData("Bahasa Indonesia", "09.00-10.00", "25-4-2019"));
        enterDataArrayList.add(new EnterData("Matematika", "09.00-10.00", "26-4-2019"));
        enterDataArrayList.add(new EnterData("Sains", "09.00-10.00", "27-4-2019"));
        enterDataArrayList.add(new EnterData("Bahasa Inggris", "09.00-10.00", "28-4-2019"));
        enterDataArrayList.add(new EnterData("Bahasa Indonesia", "09.00-10.00", "29-4-2019"));
        enterDataArrayList.add(new EnterData("Matematika", "09.00-10.00", "30-4-2019"));
    }

    private void getUserData() {
        listMhs.clear();
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET,
                "https://upview.000webhostapp.com/attendon/owned_class.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("mhs");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                nim = jsonObject.getString("class_name");
                                nama = jsonObject.getString("class_description");
                                fakultas = jsonObject.getString("class_date");


                                TampilMahasiswaModel m = new TampilMahasiswaModel(nim,nama,fakultas);
                                listMhs.add(m);
                                //Toast.makeText(TampilMahasiswa.this, ""+listMhs, Toast.LENGTH_SHORT).show();
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            Class2Adapter end = new Class2Adapter(getContext().getApplicationContext());
                            end.setListTampilMahasiswa(listMhs);
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
        requestQueue.add(jor);

}}

