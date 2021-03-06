package com.mikepenz.materialdrawer.app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText editEmail;
    EditText editPassword;
    EditText editName;
    EditText editContactNumber;
    String editSchool;
    EditText editAddress;
    EditText editBirthday;
    String editGender;
    String editSport, editPosition;
    Button btnSignIn, btnRegister;

    //Change the 192.168.43.77 into your computer's IP address,,, go to cmd and type ipconfig
    DashboardActivity DA = new DashboardActivity();


    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final Spinner positionDropdown = findViewById(R.id.positionSpinner);
        final Spinner schoolDropdown = findViewById(R.id.lastSchoolSpinner);
        final Spinner genderDropdown = findViewById(R.id.genderspinner);
        String[] gender = new String[]{"Gender","Male", "Female"};
        final ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gender);
        genderDropdown.setAdapter(genderAdapter);
        genderDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                editGender = genderDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
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
        schoolDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                editSchool = schoolDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        String[] basketballPosition = new String[]{"Select Position","Point Guard", "Shooting Guard", "Small Forward", "Power Forward", "Center"};
        final ArrayAdapter<String> basketballPositionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, basketballPosition);
        positionDropdown.setAdapter(basketballPositionAdapter);
        positionDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                editPosition = positionDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });


        editEmail=findViewById(R.id.editemail);
        editName=findViewById(R.id.editname);
        editContactNumber =  findViewById(R.id.editcontactnum);
        editPassword =  findViewById(R.id.editpassword);
        editAddress=findViewById(R.id.editaddress);
        editBirthday = findViewById(R.id.editBirthday);
        editBirthday.setInputType(InputType.TYPE_NULL);
        editBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editBirthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        btnRegister=findViewById(R.id.signupbtn);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AttemptLogin attemptLogin= new AttemptLogin();
                //identical to JSON Parser
                attemptLogin.execute(
                        editName.getText().toString(),
                        editAddress.getText().toString(),
                        editContactNumber.getText().toString(),
                        editBirthday.getText().toString(),
                        editEmail.getText().toString(),
                        editPassword.getText().toString(),
                        editGender,
                        editSchool,
                        editPosition);

            }
        });


    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {
            String URL = "https://buasdamlag.000webhostapp.com/registerAthlete.php";
            String name = args[0];
            String address = args[1];
            String contactNumber = args[2];
            String birthday = args[3];
            String email = args[4];
            String password = args[5];
            String gender = args[6];
            String school = args[7];
            String position = args[8];

            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("contactNumber", contactNumber));
            params.add(new BasicNameValuePair("birthday", birthday));
            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("school", school));
            params.add(new BasicNameValuePair("position", position));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {
            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    String result1 = result.getString("success");
                    if (result1.equals("1")){
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
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

