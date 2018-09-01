package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class schools extends AppCompatActivity {
    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    Button uslsBtn, unorBtn, csabBtn, stiBtn;

    private IProfile profile;
    DashboardActivity DA = new DashboardActivity();
    JSONParser jsonParser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        final String sport = getIntent().getStringExtra("sport");

        setContentView(R.layout.schools);
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_advanced_drawer);
        uslsBtn=findViewById(R.id.uslsBtn);
        unorBtn=findViewById(R.id.unorBtn);
        csabBtn = findViewById(R.id.csaBtn);
        stiBtn = findViewById(R.id.stiBtn);

        uslsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String id= getIntent().getStringExtra("id");
                String name= getIntent().getStringExtra("name");
                String email= getIntent().getStringExtra("email");
                Intent intent = null;
                intent = new Intent(getApplicationContext(), uslsInfo.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("sport",sport);
                startActivity(intent);
            }

        });
        unorBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String id= getIntent().getStringExtra("id");
                String name= getIntent().getStringExtra("name");
                String email= getIntent().getStringExtra("email");
                Intent intent = null;
                intent = new Intent(getApplicationContext(), unorInfo.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("sport",sport);
                startActivity(intent);
            }

        });
        csabBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String id= getIntent().getStringExtra("id");
                String name= getIntent().getStringExtra("name");
                String email= getIntent().getStringExtra("email");
                Intent intent = null;
                intent = new Intent(getApplicationContext(), csabInfo.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("sport",sport);
                startActivity(intent);
            }

        });
        stiBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String id= getIntent().getStringExtra("id");
                String name= getIntent().getStringExtra("name");
                String email= getIntent().getStringExtra("email");
                Intent intent = null;
                intent = new Intent(getApplicationContext(), stiInfo.class);
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
                        new PrimaryDrawerItem().withName(R.string.drawer_item_iq).withIcon(FontAwesome.Icon.faw_question).withIdentifier(6),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_school).withIcon(FontAwesome.Icon.faw_building).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_coach).withIcon(FontAwesome.Icon.faw_play).withIdentifier(4)
                ) // add the items we want to use with our Drawer
                .addStickyDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(FontAwesome.Icon.faw_lock).withIdentifier(5).withSelectable(false)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
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
}
