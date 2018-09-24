package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private AccountHeader headerResult = null;
    private Drawer result = null;
    ListView lv;
    private IProfile profile;
    ArrayAdapter<String> adapter;
    String line = null;
    String result1 = null;
    InputStream is = null;
    final ArrayList<String> athleteIDArray = new ArrayList<String>(); // List of Athlete ID's
    String[] data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final String id = getIntent().getStringExtra("id");
        final String name= getIntent().getStringExtra("name");
        final String email= getIntent().getStringExtra("email");
        final String sport = getIntent().getStringExtra("sport");
        lv=findViewById(R.id.listView1);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer selectionID = Integer.parseInt(athleteIDArray.get(position));
                String select = selectionID.toString();
                if (sport.equals("Basketball")){
                    Intent intent = null;
                    intent = new Intent(getApplicationContext(),viewProfile.class);
                    intent.putExtra("RowID",select);
                    intent.putExtra("name",name);
                    intent.putExtra("email",email);
                    intent.putExtra("sport",sport);
                    startActivity(intent);
                }
            }
        });
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
            getData();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
            lv.setAdapter(adapter);

        // Create a few sample profile
        profile = new ProfileDrawerItem().withName(name).withEmail(email).withIcon(getResources().getDrawable(R.drawable.profile3)).withIdentifier(2);

        // Handle Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_advanced_drawer);
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
                        new PrimaryDrawerItem().withName(R.string.drawer_item_invitations).withIcon(FontAwesome.Icon.faw_handshake).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_school).withIcon(FontAwesome.Icon.faw_building).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_coach).withIcon(FontAwesome.Icon.faw_play).withIdentifier(4)
                ) // add the items we want to use with our Drawer
                .addStickyDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(FontAwesome.Icon.faw_lock).withIdentifier(7).withSelectable(false)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            sidebar(drawerItem);
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();


    }
    public void sidebar(IDrawerItem drawerItem){

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
    private void getData(){
        TextView label = findViewById(R.id.labelRankings);
        label.setText("Basketball Rankings");
        try{
            String address = "https://buasdamlag.000webhostapp.com/dashboardRetrieve.php";
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
                data[i] = jo.getString("name");
                athleteIDArray.add(jo.getString("id"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getDataVolleyball(){
        TextView label = findViewById(R.id.labelRankings);
        label.setText("Volleyball Rankings");
        try{
            String address = "https://buasdamlag.000webhostapp.com/dashboardRetrieveVolleyball.php";
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
                data[i] = jo.getString("name");
                athleteIDArray.add(jo.getString("id"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }



}

