package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.app.drawerItems.CustomPrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class viewProfile extends AppCompatActivity {

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    Button  btnUpdate, btnUpdateStats;

    private IProfile profile;
    DashboardActivity DA = new DashboardActivity();
    String URL = DA.URL1;
    JSONParser jsonParser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String rowID = getIntent().getStringExtra("RowID");
        viewProfile.GetUserDetails getUserDetails = new viewProfile.GetUserDetails();
        getUserDetails.execute(rowID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewprofile);

    }

    private class GetUserDetails extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {

            URL = URL.concat("profileRetrieve.php");
            String id = args[0];

            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("id",id));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            TextView nameText=findViewById(R.id.NameText);
            TextView emailText=findViewById(R.id.EmailText);
            TextView contactText =  findViewById(R.id.ContactText);
            TextView addressText =  findViewById(R.id.AddressText);
            TextView genderText=findViewById(R.id.GenderText);
            TextView birthdayText = findViewById(R.id.BirthdayText);
            TextView schoolText = findViewById(R.id.SchoolText);
            TextView sportText = findViewById(R.id.SportText);
            TextView positionText = findViewById(R.id.PositionText);

            TextView pointsText = findViewById(R.id.textPoints);
            TextView reboundsText = findViewById(R.id.textRebounds);
            TextView assistsText = findViewById(R.id.textAssists);
            TextView blocksText = findViewById(R.id.textBlocks);
            TextView stealsText = findViewById(R.id.textSteals);
            TextView turnoverText = findViewById(R.id.textTurnovers);
            TextView foulsText = findViewById(R.id.textFouls);
            TextView minutesText = findViewById(R.id.textMinutes);
            TextView missedFGText = findViewById(R.id.textMissedFG);



            try {
                if (result != null) {
                    nameText.setText(result.getString("name"));
                    emailText.setText(result.getString("email"));
                    contactText.setText(result.getString("contactNumber"));
                    addressText.setText(result.getString("address"));
                    genderText.setText(result.getString("gender"));
                    birthdayText.setText(result.getString("birthdate"));
                    schoolText.setText(result.getString("school"));
                    sportText.setText(result.getString("sport"));
                    positionText.setText(result.getString("position"));

                    pointsText.setText(result.getString("points"));
                    reboundsText.setText(result.getString("rebounds"));
                    assistsText.setText(result.getString("assists"));
                    blocksText.setText(result.getString("blocks"));
                    stealsText.setText(result.getString("steals"));
                    turnoverText.setText(result.getString("turnover"));
                    foulsText.setText(result.getString("fouls"));
                    minutesText.setText(result.getString("minutes"));
                    missedFGText.setText(result.getString("missedFG"));
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
