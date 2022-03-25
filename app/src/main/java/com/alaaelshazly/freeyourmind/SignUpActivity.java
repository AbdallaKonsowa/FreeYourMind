package com.alaaelshazly.freeyourmind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class SignUpActivity extends AppCompatActivity implements AsyncCallback<BackendlessUser> {
    EditText nameText, mailText, passText;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameText = findViewById(R.id.nameText);
        mailText = findViewById(R.id.loginMail);
        passText = findViewById(R.id.loginPass);
        animationView = findViewById(R.id.Sup_loading);


        //Transparent Action Bar
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    public void register2(View view) {
        try {
            animationView.setVisibility(View.VISIBLE);
            animationView.playAnimation();
            BackendlessUser user = new BackendlessUser();
            user.setEmail(mailText.getText().toString());
            user.setPassword(passText.getText().toString());
            user.setProperty("name", nameText.getText().toString());

            Backendless.UserService.register(user, this);
        } catch (Exception e) {
            animationView.pauseAnimation();
            animationView.setVisibility(View.INVISIBLE);

            Toast.makeText(this, "Please Fill All Data", Toast.LENGTH_SHORT).show();
        }

    }


    public void haveAccount(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void handleResponse(BackendlessUser response) {
        animationView.pauseAnimation();
        animationView.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "user saved", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void handleFault(BackendlessFault fault) {
        animationView.pauseAnimation();
        animationView.setVisibility(View.INVISIBLE);
        if (fault.getCode().equals("3033"))
            Toast.makeText(getApplicationContext(), "user exists", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "error in register", Toast.LENGTH_SHORT).show();
    }


}