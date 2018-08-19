package com.mikepenz.materialdrawer.app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Calendar;

public class updateProfile extends AppCompatActivity {
    private AccountHeader headerResult = null;
    private Drawer result = null;
    Button  btnUpdate;
    DatePickerDialog picker;
    private IProfile profile;
    String URL= "http://192.168.43.222/bwas_damlag_web/profileRetrieve.php";
    String URLUpdate= "http://192.168.43.222/bwas_damlag_web/updateProfile.php";
    JSONParser jsonParser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String id= getIntent().getStringExtra("id");
        String name= getIntent().getStringExtra("name");
        String email= getIntent().getStringExtra("email");
        updateProfile.GetUserDetails getUserDetails= new updateProfile.GetUserDetails();
        getUserDetails.execute(id);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        final Spinner genderDropdown=findViewById(R.id.genderSpinner);
        final Spinner schoolDropdown = findViewById(R.id.schoolSpinner);
        final TextView nameText=findViewById(R.id.editName);
        final TextView emailText=findViewById(R.id.editEmail);
        final TextView contactText =  findViewById(R.id.editContact);
        final TextView addressText =  findViewById(R.id.editAddress);
        final TextView birthdayText = findViewById(R.id.editBirthday);
        btnUpdate = findViewById(R.id.updateBtn);

        String[] gender = new String[]{"Gender","Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gender);
        genderDropdown.setAdapter(genderAdapter);
        String[] lastSchool = new String[]
                {"Select Last School Attended","Bacolod City National High School", "Luis Hervias National High School",
                        "Domingo Lacson National High School", "Barangay Singcang-Airport National High School", "Negros Occidental High School",
                        "Bata National High School", "Abkasa National High School", "Handumanan National High School", "Maranatha Christian College High School",
                        "Mansiligan Agro Industrial High School", "Generoso Villanueva Sr National High School" ,"Sum-ag National High School",
                        "Saint Joseph School–La Salle", "Colegio San Agustin - Bacolod", "La Consolacion College–Bacolod", "St. Sebastian International School",
                        "ST. John's Institute-Bacolod", "Riverside College", "St. Scholastica's Academy", "Trinity Christian School"," Jack & Jill School Castleson High",
                        "Negros Mission Academy","Bacolod Tay Tung High School", "Living Stones International School", "STI West Negros University",
                        "University of Negros Occidental - Recoletos", "University of St. La Salle"};
        final ArrayAdapter<String> lastSchoolAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lastSchool);
        schoolDropdown.setAdapter(lastSchoolAdapter);
        birthdayText.setInputType(InputType.TYPE_NULL);
        birthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(updateProfile.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                birthdayText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateProfile.UpdateAthlete updateProfile= new UpdateAthlete();
                //identical to JSON Parser
                updateProfile.execute(
                        getIntent().getStringExtra("id"),
                        nameText.getText().toString(),
                        addressText.getText().toString(),
                        contactText.getText().toString(),
                        birthdayText.getText().toString(),
                        emailText.getText().toString(),
                        genderDropdown.getSelectedItem().toString(),
                        schoolDropdown.getSelectedItem().toString());
            }
        });
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_advanced_drawer);

        // Create a few sample profile
        profile = new ProfileDrawerItem().withName(name).withEmail(email).withIcon(getResources().getDrawable(R.drawable.profile3)).withIdentifier(2);

        // Create the AccountHeader
        buildHeader(false, savedInstanceState);

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_profile).withIcon(FontAwesome.Icon.faw_male).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_school).withIcon(FontAwesome.Icon.faw_building).withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_coach).withIcon(FontAwesome.Icon.faw_play).withIdentifier(4).withSelectable(false)
                ) // add the items we want to use with our Drawer
                .addStickyDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(FontAwesome.Icon.faw_lock).withIdentifier(5).withSelectable(false)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            String id= getIntent().getStringExtra("id");
                            String name= getIntent().getStringExtra("name");
                            String email= getIntent().getStringExtra("email");
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(updateProfile.this, DashboardActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                startActivity(intent);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(updateProfile.this, ProfileViewActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                startActivity(intent);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(updateProfile.this, DashboardActivity.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(updateProfile.this, DashboardActivity.class);
                            }else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(updateProfile.this, LoginActivity.class);
                            }
                            if (intent != null) {
                                updateProfile.this.startActivity(intent);
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

            String id = args[0];

            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("id",id));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            try {
                if (result != null) {

                    TextView nameText=findViewById(R.id.editName);
                    TextView emailText=findViewById(R.id.editEmail);
                    TextView contactText =  findViewById(R.id.editContact);
                    TextView addressText =  findViewById(R.id.editAddress);
                    TextView birthdayText = findViewById(R.id.editBirthday);
                    nameText.setText(result.getString("name"));
                    emailText.setText(result.getString("email"));
                    contactText.setText(result.getString("contactNumber"));
                    addressText.setText(result.getString("address"));
                    birthdayText.setText(result.getString("birthdate"));

                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    private class UpdateAthlete extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {

            String id1 = args[0];
            String name = args[1];
            String address = args[2];
            String contact = args[3];
            String birthday = args[4];
            String email = args[5];
            String gender = args[6];
            String school = args[7];


            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("id",id1));
            params.add(new BasicNameValuePair("name",name));
            params.add(new BasicNameValuePair("address",address));
            params.add(new BasicNameValuePair("contactNumber",contact));
            params.add(new BasicNameValuePair("birthday",birthday));
            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("gender",gender));
            params.add(new BasicNameValuePair("school",school));

            JSONObject json = jsonParser.makeHttpRequest(URLUpdate, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            if (result != null) {
                try {
                    String result1 = result.getString("success");
                    String id = result.getString("id");
                    String name = result.getString("name");
                    String email = result.getString("email");
                    if (result1.equals("1")){
                        Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),ProfileViewActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("name",name);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
            }


        }

    }


}
