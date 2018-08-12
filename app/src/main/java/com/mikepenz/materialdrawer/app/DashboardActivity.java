package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
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

public class DashboardActivity extends AppCompatActivity {
    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    private IProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        String id= getIntent().getStringExtra("id");
        String name= getIntent().getStringExtra("name");
        String email= getIntent().getStringExtra("email");
        // Handle Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
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
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_profile).withIcon(FontAwesome.Icon.faw_male).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_school).withIcon(FontAwesome.Icon.faw_building).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_coach).withIcon(FontAwesome.Icon.faw_play).withIdentifier(4)
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
                                intent = new Intent(getApplicationContext(),DashboardActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                startActivity(intent);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(getApplicationContext(),ProfileViewActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                startActivity(intent);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                            }else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(DashboardActivity.this, LoginActivity.class);
                            }
                            if (intent != null) {
                                DashboardActivity.this.startActivity(intent);
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

}
