package com.cst2335.cst2335mada0043;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,mToolbar,R.string.Open,R.string.Close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String message = null;
        switch (item.getItemId()){
            case R.id.choice1 :
                message ="You clicked choice1";
                break;
            case R.id.choice2:
                message ="You clicked choice2";
                break;
            case R.id.choice3:
                 message ="You clicked choice3";
                 break;
            case R.id.choice4:
                message ="You clicked choice4";
                break;
        }
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        return true;
    }
    public boolean onNavigationItemSelected( @NonNull MenuItem item) {
        String message = null;
        switch (item.getItemId()){
            case R.id.chtPg :
                Intent goToProfile  = new Intent(TestToolbar.this, ChatRoomActivity.class);
                startActivityForResult( goToProfile,345);
                break;
            case R.id.wthrPg:
                Intent goToWeather  = new Intent(TestToolbar.this, WeatherForecast.class);
                startActivityForResult( goToWeather,345);
                break;
            case R.id.logPg:
                String result = "500";
                goToProfile  = new Intent(TestToolbar.this, activity_profile.class);
                goToProfile .putExtra("Number", result);
                finish();
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}