package com.hfad.surge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {
    FirebaseAuth fauth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        fauth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBarsplash);
        progressBar.setVisibility(View.VISIBLE);

        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                    startActivity(new Intent(getApplicationContext(),Register.class));
                    finish();

            }
        },2000);
    }
}
