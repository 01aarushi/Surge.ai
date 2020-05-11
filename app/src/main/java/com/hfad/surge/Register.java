package com.hfad.surge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText Username , UserEmail ,UserPassword,phonenum;
    Button Register;
    TextView already;
    ProgressBar pg;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID,em,pass,fullname,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Username=findViewById(R.id.Namereg);
        UserEmail=findViewById(R.id.emailreg);
        UserPassword=findViewById(R.id.pwdreg);
        phonenum=findViewById(R.id.phonereg);
        Register=findViewById(R.id.signup);
        already=findViewById(R.id.gotoLogin);
        pg=findViewById(R.id.progressBarreg);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();


        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em=UserEmail.getText().toString();
                pass=UserPassword.getText().toString();
                fullname=Username.getText().toString();
                phone=phonenum.getText().toString();

                if(TextUtils.isEmpty(em)){
                    UserEmail.setError("Email can't be empty");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    UserPassword.setError("Password can't be empty");
                    return;
                }
                if(pass.length()<6){
                    UserPassword.setError("Password must be of atleast 6 characters");
                    return;
                }
                pg.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          Toast.makeText(getApplicationContext(),"User Registered Successfully",Toast.LENGTH_SHORT).show();
                          userID=fAuth.getCurrentUser().getUid();
                          DocumentReference docref = fstore.collection("Users").document(userID);
                          Map<String,Object> data = new HashMap<>();
                          data.put("Name",fullname);
                          data.put("Phone Number",phone);
                          data.put("Email",em);
                          docref.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {

                                  Log.d(TAG, "onSuccess: User profile is created for "+userID);
                              }
                          });
                          pg.setVisibility(View.INVISIBLE);
                          startActivity(new Intent(getApplicationContext(),MainActivity.class));
                      }
                      else{
                          Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                          pg.setVisibility(View.INVISIBLE);
                      }
                    }
                });



            }
        });

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Register.this,Login.class);
                pg.setVisibility(View.INVISIBLE);
                startActivity(i);

            }
        });
    }
}
