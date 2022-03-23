package com.example.Trivia_Quest_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private ImageButton showPass;
    private Button btnlogin, btnsignup;
    private DatabaseHelper DB;
    private Toast toastMessage;
    private int b=1;

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
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.username1);
        password = (EditText)findViewById(R.id.password1);
        btnlogin = (Button)findViewById(R.id.btnsignin1);
        btnsignup = findViewById(R.id.btnsignup1);
        DB = new DatabaseHelper(this);
        showPass = findViewById(R.id.togglePassword);

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b==1) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    b=0;
                    showPass.setBackgroundResource(R.drawable.show_password);
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    b=1;
                    showPass.setBackgroundResource(R.drawable.hide_password);
                }
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("")||pass.equals("")) {
                    ToastManager();
                    toastMessage = Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT);
                    toastMessage.show();
                }
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user,pass);
                    if(checkuserpass == true){
                        ToastManager();
                        toastMessage = Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT);
                        toastMessage.show();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        intent.putExtra("username", username.getText().toString());
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }else{
                        ToastManager();
                        toastMessage = Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT);
                        toastMessage.show();
                    }
                }
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastManager();
                username.setText("");
                password.setText("");
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
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