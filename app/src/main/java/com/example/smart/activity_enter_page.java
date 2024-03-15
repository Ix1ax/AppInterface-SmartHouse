package com.example.smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class activity_enter_page extends AppCompatActivity {

    private static final long CLICK_INTERVAL = 3000; // 2 seconds
    private long lastClickTime = 0;
    private EditText emailLogin;
    private EditText passwordLogin;
    private ImageButton enter;
    private ImageButton register;
    private FirebaseAuth mAuth;
    private ImageButton forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_page);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        enter = findViewById(R.id.enter);
        register = findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = findViewById(R.id.forgotPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_enter_page.this, activity_enter_password_page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < CLICK_INTERVAL) {
                    Toasty.info(activity_enter_page.this, "Попробуйте через 3 секунды", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                lastClickTime = currentTime;

                if(emailLogin.getText().toString().isEmpty() || passwordLogin.getText().toString().isEmpty()){
                    Toasty.info(activity_enter_page.this, "Поле не может быть пустым", Toast.LENGTH_SHORT, true).show();
                } else {
                    mAuth.signInWithEmailAndPassword(emailLogin.getText().toString(), passwordLogin.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(activity_enter_page.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toasty.error(activity_enter_page.this, "Неправильный email или пароль", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            });
                }
            }
        });


        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_enter_page.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot,null);
                EditText emailbox = dialogView.findViewById(R.id.emailBox);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long currentTime = System.currentTimeMillis(); // текущее время
                        if (currentTime - lastClickTime < CLICK_INTERVAL) {
                            // Еще не прошло 3 секунд с момента последнего нажатия
                            return; // игнорируем нажатие кнопки
                        }
                        lastClickTime = currentTime; // обновляем время последнего нажатия

                        String usermail = emailbox.getText().toString();

                        if (TextUtils.isEmpty(usermail) || !Patterns.EMAIL_ADDRESS.matcher(usermail).matches()) {
                            Toasty.info(activity_enter_page.this, "Введите вашу почту", Toast.LENGTH_SHORT, true).show();
                        } else {
                            mAuth.sendPasswordResetEmail(usermail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toasty.success(activity_enter_page.this, "Проверьте вашу почту", Toast.LENGTH_SHORT, true).show();
                                        dialog.dismiss();
                                    } else {
                                        Toasty.error(activity_enter_page.this, "Не удалось отправить, сбой", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            });
                        }
                    }
                });
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });

    }

}