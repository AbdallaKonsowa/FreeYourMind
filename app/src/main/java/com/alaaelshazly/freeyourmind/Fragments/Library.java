package com.alaaelshazly.freeyourmind.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.alaaelshazly.freeyourmind.Book;
import com.alaaelshazly.freeyourmind.ListOfMoviesActivity;
import com.alaaelshazly.freeyourmind.R;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Library extends Fragment implements AdapterView.OnItemClickListener {

    ArrayList<Book> AllBooks = new ArrayList<>();
    ListView list;
    TextView loadingTV;
    LottieAnimationView animationView;

    public Library() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library, container, false);
        list = view.findViewById(R.id.libarby_list);
        loadingTV = view.findViewById(R.id.loadingTV);
        animationView = view.findViewById(R.id.Lib_loading);
        loadingTV.setVisibility(View.VISIBLE);
        animationView.setVisibility(View.VISIBLE);
        animationView.playAnimation();

        if (AllBooks.isEmpty()) {
            LoadData loadData = new LoadData();
            loadData.execute();
        } else {
            LibraryAdapter adapter = new LibraryAdapter(getActivity(), AllBooks);
            list.setAdapter(adapter);
            list.setOnItemClickListener(Library.this);
            loadingTV.setVisibility(View.INVISIBLE);
            animationView.setVisibility(View.INVISIBLE);
            animationView.pauseAnimation();
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Book detail = AllBooks.get(position);
        Intent in = new Intent(getActivity(), ListOfMoviesActivity.class);
        in.putExtra("addToMyBook", true);
        in.putExtra("book", detail);
        in.putExtra("position", position);
        startActivity(in);

    }


    public class LibraryAdapter extends ArrayAdapter<Book> {

        public LibraryAdapter(@NonNull Context context, List<Book> list) {
            super(context, 0, list);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_view, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();

            }

            holder.book_name.setText(getItem(position).getName());
            holder.book_Author.setText(getItem(position).getAuthur());

            Picasso.get().load(getItem(position).getPoster()).into(holder.book_image);

            return convertView;
        }

        class ViewHolder {
            TextView book_name, book_Author;
            ImageView book_image;

            public ViewHolder(View convertView) {
                book_name = convertView.findViewById(R.id.tvTitle);
                book_Author = convertView.findViewById(R.id.tvContent);

                book_image = convertView.findViewById(R.id.imageView4);

            }
        }
    }

    class LoadData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                DataQueryBuilder builder = DataQueryBuilder.create();
                builder.setPageSize(20);
                builder.setSortBy("name");
                Backendless.Data.of(Book.class).find(builder, new AsyncCallback<List<Book>>() {
                    @Override
                    public void handleResponse(List<Book> response) {
                        for (Book book : response) {
                            AllBooks.add(book);
                        }
                        LibraryAdapter adapter = new LibraryAdapter(getActivity(), AllBooks);
                        list.setAdapter(adapter);
                        list.setOnItemClickListener(Library.this);
                        loadingTV.setVisibility(View.INVISIBLE);
                        animationView.setVisibility(View.INVISIBLE);
                        animationView.pauseAnimation();
//                list.setOnItemSelectedListener(Library.this);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getActivity(), "Book loading problem", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}