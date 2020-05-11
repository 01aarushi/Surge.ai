package com.hfad.surge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    FirebaseAuth fauth;
    NavigationView navi_view;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;




    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fauth=FirebaseAuth.getInstance();

        drawerLayout=findViewById(R.id.drawer);
        navi_view=findViewById(R.id.nav_view);
        navi_view.setNavigationItemSelectedListener(this);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.logout :
                logoutuser();
                break;
            case  R.id.home :
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            default:
                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void logoutuser() {
        fauth.signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();

    }
}
