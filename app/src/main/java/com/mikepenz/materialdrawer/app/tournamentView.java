package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
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

public class tournamentView extends AppCompatActivity {

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    Button  btnUpdate, btnUpdateStats;

    private IProfile profile;
    DashboardActivity DA = new DashboardActivity();
    JSONParser jsonParser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String rowID = getIntent().getStringExtra("RowID");
        tournamentView.GetUserDetails getUserDetails = new tournamentView.GetUserDetails();
        getUserDetails.execute(rowID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournament_view);

    }

    private class GetUserDetails extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {
            String URL = "https://buasdamlag.000webhostapp.com/tournamentView.php";
            String id = args[0];

            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("id",id));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            TextView tournamentNameView=findViewById(R.id.tournamentNameView);
            TextView positionView=findViewById(R.id.positionView);
            TextView pointsView =  findViewById(R.id.pointsView);
            TextView assistsView =  findViewById(R.id.assistsView);
            TextView stealsView=findViewById(R.id.stealsView);
            TextView blocksView = findViewById(R.id.blocksView);
            TextView reboundsView = findViewById(R.id.reboundsView);
            TextView totalRankingPoints = findViewById(R.id.totalRankingPoints);


            try {
                if (result != null) {
                    tournamentNameView.setText(result.getString("tournament_Name"));
                    positionView.setText(result.getString("basketball_position"));
                    pointsView.setText(result.getString("basketball_points"));
                    assistsView.setText(result.getString("basketball_assists"));
                    stealsView.setText(result.getString("basketball_steals"));
                    blocksView.setText(result.getString("basketball_blocks"));
                    reboundsView.setText(result.getString("basketball_rebounds"));
                    totalRankingPoints.setText(result.getString("basketball_average"));

                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }

}
