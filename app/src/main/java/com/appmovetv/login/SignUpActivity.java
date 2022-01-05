package com.appmovetv.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appmovetv.MainActivity;
import com.appmovetv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailUp, passUp;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        anhxa();

        initListener();
    }

    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickSignUp();
            }
        });
    }

    private void onClickSignUp() {

        String email = emailUp.getText().toString().trim();
        String password = passUp.getText().toString().trim();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(email.equals("") || password.equals("")){
            btnSignUp.setTextColor(Color.parseColor("#ff0000"));
            Toast.makeText(SignUpActivity.this,"Vui lòng nhập thông tin đăng ký",Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnSignUp.setTextColor(Color.parseColor("#039BE5"));
                }
            }, 300);
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();// Đóng các Activity trước

                            } else {

                                btnSignUp.setTextColor(Color.parseColor("#ff0000"));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        btnSignUp.setTextColor(Color.parseColor("#039BE5"));
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUpActivity.this, "Thông tin tài khoản hoặc mật khẩu không chính xác",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }, 300);
                            }
                        }
                    });
        }

    }

    private void anhxa() {
        emailUp = findViewById(R.id.txt_emailUp);
        passUp = findViewById(R.id.txt_passwordUp);
        btnSignUp = findViewById(R.id.btn_signUp);
    }
}