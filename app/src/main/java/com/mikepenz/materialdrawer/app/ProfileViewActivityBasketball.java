package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileViewActivityBasketball extends AppCompatActivity {

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    Button  btnUpdate, btnUpdateStats;

    private IProfile profile;
    DashboardActivity DA = new DashboardActivity();
    JSONParser jsonParser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String id= getIntent().getStringExtra("id");
        String name= getIntent().getStringExtra("name");
        String email= getIntent().getStringExtra("email");
        String sport = getIntent().getStringExtra("sport");
        ProfileViewActivityBasketball.GetUserDetails getUserDetails= new ProfileViewActivityBasketball.GetUserDetails();
        getUserDetails.execute(id);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_advanced_drawer);
        btnUpdate=findViewById(R.id.updateBtn);
        btnUpdateStats=findViewById(R.id.SportStatsBtn);

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String id= getIntent().getStringExtra("id");
                String name= getIntent().getStringExtra("name");
                String email= getIntent().getStringExtra("email");
                String sport = getIntent().getStringExtra("sport");
                Intent intent = null;
                intent = new Intent(ProfileViewActivityBasketball.this, updateProfile.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("sport",sport);
                startActivity(intent);
            }

        });

        // Create a few sample profile
        profile = new ProfileDrawerItem().withName(name).withEmail(email).withIcon(getResources().getDrawable(R.drawable.profile3)).withIdentifier(2);

        // Create the AccountHeader
        buildHeader(false, savedInstanceState);

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_profile).withIcon(FontAwesome.Icon.faw_male).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_invitations).withIcon(FontAwesome.Icon.faw_facebook_messenger).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_school).withIcon(FontAwesome.Icon.faw_building).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_coach).withIcon(FontAwesome.Icon.faw_play).withIdentifier(4)
                ) // add the items we want to use with our Drawer
                .addStickyDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(FontAwesome.Icon.faw_lock).withIdentifier(7).withSelectable(false)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            String id = getIntent().getStringExtra("id");
                            String name= getIntent().getStringExtra("name");
                            String email= getIntent().getStringExtra("email");
                            String sport = getIntent().getStringExtra("sport");

                            if (drawerItem.getIdentifier() == 1) {
                                Intent intent = null;
                                intent = new Intent(getApplicationContext(),DashboardActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                intent.putExtra("sport",sport);
                                startActivity(intent);
                            } else if (drawerItem.getIdentifier() == 2) {
                                if(sport.equals("Basketball")){
                                    Intent intent = null;
                                    intent = new Intent(getApplicationContext(),ProfileViewActivityBasketball.class);
                                    intent.putExtra("id",id);
                                    intent.putExtra("name",name);
                                    intent.putExtra("email",email);
                                    intent.putExtra("sport",sport);
                                    startActivity(intent);
                                }
                            } else if (drawerItem.getIdentifier() == 3) {
                                Intent intent = null;
                                intent = new Intent(getApplicationContext(),schools.class);
                                intent.putExtra("id",id);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                intent.putExtra("sport",sport);
                                startActivity(intent);
                            } else if (drawerItem.getIdentifier() == 4) {
                                Intent intent = null;
                                intent = new Intent(getApplicationContext(), coaches.class);
                                intent.putExtra("id",id);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                intent.putExtra("sport",sport);
                                startActivity(intent);
                            }else if (drawerItem.getIdentifier() == 5) {
                                Intent intent = null;
                                intent = new Intent(getApplicationContext(), invitations.class);
                                intent.putExtra("id",id);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                intent.putExtra("sport",sport);
                                startActivity(intent);
                            }else if (drawerItem.getIdentifier() == 7) {
                                Intent intent = null;
                                intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                intent.putExtra("sport",sport);
                                startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }

    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();
    }

    private class GetUserDetails extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String URL = "https://buasdamlag.000webhostapp.com/profileRetrieveBasketball.php";
            String id = args[0];

            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("id",id));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result1) {

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

            TextView textPoints = findViewById(R.id.textPoints);
            TextView textRebounds = findViewById(R.id.textRebounds);
            TextView textAssists = findViewById(R.id.textAssists);
            TextView textBlocks = findViewById(R.id.textBlocks);
            TextView textSteals = findViewById(R.id.textSteals);
            TextView textMinutes = findViewById(R.id.textMinutes);
            TextView textFouls = findViewById(R.id.textFouls);
            TextView textTurnovers = findViewById(R.id.textTurnovers);
            TextView textMissedFG = findViewById(R.id.textMissedFG);



            try {
                if (result1 != null) {
                    nameText.setText(getIntent().getStringExtra("name"));
                    emailText.setText(result1.getString("email"));
                    contactText.setText(result1.getString("contact"));
                    addressText.setText(result1.getString("address"));
                    genderText.setText(result1.getString("gender"));
                    birthdayText.setText(result1.getString("birthdate"));
                    schoolText.setText(result1.getString("school"));
                    sportText.setText(result1.getString("sport"));
                    positionText.setText(result1.getString("position"));

                    textPoints.setText(result1.getString("points"));
                    textRebounds.setText(result1.getString("rebounds"));
                    textAssists.setText(result1.getString("assists"));
                    textBlocks.setText(result1.getString("blocks"));
                    textSteals.setText(result1.getString("steals"));
                    textMinutes.setText(result1.getString("minutes"));
                    textFouls.setText(result1.getString("fouls"));
                    textTurnovers.setText(result1.getString("turnover"));
                    textMissedFG.setText(result1.getString("missedFG"));
                    WebView mWebView =  findViewById(R.id.videoProfile);
                    String videoUrl = result1.getString("youtube");
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



                    if (result1.getString("sport").equals("Basketball")) {
                        btnUpdateStats.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                String id = getIntent().getStringExtra("id");
                                String name = getIntent().getStringExtra("name");
                                String email = getIntent().getStringExtra("email");
                                Intent intent = null;
                                intent = new Intent(ProfileViewActivityBasketball.this, updateBasketballStats.class);
                                intent.putExtra("id", id);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            }

                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
