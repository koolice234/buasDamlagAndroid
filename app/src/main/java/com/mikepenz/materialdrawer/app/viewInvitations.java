package com.mikepenz.materialdrawer.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class viewInvitations extends AppCompatActivity {
    String URL= "https://buasdamlag.000webhostapp.com/invitationRetrieveByID.php";
    JSONParser jsonParser=new JSONParser();
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.invitationview);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitationview);
        String id = getIntent().getStringExtra("invitationID");
        viewInvitations.GetInviDetails GetInviDetails = new viewInvitations.GetInviDetails();
        GetInviDetails.execute(id);
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();

    }
    private class GetInviDetails extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {
            String id = args[0];

            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("id",id));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            TextView nameText=findViewById(R.id.athleteName);
            TextView emailText=findViewById(R.id.invitationMessage);
            TextView coach=findViewById(R.id.Coach);



            try {
                if (result != null) {
                    nameText.setText("Dear, "+result.getString("name1"));
                    emailText.setText(result.getString("message1"));
                    coach.setText("From Coach, "+result.getString("coachName1"));
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
