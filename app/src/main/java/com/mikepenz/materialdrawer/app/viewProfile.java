package com.mikepenz.materialdrawer.app;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class viewProfile extends AppCompatActivity {

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    Button  btnUpdate, btnUpdateStats;

    private IProfile profile;
    DashboardActivity DA = new DashboardActivity();
    JSONParser jsonParser=new JSONParser();
    ArrayAdapter<String> adapter;
    ListView lv;
    String line = null;
    String result1 = null;
    InputStream is = null;
    final ArrayList<String> athleteIDArray = new ArrayList<String>(); // List of Athlete ID's
    String[] data;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewprofile);
        final String name= getIntent().getStringExtra("name");
        final String email= getIntent().getStringExtra("email");
        final String sport= getIntent().getStringExtra("sport");
        String rowID = getIntent().getStringExtra("RowID");
        viewProfile.GetUserDetails getUserDetails = new viewProfile.GetUserDetails();
        getUserDetails.execute(rowID);
        lv=findViewById(R.id.league_list);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer selectionID = Integer.parseInt(athleteIDArray.get(position));
                String select = selectionID.toString();
                Intent intent = null;
                intent = new Intent(getApplicationContext(), tournamentView.class);
                intent.putExtra("RowID", select);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("sport", sport);
                startActivity(intent);
            }
        });
        getData();
        if(data!=null && data.length>0) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
            lv.setAdapter(adapter);
        }else{
            View empty = findViewById(R.id.league_list);
            lv.setEmptyView(empty);
        }

    }

    private class GetUserDetails extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {
            String URL = "https://buasdamlag.000webhostapp.com/profileRetrieve.php";
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
            TextView rankingText = findViewById(R.id.EditRankingPoints);


            try {
                if (result != null) {
                    nameText.setText(result.getString("name"));
                    emailText.setText(result.getString("email"));
                    contactText.setText(result.getString("contact"));
                    addressText.setText(result.getString("address"));
                    genderText.setText(result.getString("gender"));
                    birthdayText.setText(result.getString("birthdate"));
                    schoolText.setText(result.getString("school"));
                    rankingText.setText(result.getString("rankingsScore"));


                    WebView mWebView =  findViewById(R.id.videoProfile);
                    String videoUrl = result.getString("youtube");
                    // WebViewの設定
                    WebSettings settings = mWebView.getSettings();
                    settings.setJavaScriptEnabled(true);
                    settings.setAllowFileAccess(true);
                    mWebView.setBackgroundColor(Color.TRANSPARENT);
                    String html = "";
                    html += "<html><body>";
                    html += "<style>\n" +
                            ".video-container { \n" +
                            "position: relative; \n" +
                            "padding-bottom: 56.25%; \n" +
                            "padding-top: 35px; \n" +
                            "height: 0; \n" +
                            "overflow: hidden; \n" +
                            "}\n" +
                            ".video-container iframe { \n" +
                            "position: absolute; \n" +
                            "top:0; \n" +
                            "left: 0; \n" +
                            "width: 100%; \n" +
                            "height: 100%; \n" +
                            "}\n" +
                            "</style>" +
                            "<div class=\"video-container\">\n" +
                            "<iframe width=\"560\" height=\"315\" src=\""+videoUrl+"\" frameborder=\"0\" allowfullscreen></iframe>"+
                            "</div>" ;
                    html += "</body></html>";

                    mWebView.loadData(html, "text/html", null);
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    private void getData(){
        final String id = getIntent().getStringExtra("RowID");
        try{
            String address = "https://buasdamlag.000webhostapp.com/leagueRetrieve.php?id="+id;
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null)
            {
                sb.append(line+"\n");
            }

            is.close();
            result1 = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        try
        {
            JSONArray ja = new JSONArray(result1);
            JSONObject jo;

            data=new String[ja.length()];

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                data[i] = jo.getString("tournament_Name");
                athleteIDArray.add(jo.getString("basketball_id"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
