package com.alaaelshazly.freeyourmind;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alaaelshazly.freeyourmind.Fragments.Library;
import com.alaaelshazly.freeyourmind.Fragments.My_Books;
import com.alaaelshazly.freeyourmind.Fragments.Search;
import com.backendless.Backendless;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity2 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Library library = new Library();
    My_Books my_books = new My_Books();
    Search search = new Search();
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        Backendless.initApp(this, "67D6ED81-C5CB-A3BF-FFA9-F6619DACA800", "1AFA5BF7-95B3-42C3-90F3-66816C1435F0");

        getSupportFragmentManager().beginTransaction().add(R.id.counter, my_books).commit();

        EventBus.getDefault().register(this);
        navigationView = findViewById(R.id.nav_view);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void OnMessage(My_Books.search s) {
        if (s.goToLibrary) {
            getSupportFragmentManager().beginTransaction().replace(R.id.counter, library).commit();
            onNavigationItemSelected(navigationView.getMenu().getItem(1).setChecked(true));
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.My_books_navigation) {
            getSupportFragmentManager().beginTransaction().replace(R.id.counter, my_books).commit();
        } else if (item.getItemId() == R.id.Library_navigation) {
            getSupportFragmentManager().beginTransaction().replace(R.id.counter, library).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.counter, search).commit();
        }
        return true;
    }
}