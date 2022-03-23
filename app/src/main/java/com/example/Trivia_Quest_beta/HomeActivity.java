package com.example.Trivia_Quest_beta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    TextView username, currency, level, exp;
    String userName;
    ProgressBar expbar;
    DatabaseHelper MyDB;

    private String[] data;
    private int expnumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!= null)
            userName = (String) b.get("username");

        SharedPreferences sp = this.getSharedPreferences("TriviaPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", userName);
        editor.commit();

        username = findViewById(R.id.usernametv);
        level = findViewById(R.id.leveltv);
        currency = findViewById(R.id.currencytv);
        exp = findViewById(R.id.expvalue);
        expbar = findViewById(R.id.levelBar);
        MyDB = new DatabaseHelper(this);

        data = MyDB.GetUserData(userName);

        username.setText(data[0]);
        level.setText("Level: " + data[1]);
        currency.setText("$ " + data[2]);
        exp.setText(data[3] + "%");
        expnumber = Integer.parseInt(data[3]);

        expbar.setProgress(expnumber);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new HomeFragment(),"Home").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.home:
                    selectedFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, selectedFragment,"Home").commit();
                    break;
                case R.id.bag:
                    selectedFragment = new BagFragmentEquipment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, selectedFragment,"Bag").commit();
                    break;
                case R.id.shop:
                    selectedFragment = new ShopFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, selectedFragment,"Shop").commit();
                    break;
                case R.id.more:
                    selectedFragment = new MoreFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, selectedFragment,"More").commit();
                    break;
            }

            return true;
        }
    };

    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.FragmentContainer);
        if (currentFragment.getTag().equals("Home")) {
            AlertDialog.Builder ab = new AlertDialog.Builder(HomeActivity.this);
            ab.setTitle("");
            ab.setMessage("Do you want to exit?");
            ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finishAffinity();
                }
            });
            ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    onResume();
                }
            });
            ab.show();
        }else {
            BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
            bottomNav.setSelectedItemId(R.id.home);
            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new HomeFragment(),"Home").commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


}