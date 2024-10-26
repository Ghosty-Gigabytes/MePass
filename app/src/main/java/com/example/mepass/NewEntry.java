package com.example.mepass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class NewEntry extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_entry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        EditText site = findViewById(R.id.editTextsite);
        EditText emailET = findViewById(R.id.editTextEmail);
        EditText pwd = findViewById(R.id.editTextPwd);
        FloatingActionButton push = findViewById(R.id.FAB);
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (site.getText().toString().isEmpty() || emailET.getText().toString().isEmpty() || pwd.getText().toString().isEmpty()) {
                    Toast.makeText(NewEntry.this, "Credentials can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", emailET.getText().toString());
                    map.put("password", pwd.getText().toString());
                    map.put("website", site.getText().toString());
                    String site_name = site.getText().toString().replace(".","");
                    DataClass users = new DataClass(site.getText().toString(),emailET.getText().toString(),pwd.getText().toString());
                    db = FirebaseDatabase.getInstance();
                    ref = db.getReference();
                    ref.child("users").child(userId).child(site_name).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(NewEntry.this, "Credentials Added Succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewEntry.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(NewEntry.this, "Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }


        });
    }
}