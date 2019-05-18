package com.testcom.attendon;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class Info extends Fragment {
    private LinearLayout signout, creds;
    private SharedPreferences alldata;
    private TextView name;
    RequestQueue requestQueueInfo;


    public Info() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.info, container, false);

        alldata = getActivity().getSharedPreferences("alldata", Context.MODE_PRIVATE);
        signout = view.findViewById(R.id.out);
        creds = view.findViewById(R.id.info_creds);
        name = view.findViewById(R.id.info_name);

        ProfileData(alldata.getString("email", "fail"));



        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = alldata.edit();
                editor.remove("logged");
                editor.commit();
                getActivity().finish();

            }
        });

        creds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity() , info_credit.class);
                startActivity(i);
            }
        });

        return view;
    }

    private void ProfileData(String email){

        requestQueueInfo = Volley.newRequestQueue(getContext());
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST,
                "https://upview.000webhostapp.com/attendon/info.php",
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("info");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                String me = jsonObject.getString("username");
                                name.setText("Hello, "+me);
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
        requestQueueInfo.add(jor);
    }



}
