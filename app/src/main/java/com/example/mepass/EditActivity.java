package com.example.mepass;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText site = findViewById(R.id.editTextsite);
        EditText emailET = findViewById(R.id.editTextEmail);
        EditText pwd = findViewById(R.id.editTextPwd);
        FloatingActionButton push = findViewById(R.id.FAB);
        FloatingActionButton delete = findViewById(R.id.FAB1);

        Bundle extras = getIntent().getExtras();
        String username_string = extras.getString("user");
        String email_string = extras.getString("email");
        String website_string = extras.getString("site");
        String password_string = extras.getString("pass");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        String websiteCleansed = website_string.replace(".","");
        emailET.setText(email_string);
        pwd.setText(password_string);
        site.setText(website_string);

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String site_name = site.getText().toString().replace(".","");
                Map<String, Object> map = new HashMap<>();
                map.put("email", emailET.getText().toString());
                map.put("password", pwd.getText().toString());
                map.put("website", site.getText().toString());

                db = FirebaseDatabase.getInstance();
                ref = db.getReference();
                ref.child("users").child(userId).child(websiteCleansed).removeValue();

                ref.child("users").child(userId).child(site_name).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EditActivity.this, "Credentials Updated Succesfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditActivity.this, "Please try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }


        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseDatabase.getInstance();
                ref = db.getReference();
                ref.child("users").child(userId).child(websiteCleansed).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EditActivity.this, "Credentials Deleted Succesfully", Toast.LENGTH_SHORT).show();
                            //finish();
                        } else {
                            Toast.makeText(EditActivity.this, "Please try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}