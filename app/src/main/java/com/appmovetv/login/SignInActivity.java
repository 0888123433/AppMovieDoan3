package com.appmovetv.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appmovetv.DialogLoading;
import com.appmovetv.MainActivity;
import com.appmovetv.R;
import com.appmovetv.admin.AdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private LinearLayout layousignUp;
    private EditText edtemail,edtpassword;
    private Button btnSignIn;

    TextView txttitle, txtnamelogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        anhxa();

        startAnimScore();

        UniListenner();
    }
    private void startAnimScore() {
                Animation animation = AnimationUtils.loadAnimation(SignInActivity.this, R.anim.slide_in_from_right);
                txttitle.startAnimation(animation);
                Animation animation1 = AnimationUtils.loadAnimation(SignInActivity.this, R.anim.slide_in_from_left);
                txtnamelogin.startAnimation(animation1);
                Animation animation2 = AnimationUtils.loadAnimation(SignInActivity.this, R.anim.fade_in);

                edtemail.startAnimation(animation2);
                edtpassword.startAnimation(animation2);
                btnSignIn.startAnimation(animation);
                layousignUp.startAnimation(animation1);
    }

    private void UniListenner() {

        layousignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //đăng nhập
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                String email = edtemail.getText().toString().trim();
                String password = edtpassword.getText().toString().trim();

                if (email.equals("admin") && password.equals("admin")){
                    Intent intent = new Intent(SignInActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                else if(email.equals("") || password.equals("")){
                    btnSignIn.setBackgroundResource(R.drawable.bogocdo);
                    Toast.makeText(SignInActivity.this,"Vui lòng nhập thông tin đăng nhập",Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnSignIn.setBackgroundResource(R.drawable.bogoc1);
                        }
                    }, 300);
                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        btnSignIn.setBackgroundResource(R.drawable.bogocxanh);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                btnSignIn.setBackgroundResource(R.drawable.bogoc1);
                                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();//Dừng các activity trước
                                            }
                                        },300);

                                    } else {
                                        btnSignIn.setBackgroundResource(R.drawable.bogocdo);
                                        Toast.makeText(SignInActivity.this,"Tài khoản hoặc Mật khẩu không chính xác !",Toast.LENGTH_SHORT).show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                btnSignIn.setBackgroundResource(R.drawable.bogoc1);
                                            }
                                        }, 300);
                                    }
                                }
                            });
                }

            }
        });
    }

    private void anhxa() {

        layousignUp = findViewById(R.id.layout_sign_up);
        edtemail = findViewById(R.id.txt_email);
        edtpassword = findViewById(R.id.txt_password);
        btnSignIn = findViewById(R.id.btn_login);
        txttitle = findViewById(R.id.txt_nametitle);
        txtnamelogin = findViewById(R.id.txt_namelogin);
    }
}