package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    EditText editName;
    EditText editContactNumber;
    EditText editSchool;
    EditText editAddress;
    String editSport;
    Button btnSignIn, btnRegister;

    //Change the 192.168.43.77 into your computer's IP address,,, go to cmd and type ipconfig
    String URL= "http://192.168.43.222/bwas_damlag/retrieve.php";

    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final Spinner dropdown = (Spinner)findViewById(R.id.sportspinner);
        String[] items = new String[]{"Select Sport","Basketball", "Volleyball", "Football"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                editSport = dropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        editEmail=findViewById(R.id.editemail);
        editName=findViewById(R.id.editname);
        editContactNumber =  findViewById(R.id.editcontactnum);
        editPassword =  findViewById(R.id.editpassword);
        editSchool=findViewById(R.id.editschool);
        editAddress=findViewById(R.id.editaddress);
        btnRegister=findViewById(R.id.signupbtn);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AttemptLogin attemptLogin= new AttemptLogin();
                //identical to JSON Parser
                attemptLogin.execute(editEmail.getText().toString(),
                        editName.getText().toString(),
                        editPassword.getText().toString(),
                        editContactNumber.getText().toString(),
                        editSchool.getText().toString(),
                        editAddress.getText().toString(),editSport);

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

            String sport = args[6];
            String address = args[5];
            String school = args[4];
            String contactNumber = args[3];
            String password = args[2];
            String name= args[1];
            String email= args[0];

            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("contactNumber", contactNumber));
            params.add(new BasicNameValuePair("school", school));
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("sport", sport));

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

