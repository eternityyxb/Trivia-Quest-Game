package com.example.Trivia_Quest_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText username, password, repassword;
    private Button signup, signin;
    private DatabaseHelper DB;
    private Toast toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.btnsignup);
        signin = findViewById(R.id.btnsignin);
        DB = new DatabaseHelper(this);

        //signup function
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")){
                    ToastManager();
                    toastMessage = Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT);
                    toastMessage.show();
                }
                else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkusername(user);
                        if (!checkuser) {
                            Boolean insert = DB.insertData(user, pass);
                            DB.InsertNewUser(user);
                            if (insert) {
                                ToastManager();
                                toastMessage = Toast.makeText(SignUpActivity.this, "Registered and Login Successfully", Toast.LENGTH_SHORT);
                                toastMessage.show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.putExtra("username", username.getText().toString());
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            } else {
                                ToastManager();
                                toastMessage = Toast.makeText(SignUpActivity.this, "Registration Failed, Please Try Again", Toast.LENGTH_SHORT);
                                toastMessage.show();
                            }
                        } else {
                            ToastManager();
                            toastMessage = Toast.makeText(SignUpActivity.this, "User Existed! Please Login", Toast.LENGTH_SHORT);
                            toastMessage.show();
                        }
                    }else{
                        ToastManager();
                        toastMessage = Toast.makeText(SignUpActivity.this, "Password Not Matching", Toast.LENGTH_SHORT);
                        toastMessage.show();
                    }

                }
            }
        });

        //signin function
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastManager();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    public void ToastManager(){
        if (toastMessage != null) {
            toastMessage.cancel();
        }
    }
}