package com.alaaelshazly.freeyourmind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alaaelshazly.freeyourmind.User.UserData;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {
    EditText loginMail, loginPass;
    UserData mUserData;
    CheckBox rememberCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginMail = findViewById(R.id.loginMail);
        loginPass = findViewById(R.id.loginPass);
        mUserData = new UserData(LoginActivity.this);
        rememberCheckBox = findViewById(R.id.remember);

    }

    public void createAccount(View view) {
        Intent in = new Intent(this, SignUpActivity.class);
        startActivity(in);
        finish();
    }

    public void login(View view) {
        switch (view.getId()) {

            case R.id.button3:
                String mail = loginMail.getText().toString();
                String pass = loginPass.getText().toString();

                Backendless.UserService.login(mail, pass, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        mUserData.saveData(mail, pass, rememberCheckBox.isChecked());
                        Intent in = new Intent(LoginActivity.this, MainActivity2.class);
                        startActivity(in);
                        finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        if (fault.getCode().equals("3003"))
                            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(LoginActivity.this, "error in login", Toast.LENGTH_SHORT).show();
                    }
                });

                break;


            case R.id.goback:
                Intent in = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(in);
                finish();
                break;
        }

    }


    public void forgetPass(View view) {

    }
}