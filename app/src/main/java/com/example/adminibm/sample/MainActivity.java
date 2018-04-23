package com.example.adminibm.sample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String TAG="Sample Data Display App";
    TextView id_txt1,name_txt1,city_txt1,id_txt2,name_txt2,city_txt2;
    String id1,name1,age1,city1,id2,name2,age2,city2;
    ProgressDialog pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb=new ProgressDialog(this);

        initviews();

        new GetContacts().execute();
    }

    private void initviews()
    {
     id_txt1=(TextView)findViewById(R.id.two);
     name_txt1=(TextView)findViewById(R.id.four);
     city_txt1=(TextView)findViewById(R.id.six);


      id_txt2=(TextView)findViewById(R.id.eight);
     name_txt2=(TextView)findViewById(R.id.ten);
     city_txt2=(TextView)findViewById(R.id.twelve);




}

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setTitle("Getting Data");
            pb.show();
         //   Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://springmvcjson.cfapps.io/listEmp";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {

                   // Getting JSON Array node
                    JSONArray data = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);
                        if(i==0)
                        {
                            id1 = c.getString("id");
                            name1 = c.getString("name");
                            age1 = c.optString("age");

                            // Phone node is JSON Object
                            JSONObject address = c.getJSONObject("addr");
                            city1 = address.getString("city");
                        }
                        else
                        {
                            id2 = c.getString("id");
                            name2 = c.getString("name");
                            age2 = c.optString("age");

                            // Phone node is JSON Object
                            JSONObject address = c.getJSONObject("addr");
                            city2 = address.getString("city");
                        }


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(pb.isShowing())
            {
                pb.dismiss();
            }
            id_txt1.setText(": "+id1);
            name_txt1.setText(": "+name1);
            city_txt1.setText(": "+city1);


            id_txt2.setText(": "+id2);
            name_txt2.setText(": "+name2);
            city_txt2.setText(": "+city2);

        }
    }
}


