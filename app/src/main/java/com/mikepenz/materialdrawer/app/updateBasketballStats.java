package com.mikepenz.materialdrawer.app;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class updateBasketballStats extends AppCompatActivity {

    EditText points, rebounds, steals, assists, blocks;
    String positionSpin, leagueSpin;
    Button btnUpdateStats;
    DashboardActivity DA = new DashboardActivity();
    JSONParser jsonParser=new JSONParser();

    protected void onCreate(Bundle savedInstanceState) {
        final String id= getIntent().getStringExtra("id");
        String name= getIntent().getStringExtra("name");
        String email= getIntent().getStringExtra("email");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournament_update);
        final Spinner positionDropdown = findViewById(R.id.positionspinner);
        String[] positions = new String[]{"Select Position","Point Guard", "Shooting Guard", "Small Forward", "Power Forward", "Center"};
        final ArrayAdapter<String> positionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, positions);
        positionDropdown.setAdapter(positionAdapter);
        positionDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                positionSpin = positionDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        final Spinner leagueDropdown = findViewById(R.id.leagueSpinner);
        String[] leagues = new String[]{"Select League or Tournament","Gasataya Cup", "Division Meet", "NOPSSCEA", "Provincial Meet", "Regional Meet", "Palarong Pambansa"};
        final ArrayAdapter<String> leagueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, leagues);
        leagueDropdown.setAdapter(leagueAdapter);
        leagueDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                leagueSpin = leagueDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        points=findViewById(R.id.editPoints);
        rebounds=findViewById(R.id.editRebounds);
        steals =  findViewById(R.id.editSteals);
        assists =  findViewById(R.id.editAssists);
        blocks=findViewById(R.id.editBlocks);

        btnUpdateStats=findViewById(R.id.updateStatsBtn);

        btnUpdateStats.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateBasketballStats.updateStats updateStats= new updateBasketballStats.updateStats();
                //identical to JSON Parser
                updateStats.execute(
                        points.getText().toString(),
                        rebounds.getText().toString(),
                        steals.getText().toString(),
                        assists.getText().toString(),
                        blocks.getText().toString(),
                        positionSpin,
                        leagueSpin,
                        id);
            }

        });


    }
    private class updateStats extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {
            String URL = "https://buasdamlag.000webhostapp.com/updateBasketBallStats.php";

            String points = args[0];
            String rebounds = args[1];
            String steals = args[2];
            String assists = args[3];
            String blocks = args[4];
            String id = args[5];
            String position = args[6];
            String league = args[7];

            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("points", points));
            params.add(new BasicNameValuePair("rebounds", rebounds));
            params.add(new BasicNameValuePair("steals", steals));
            params.add(new BasicNameValuePair("assists", assists));
            params.add(new BasicNameValuePair("blocks",blocks));
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("blocks",position));
            params.add(new BasicNameValuePair("id", league));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {
            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    String id = getIntent().getStringExtra("id");
                    String name= getIntent().getStringExtra("name");
                    String email= getIntent().getStringExtra("email");
                    Intent intent = null;
                    intent = new Intent(updateBasketballStats.this, ProfileViewActivityBasketball.class);
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    intent.putExtra("email",email);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
