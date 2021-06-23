package com.example.helpbuy;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;

import com.example.helpbuy.ui.listrequest.ListRequestFragment;
import com.example.helpbuy.ui.listrequest.RequestDetailsFragment;
import com.example.helpbuy.ui.transactions.TransactionActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.helpbuy.R;

import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;

import com.example.helpbuy.databinding.ActivityNavigationBinding;

import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigation.toolbar);
        //BOTTOM RIGHT CHAT BUTTON
//        binding.appBarNavigation.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        //ADD THE BUTTON IDS HERE
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_chats,
                R.id.nav_transactions, R.id.nav_addrequest, R.id.nav_addoffer, R.id.nav_listrequest,R.id.nav_listoffer, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }



    //Sign out button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        super.onCreateOptionsMenu(menu);
//        Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
//        intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
//        auth = FirebaseAuth.getInstance();
//        auth.signOut();
////        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
////            @Override
////            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
////                FirebaseUser user = firebaseAuth.getCurrentUser();
////            }
////        };
//        menu.addIntentOptions(
//                R.id.action_settings,  // Menu group to which new items will be added
//                0,      // Unique item ID (none)
//                0,      // Order for the items (none)
//                this.getComponentName(),   // The current activity name
//                null,   // Specific items to place first (none)
//                intent, // Intent created above that describes our requirements
//                0,      // Additional flags to control items (none)
//                null);  // Array of MenuItems that correlate to specific items (none)
//        return true;
//    }

//        getMenuInflater().inflate(R.menu.navigation, menu);
//        auth = FirebaseAuth.getInstance();
//        auth.signOut();
//        // this listener will be called when there is change in firebase user session
//        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                startActivity(new Intent(NavigationActivity.this, LoginActivity.class));
//                finish();
//            }
//        };
//        return true;

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.nav_transactions) {
//            startActivity(new Intent(getApplicationContext(), TransactionActivity.class));
//        } else {
//            this.onNavigationItemSelected(item);
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}