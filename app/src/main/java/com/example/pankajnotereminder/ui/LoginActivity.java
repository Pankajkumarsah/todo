package com.example.pankajnotereminder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pankajnotereminder.R;
import com.example.pankajnotereminder.db.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText username, Password;
    Button loginBtn;

    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);


        username = (EditText) findViewById(R.id.my_username);
        Password = (EditText) findViewById(R.id.my_login_password);
        loginBtn = (Button) findViewById(R.id.my_loginBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pwd = Password.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if (res == true) {
                    Toast.makeText(LoginActivity.this, "logged in", Toast.LENGTH_SHORT).show();
                    Intent HomePage = new Intent(LoginActivity.this, Splash.class);
                    startActivity(HomePage);
                } else {
                    Toast.makeText(LoginActivity.this, "Login error found", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void registeraccount(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
