package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class coaches extends AppCompatActivity {

    private AccountHeader headerResult = null;
    private Drawer result = null;
    ListView lv;
    private IProfile profile;
    ArrayAdapter<String> adapter;
    String line = null;
    String result1 = null;
    InputStream is = null;
    final ArrayList<String> coachIDArray = new ArrayList<String>(); // List of Athlete ID's
    String[] data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coaches); //kwaon ang layout ka coaches.xml
        String id= getIntent().getStringExtra("id");
        final String name= getIntent().getStringExtra("name");
        final String email= getIntent().getStringExtra("email");
        final String sport = getIntent().getStringExtra("sport"); //retrieve ang data nga gin pasa sa diri nga class
        lv=findViewById(R.id.listView1); //list view meaning ang view sang mga coaches
        lv.setClickable(true); // pwede ma click ang listView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer selectionID = Integer.parseInt(coachIDArray.get(position));
                String select = selectionID.toString();
                Intent intent = null;
                intent = new Intent(getApplicationContext(),viewCoachesProfile.class);
                intent.putExtra("RowID",select);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("sport",sport);
                startActivity(intent);
            }
        });// kung mag click sang ara sa listView makadto sa ViewCoachesProfile.java kag i pass ang ara sa intent nga data
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        if(sport.equals("Basketball")){ //kung basketball ipakita lang ang basketball nga coaches kag butang sa data nga array kag ang unod ka array butang sa listView
            getData();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
            lv.setAdapter(adapter);
        }

        //sidebar details ni ang profile amo ni ang gagwa sa kilid nga picture kag ngalan
        profile = new ProfileDrawerItem().withName(name).withEmail(email).withIcon(getResources().getDrawable(R.drawable.profile3)).withIdentifier(2);

        // Handle Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_advanced_drawer);
        // Create the AccountHeader
        buildHeader(false, savedInstanceState);

        //Create the drawer ang drawer amo ni ang unod ka sidebar if lantawon mo ang addDrawerItems may mga number ga reflect na sa if else sa dalom nga getIdentifier
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
    //i build ya na ang header mo ang toolbar kag sidebar
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
    private void getData(){ //ang amo ni nga function ma kadto sa address nga basketballCoaches sa web kag mag get sang mga list of coaches nga basketball lang
        TextView label = findViewById(R.id.labelCoaches);
        label.setText("Basketball Coaches");
        try{
            String address = "https://buasdamlag.000webhostapp.com/basketballCoachesRetrieve.php"; //url sang
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
                coachIDArray.add(jo.getString("id"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

