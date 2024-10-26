package com.example.mepass;

import static android.content.ContentValues.TAG;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity{
    FirebaseAuth mAuth;
    EditText email;
    EditText password;
    Button button;
    TextView registerButton, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //onStart();
        mAuth = FirebaseAuth.getInstance();
        button = (Button) findViewById(R.id.button_log);
        email =  findViewById(R.id.email);
        password = findViewById(R.id.password);
        pass = findViewById(R.id.passreset);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email.getText().toString();

                if (!emailAddress.isEmpty()){
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Email sent.");
                                        Toast.makeText(MainActivity.this, "Password link sent", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Log.d(TAG, "Email not sent.");
                                        Toast.makeText(MainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Log.d(TAG, "Email field empty");
                    Toast.makeText(MainActivity.this, "E-Mail can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerButton = findViewById(R.id.registerBTN);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = email.getText().toString();
                String pwd = password.getText().toString();

                if (user.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Credentials can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(user, pwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(MainActivity.this, "Authentication Success.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(user);
                                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }
                                }
                            });
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        ;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String email_string = currentUser.getEmail();
            mAuth.signOut();
            email.setText(email_string);
            }
    }


}