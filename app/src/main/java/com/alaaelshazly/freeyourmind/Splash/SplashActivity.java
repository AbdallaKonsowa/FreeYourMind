package com.alaaelshazly.freeyourmind.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.alaaelshazly.freeyourmind.MainActivity;
import com.alaaelshazly.freeyourmind.MainActivity2;
import com.alaaelshazly.freeyourmind.R;
import com.alaaelshazly.freeyourmind.User.UserData;
import com.backendless.Backendless;

public class SplashActivity extends AppCompatActivity {

    UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mUserData = new UserData(SplashActivity.this);
        splashTimer();
        Backendless.initApp(this, "67D6ED81-C5CB-A3BF-FFA9-F6619DACA800", "1AFA5BF7-95B3-42C3-90F3-66816C1435F0");
        //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }


    public void splashTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mUserData.isLogin()) {
                    Intent i = new Intent(SplashActivity.this, MainActivity2.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2250);
    }

}