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

public class updateVolleyballStats extends AppCompatActivity {

    EditText kills, assists, serviceAce, digs, blocks, gamesPlayed;
    String positionSpin;
    Button btnUpdateStats;
    DashboardActivity DA = new DashboardActivity();
    String URL= DA.URL1;
    JSONParser jsonParser = new JSONParser();

    protected void onCreate(Bundle savedInstanceState) {
        final String id= getIntent().getStringExtra("id");
        String name= getIntent().getStringExtra("name");
        String email= getIntent().getStringExtra("email");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_volleyball_stats);
        final Spinner positionDropdown = findViewById(R.id.positionspinner);
        String[] positions = new String[]{"Select Position","Outside hitter", "Right Side Hitter", "Opposite Hitter", "Setter", "Middle Blocker", "Libero", "Defensive Specialist"};
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
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnUpdateStats=findViewById(R.id.updateStatsBtn);

        btnUpdateStats.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateVolleyballStats.updateStats updateStats= new updateVolleyballStats.updateStats();
                //identical to JSON Parser
                updateStats.execute(
                        kills.getText().toString(),
                        assists.getText().toString(),
                        serviceAce.getText().toString(),
                        digs.getText().toString(),
                        blocks.getText().toString(),
                        gamesPlayed.getText().toString(),
                        positionSpin,
                        id);
            }

        });

        kills=findViewById(R.id.killsVB);
        assists=findViewById(R.id.assistsVB);
        serviceAce =  findViewById(R.id.serviceVB);
        digs =  findViewById(R.id.digsVB);
        blocks=findViewById(R.id.blocksVB);
        gamesPlayed=findViewById(R.id.gamesVB);

    }
    private class updateStats extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {
            URL = URL.concat("updateVolleyballStats.php");
            String kills = args[0];
            String assists = args[1];
            String service = args[2];
            String digs = args[3];
            String blocks = args[4];
            String gamesPlayed = args[5];
            String position = args[6];
            String id = args[7];

            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("kills", kills));
            params.add(new BasicNameValuePair("assists", assists));
            params.add(new BasicNameValuePair("service", service));
            params.add(new BasicNameValuePair("digs", digs));
            params.add(new BasicNameValuePair("blocks",blocks));
            params.add(new BasicNameValuePair("totalGames", gamesPlayed));
            params.add(new BasicNameValuePair("position", position));
            params.add(new BasicNameValuePair("id", id));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {
            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    String id= getIntent().getStringExtra("id");
                    String name= getIntent().getStringExtra("name");
                    String email= getIntent().getStringExtra("email");
                    String sport = getIntent().getStringExtra("sport");
                    Intent intent = null;
                    intent = new Intent(updateVolleyballStats.this, ProfileViewActivityVolleyball.class);
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    intent.putExtra("email",email);
                    intent.putExtra("sport", sport);
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
