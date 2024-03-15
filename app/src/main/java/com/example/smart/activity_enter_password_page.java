package com.example.smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class activity_enter_password_page extends AppCompatActivity {

    private EditText emailRegister;
    private EditText passwordRegister;
    private ImageButton enterRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FirebaseDatabase database;

    private EditText name;
    private EditText surname;

    private EditText phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password_page);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        emailRegister = findViewById(R.id.emailRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        enterRegister = findViewById(R.id.enterRegister);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        phone = findViewById(R.id.phone);

        enterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailRegister.getText().toString().isEmpty() || passwordRegister.getText().toString().isEmpty() || name.getText().toString().isEmpty() || surname.getText().toString().isEmpty() || phone.getText().toString().isEmpty()) {
                    Toasty.info(activity_enter_password_page.this, "Поле не может быть пустым", Toast.LENGTH_SHORT, true).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(emailRegister.getText().toString(), passwordRegister.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("email").setValue(emailRegister.getText().toString());
                                        ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("password").setValue(passwordRegister.getText().toString());
                                        ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("name").setValue(name.getText().toString());
                                        ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("surname").setValue(surname.getText().toString());
                                        ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("phone").setValue(phone.getText().toString());
                                        Intent intent = new Intent(activity_enter_password_page.this, activity_enter_page.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(intent);
                                    } else {
                                        Toasty.error(activity_enter_password_page.this, "Произошла ошибка, попробуйте еще раз", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            });
                }
            }
        });


        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout1);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });


    }

    public void License(View V){
        Intent intent = new Intent(activity_enter_password_page.this, LicensePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void BackLogin(View v) { // переход назад
        Intent intent = new Intent(this, activity_enter_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, activity_enter_page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        overridePendingTransition(0, 0); // Отключает анимацию
    }


}