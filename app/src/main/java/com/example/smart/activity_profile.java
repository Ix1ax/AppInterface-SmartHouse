package com.example.smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_profile extends AppCompatActivity {

    private TextView textViewEmail;
    private TextView textViewPassword;
    private TextView textViewName;
    private TextView textViewSurname;
    private TextView textViewPhone;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPassword = findViewById(R.id.textViewPassword);
        textViewName = findViewById(R.id.textViewName);
        textViewSurname = findViewById(R.id.textViewSurname);
        textViewPhone = findViewById(R.id.textViewPhone);
        mAuth = FirebaseAuth.getInstance();

        ref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()); // в подкатолог заходим
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = snapshot.child("email").getValue().toString();
                password = snapshot.child("password").getValue().toString();
                name = snapshot.child("name").getValue().toString();
                surname = snapshot.child("surname").getValue().toString();
                phone = snapshot.child("phone").getValue().toString();
                textViewEmail.setText(email);
                textViewPassword.setText(password);
                textViewName.setText(name);
                textViewSurname.setText(surname);
                textViewPhone.setText(phone);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void goToMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        overridePendingTransition(0, 0); // Отключает анимацию
    }


    public void goToLogin(View v) {
        Intent intent = new Intent(this, activity_enter_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}