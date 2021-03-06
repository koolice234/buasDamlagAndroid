package com.mikepenz.materialdrawer.app;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Downloader;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class updateProfile extends AppCompatActivity {
    private AccountHeader headerResult = null;
    private Drawer result = null;
    Button  btnUpdate, btnUpload;
    DatePickerDialog picker;
    private IProfile profile;
    Bitmap bitmap, decoded;
    String URL= "https://buasdamlag.000webhostapp.com/profileRetrieve.php";
    String URLUpdate= "https://buasdamlag.000webhostapp.com/updateProfile.php";
    JSONParser jsonParser=new JSONParser();
    EditText txt_name;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String id= getIntent().getStringExtra("id");
        String name= getIntent().getStringExtra("name");
        String email= getIntent().getStringExtra("email");
        String sport= getIntent().getStringExtra("sport");

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
        final TextView videoURL = findViewById(R.id.editURL);
        final TextView weight = findViewById(R.id.editWeight);
        final TextView height = findViewById(R.id.editHeight);
        final TextView gpa = findViewById(R.id.editGPA);
        final TextView medical = findViewById(R.id.editMedical);
        final TextView yearGraduated = findViewById(R.id.yearGraduated);

        btnUpdate = findViewById(R.id.updateBtn);
        btnUpload = findViewById(R.id.uploadBtn);

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
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showFileChooser();
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
                        schoolDropdown.getSelectedItem().toString(),
                        videoURL.getText().toString(),
                        weight.getText().toString(),
                        height.getText().toString(),
                        gpa.getText().toString(),
                        medical.getText().toString(),
                        yearGraduated.getText().toString());
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
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_profile).withIcon(FontAwesome.Icon.faw_male).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_invitations).withIcon(FontAwesome.Icon.faw_handshake).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_applications).withIcon(FontAwesome.Icon.faw_tasks).withIdentifier(7),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_school).withIcon(FontAwesome.Icon.faw_building).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_coach).withIcon(FontAwesome.Icon.faw_play).withIdentifier(4)
                ) // add the items we want to use with our Drawer
                .addStickyDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(FontAwesome.Icon.faw_lock).withIdentifier(5).withSelectable(false)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        DashboardActivity DA = new DashboardActivity();
                        DA.sidebar(drawerItem);

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

    public String getStringImage(Bitmap bmp) {
        int bitmap_size = 60; // range 1 - 100
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void showFileChooser() {
        int PICK_IMAGE_REQUEST = 1;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
                    TextView videoText = findViewById(R.id.editURL);
                    TextView weightText = findViewById(R.id.editWeight);
                    TextView heightText = findViewById(R.id.editHeight);
                    TextView gpaText = findViewById(R.id.editGPA);
                    TextView medicalText = findViewById(R.id.editMedical);
                    TextView yearGraduatedText = findViewById(R.id.yearGraduated);

                    nameText.setText(result.getString("name"));
                    emailText.setText(result.getString("email"));
                    contactText.setText(result.getString("contact"));
                    addressText.setText(result.getString("address"));
                    birthdayText.setText(result.getString("birthdate"));
                    videoText.setText(result.getString("youtube"));
                    weightText.setText(result.getString("weight"));
                    heightText.setText(result.getString("height"));
                    gpaText.setText(result.getString("gpa"));
                    medicalText.setText(result.getString("medical"));
                    yearGraduatedText.setText(result.getString("yearGraduated"));

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
            String urlVideo = args[8];
            String weight = args[9];
            String height = args[10];
            String gpa = args[11];
            String medical = args[12];
            String yearGraduated = args[13];


            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("id",id1));
            params.add(new BasicNameValuePair("name",name));
            params.add(new BasicNameValuePair("address",address));
            params.add(new BasicNameValuePair("contactNumber",contact));
            params.add(new BasicNameValuePair("birthday",birthday));
            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("gender",gender));
            params.add(new BasicNameValuePair("school",school));
            params.add(new BasicNameValuePair("youtube",urlVideo));
            params.add(new BasicNameValuePair("weight",weight));
            params.add(new BasicNameValuePair("height",height));
            params.add(new BasicNameValuePair("gpa",gpa));
            params.add(new BasicNameValuePair("medical",medical));
            params.add(new BasicNameValuePair("yearGraduated",yearGraduated));

            JSONObject json = jsonParser.makeHttpRequest(URLUpdate, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {
            String sport = getIntent().getStringExtra("sport");
            if (result != null) {
                try {
                    String result1 = result.getString("success");
                    String id = result.getString("id");
                    String name = result.getString("name");
                    String email = result.getString("email");
                    if (result1.equals("1")){
                            Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),ProfileViewActivityBasketball.class);
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
