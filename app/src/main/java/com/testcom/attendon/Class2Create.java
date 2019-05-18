package com.testcom.attendon;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Class2Create extends AppCompatActivity {
    private Button starttime, endtime, setdate, createclass;
    private EditText classid, classname, classdesc;
    private CheckBox checker;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TimePickerDialog timePickerDialog;
    private TextView startview, endview, dateview;
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class2_create);

        classid = (EditText) findViewById(R.id.create_classid);
        classname = (EditText) findViewById(R.id.create_classname);
        classdesc = (EditText) findViewById(R.id.create_classdesc);
        checker = (CheckBox) findViewById(R.id.create_checkbox);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        starttime = (Button) findViewById(R.id.create_timestart);
        endtime = (Button) findViewById(R.id.create_timeend);
        setdate = (Button) findViewById(R.id.create_setdate);
        createclass = (Button) findViewById(R.id.create_create);

        startview = (TextView) findViewById(R.id.create_view1);
        endview = (TextView) findViewById(R.id.create_view2);
        dateview =(TextView) findViewById(R.id.create_view3);


        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(1);
            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(2);
            }
        });

        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        requestQueue = Volley.newRequestQueue(Class2Create.this);
        progressDialog = new ProgressDialog(Class2Create.this);


        createclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SharedPreferences alldata = getSharedPreferences("alldata", Context.MODE_PRIVATE);
                // Showing progress dialog at user registration time.
                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();

                // Creating string request with post method.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://upview.000webhostapp.com/attendon/owned_class_create.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {

                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Showing response message coming from server.
                                Toast.makeText(Class2Create.this, ServerResponse, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Class2Create.this, Class2.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Showing error message if something goes wrong.
                                Toast.makeText(Class2Create.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {

                        // Creating Map String Params.
                        Map<String, String> params = new HashMap<String, String>();

                        // Adding All values to Params.
                        params.put("email", alldata.getString("email", "fail"));
                        params.put("class_code", classid.getText().toString().trim());
                        params.put("class_name", classname.getText().toString().trim());
                        params.put("class_description", classdesc.getText().toString().trim());
                        params.put("class_date", setdate.getText().toString().trim());
                        params.put("class_start", starttime.getText().toString().trim());
                        params.put("class_end", endtime.getText().toString().trim());
                        if (checker.isChecked()){
                            params.put("class_repeat", "1");
                        }
                        else {
                            params.put("class_repeat", "0");
                        }
                        return params;
                    }

                };

                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(Class2Create.this);

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);

            }
        });

    }



    private void showTimeDialog(int ind) {
        final int time = ind;
        /**
         * Calendar untuk mendapatkan waktu saat ini
         */
        Calendar calendar = Calendar.getInstance();

        /**
         * Initialize TimePicker Dialog
         */
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /**
                 * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
                 */

                if (time==1){starttime.setText(hourOfDay+":"+minute);}

                else if (time==2){endtime.setText(hourOfDay+":"+minute);}

            }
        },
                /**
                 * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                 */
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),

                /**
                 * Cek apakah format waktu menggunakan 24-hour format
                 */
                DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                setdate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }


}
