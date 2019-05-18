package com.testcom.attendon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Qr_Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    private SharedPreferences alldata;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
        alldata = getSharedPreferences("alldata", Context.MODE_PRIVATE);


    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(final Result rawResult) {

        // Do something with the result here
        Log.v("tag", rawResult.getText()); // Prints scan results
        Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);
        System.out.println("Current time => " + formattedDate);

        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rawResult.getText()));
        //startActivity(browserIntent);

        // Showing progress dialog at user registration time.
       // progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
       // progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://upview.000webhostapp.com/attendon/your_class_scanqr.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        //progressDialog.dismiss();

                        // Showing response message coming from server.
                        Toast.makeText(Qr_Scanner.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        //progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Qr_Scanner.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("class_code", rawResult.getText());
                params.put("email", alldata.getString("email", "fail"));
                params.put("class_date", formattedDate);
                params.put("attend", "1");


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Qr_Scanner.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


        onBackPressed();


        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}
