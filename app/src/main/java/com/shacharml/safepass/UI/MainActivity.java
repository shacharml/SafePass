package com.shacharml.safepass.UI;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.shacharml.safepass.Entities.Password;
import com.shacharml.safepass.PasswordsAdapter;
import com.shacharml.safepass.R;
import com.shacharml.safepass.Utils.HelperHTML;
import com.shacharml.safepass.ViewModels.PasswordViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle drawerToggle;
    private TextView textView;
    private DrawerLayout drawer_layout;
    private NavigationView main_nav_side_menu;
    private RecyclerView password_RCV_all_password;

    private HelperHTML helperHTML;

    private PasswordViewModel passwordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        textView = findViewById(R.id.textView);
//        textView.setText(user.getPhoneNumber() + "\n" + user.getUid());

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
//                        FirebaseAuth.getInstance().signOut();
//                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                        Toast.makeText(MainActivity.this, "Bey Bey", Toast.LENGTH_SHORT).show();
//                        finish();

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


//        //Init RecyclerView
////        password_RCV_all_password = findViewById(R.id.password_RCV_all_password);
//        password_RCV_all_password.setLayoutManager(new LinearLayoutManager(this));
//        password_RCV_all_password.setHasFixedSize(true);
//
//        //create adapter
//        PasswordsAdapter adapter = new PasswordsAdapter();
//        password_RCV_all_password.setAdapter(adapter);
//
//
//        //create view model
//        passwordViewModel = new ViewModelProvider(this).get(PasswordViewModel.class);
//        passwordViewModel.getAllPasswords().observe(this, new Observer<List<Password>>() {
//            @Override
//            public void onChanged(List<Password> passwords) {
//                // TODO: 06/02/2023 update the recycler view
//                adapter.setPasswords(passwords);
//            }
//        });


        //fragment Navigation - need to open the firs fragment in the nav graph

//        //observe on the live data in room
//        passwordViewModel.getAllPasswords().observe(this, new Observer<List<Password>>() {
//            @Override
//            public void onChanged(List<Password> passwords) {
//                // TODO: 06/02/2023 update the recycler view
//                adapter.setPasswords(passwords);
//            }
//        });

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