package com.alaaelshazly.freeyourmind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alaaelshazly.freeyourmind.Fragments.DilogFragment;
import com.alaaelshazly.freeyourmind.User.UserBook;
import com.alaaelshazly.freeyourmind.User.UserData;
import com.alaaelshazly.freeyourmind.User.UserDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListOfMoviesActivity extends AppCompatActivity {
    TextView tvName, tvAuthor, tvSummary, authorName;
    ImageView posterImage;
    CircleImageView imagePhoto;
    Book detail;
    DilogFragment dilogFragment = new DilogFragment();
    UserData mUserData;
    String email;
    int position;
    byte exists;
    UserBook Userbook;
    boolean addToMyBook;
    Button Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_movies);
        tvName = findViewById(R.id.tvName);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvSummary = findViewById(R.id.tvSummary);
        imagePhoto = findViewById(R.id.imagePhoto);
        posterImage = findViewById(R.id.posterImage);
        authorName = findViewById(R.id.authorName);
        Add = findViewById(R.id.addBook);

        detail = (Book) getIntent().getSerializableExtra("book");
        position = getIntent().getByteExtra("position", (byte) 0);
        addToMyBook = getIntent().getBooleanExtra("addToMyBook", false);

        Book book = new Book();
        tvName.setText(detail.getName());
        tvAuthor.setText(detail.getGenre());
        Picasso.get().load(detail.getPoster()).into(posterImage);
        tvSummary.setText(detail.getSummary());
        Picasso.get().load(detail.getPhoto()).into(imagePhoto);
        authorName.setText(detail.getAuthur());

        Userbook = new UserBook();
        mUserData = new UserData(ListOfMoviesActivity.this);
        email = mUserData.hasMail();

        if (addToMyBook) {
            Add.setVisibility(View.VISIBLE);
        } else {
            Add.setVisibility(View.INVISIBLE);
        }
    }

    public void pdf(View view) {
        switch (view.getId()) {
            case R.id.addBook:
                exists = UserDatabase.getInstance(ListOfMoviesActivity.this).userBookDAO().exists(detail.getName());
                if (exists > 0) {
                    Toast.makeText(this, "it's added before", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Userbook.Name = detail.getName();
                        Userbook.id = position;
                        long insert = UserDatabase.getInstance(ListOfMoviesActivity.this).userBookDAO().insert(Userbook);
                        if (insert > 0)
                            Toast.makeText(this, "Added" + insert, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, "Error" + insert, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.button4:
                if (mUserData.isLogin()) {
                    Intent in = new Intent(this, PdfActivity.class);
                    in.putExtra("book", detail);
                    startActivity(in);
                } else {
                    dilogFragment.show(getSupportFragmentManager(), "");
                }
                break;
        }

    }
}
