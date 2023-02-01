package com.shacharml.safepass.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shacharml.safepass.R;
import com.shacharml.safepass.Utils.HelperHTML;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle drawerToggle;
    private TextView textView;
    private DrawerLayout drawer_layout;
    private NavigationView main_nav_side_menu;

    private HelperHTML helperHTML;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        textView = findViewById(R.id.textView);
        textView.setText(user.getPhoneNumber() + "\n" + user.getUid());

        helperHTML = HelperHTML.initHelper();

        drawer_layout = findViewById(R.id.drawer_layout);
        main_nav_side_menu = findViewById(R.id.main_nav_side_menu);
        drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close);
        drawer_layout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        main_nav_side_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_home:{
                        Log.d("drawer", "Home");
                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_LONG).show();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_container, new PasswordsListFragment()).commit();
                        break;}

                    case R.id.item_terms:{
                        Log.d("drawer", "terms of use");
                        helperHTML.openHtmlTextDialog(MainActivity.this, "terms & conditions.html");
                        break;}

                    case R.id.item_policy:{
                        Log.d("drawer", "policy");
                        helperHTML.openHtmlTextDialog(MainActivity.this, "privacy.html");
                        break;}

                    case R.id.item_log_out:{
                        Log.d("drawer", "log out");
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        Toast.makeText(MainActivity.this, "Bey Bey", Toast.LENGTH_SHORT).show();
                        finish();

//                        AuthUI.getInstance()
//                                .signOut(MainActivity.this)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @SuppressLint("RestrictedApi")
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        // user is now signed out
//                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                                        Toast.makeText(MainActivity.this, "Bey Bey", Toast.LENGTH_SHORT).show();
//                                        finish();
//                                    }
//                                });
                        break;}
                }
                drawer_layout.closeDrawer(GravityCompat.START);
                return false;            }
        });
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