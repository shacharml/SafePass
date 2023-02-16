package com.shacharml.safepass.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.shacharml.safepass.R;
import com.shacharml.safepass.Utils.HelperHTML;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle drawerToggle;
    private TextView textView;
    private DrawerLayout drawer_layout;
    private NavigationView main_nav_side_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer_layout = findViewById(R.id.drawer_layout);
        main_nav_side_menu = findViewById(R.id.main_nav_side_menu);
        drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close);
        drawer_layout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        main_nav_side_menu.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_terms: {
                    Log.d("drawer", "terms of use");
                    HelperHTML.openHtmlTextDialog(MainActivity.this, "terms & conditions.html");
                    break;
                }

                case R.id.item_policy: {
                    Log.d("drawer", "policy");
                    HelperHTML.openHtmlTextDialog(MainActivity.this, "privacy.html");
                    break;
                }

                case R.id.item_log_out: {
                    Log.d("drawer", "log out");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    break;
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START);
            return false;
        });

        NavController navController = Navigation.findNavController(this, R.id.activity_main_host_fragment);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}