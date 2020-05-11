package com.hfad.surge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText Email ;
    EditText Password;
    Button login;
    int counter = 5;
    ProgressBar prog;
    FirebaseAuth fauth;
    TextView newuse,forgetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        login=findViewById(R.id.btnLogin);
        prog=findViewById(R.id.progressBar);
        newuse=findViewById(R.id.newUser);
        forgetpassword=findViewById(R.id.forgetPass);

        newuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        fauth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em=Email.getText().toString();
                String pass=Password.getText().toString();

                if(TextUtils.isEmpty(em)){
                    Email.setError("Email can't be empty");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Password.setError("Password can't be empty");
                    return;
                }
                if(pass.length()<6){
                    Password.setError("Password must be of atleast 6 characters");
                    return;
                }
                prog.setVisibility(View.VISIBLE);
                validate(em,pass);
            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail =new EditText(v.getContext());
                AlertDialog.Builder resetpass =new AlertDialog.Builder(v.getContext());
                resetpass.setTitle("Reset Password ?");
                resetpass.setMessage("Enter your E-mail to receive the password reset link ");
                resetpass.setView(resetMail);
                resetpass.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail=resetMail.getText().toString();

                        fauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this,"Password reset link is sent to your E-mail",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Error in sending E-mail"+e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
                resetpass.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });

                resetpass.create().show();
            }
        });
    }

    public void validate(String email, String password){

        fauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Logged in successfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    prog.setVisibility(View.INVISIBLE);
                }

            }
        });


    }


}
