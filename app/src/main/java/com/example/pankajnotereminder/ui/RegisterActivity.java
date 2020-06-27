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

public class RegisterActivity extends AppCompatActivity {

    EditText usersName,usersPassword,usersemail,usersConfirmpassword;
    Button usersRegisterbtn;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        usersName =(EditText)findViewById(R.id.et_name);
        usersemail=(EditText)findViewById(R.id.et_email);
        usersPassword=(EditText)findViewById(R.id.et_password);
        usersConfirmpassword=(EditText)findViewById(R.id.et_confirm_password);
        usersRegisterbtn=(Button) findViewById(R.id.et_createAcc_Btn);

        usersRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usersName.getText().toString().trim();
                String pwd = usersPassword.getText().toString().trim();
                String cnf_pwd = usersConfirmpassword.getText().toString().trim();

                if(pwd.equals(cnf_pwd)){
                    long val = db.addUser(user,pwd);
                    if(val > 0){
                        Toast.makeText(RegisterActivity.this,"you are registered succesfully",Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Registeration error found",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(RegisterActivity.this,"Password didnt match please type correctly",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }


    public void gotoLoginpage(View view) {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
