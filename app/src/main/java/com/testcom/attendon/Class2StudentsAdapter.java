package com.testcom.attendon;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.testcom.attendon.model.StudentModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Class2StudentsAdapter extends RecyclerView.Adapter<Class2StudentsAdapter.MahasiswaView> {
    private Context context;
    private ArrayList<StudentModel> listStudent;

    public ArrayList<StudentModel> getListStudent() {
        return listStudent;
    }

    public void setListStudent(ArrayList<StudentModel> listStudent) {
        this.listStudent = listStudent;
    }

    public Class2StudentsAdapter(Context context) {
        this.context = context;
    }


    @Override
    public Class2StudentsAdapter.MahasiswaView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student, parent, false);
        return new Class2StudentsAdapter.MahasiswaView(view);

    }

    @Override
    public void onBindViewHolder(final Class2StudentsAdapter.MahasiswaView holder, final int position) {
        holder.name.setText(listStudent.get(position).getUsername());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentremove(position);
                holder.remove.setEnabled(false);
                holder.remove.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListStudent().size();

    }

    public class MahasiswaView extends RecyclerView.ViewHolder{
        private TextView name;
        private CardView cvMain;
        private Button remove;

        public MahasiswaView(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_student);
            cvMain = (CardView) itemView.findViewById(R.id.row_student);
            remove = (Button) itemView.findViewById(R.id.student_remove);

        }

    }

    private void studentremove(final int position){
        final ProgressDialog progressDialog = new ProgressDialog(context);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);
        System.out.println("Current time => " + formattedDate);

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Deleting User on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://upview.000webhostapp.com/attendon/owned_class_student_remove.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
                        Toast.makeText(context, ServerResponse, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("email", listStudent.get(position).getEmail());
                params.put("class_code", listStudent.get(position).getClasscode());
                params.put("class_date", formattedDate);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }



}
