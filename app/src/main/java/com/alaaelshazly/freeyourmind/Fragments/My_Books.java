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

import com.alaaelshazly.freeyourmind.Book;
import com.alaaelshazly.freeyourmind.ListOfMoviesActivity;
import com.alaaelshazly.freeyourmind.R;
import com.alaaelshazly.freeyourmind.User.UserDatabase;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class My_Books extends Fragment implements AdapterView.OnItemClickListener {

    ArrayList<Book> MyBooks = new ArrayList<>();
    TextView search;
    search s = new search();
    ImageView imageView;
    TextView textView;
    List<String> ListNames;
    ListView my_Books;

    public My_Books() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_my_books, container, false);
        search = view.findViewById(R.id.broseMore);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.goToLibrary = true;
                EventBus.getDefault().post(s);
            }
        });
        imageView = view.findViewById(R.id.imageView5);
        textView = view.findViewById(R.id.noBooks);
        my_Books = view.findViewById(R.id.my_Books);
        my_Books.setOnItemClickListener(this);
        ListNames = UserDatabase.getInstance(getActivity()).userBookDAO().AllBooks();

        if (ListNames.isEmpty()) {
            imageView.setVisibility(view.VISIBLE);
            textView.setVisibility(view.VISIBLE);
            search.setVisibility(view.VISIBLE);
        } else {
            MyBooks.clear();
            LoadData loadData = new LoadData();
            loadData.execute();
            imageView.setVisibility(view.INVISIBLE);
            textView.setVisibility(view.INVISIBLE);
            search.setVisibility(view.INVISIBLE);
            my_Books.setVisibility(View.VISIBLE);
        }


        try {

        } catch (Exception e) {

            Toast.makeText(getActivity(), "Exception is :" + e, Toast.LENGTH_LONG).show();
        }


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Book detail = MyBooks.get(i);
        Intent in = new Intent(getActivity(), ListOfMoviesActivity.class);
        in.putExtra("addToMyBook", false);
        in.putExtra("book", detail);
        in.putExtra("position", i);
        startActivity(in);
    }

    public static class search {
        public Boolean goToLibrary = false;
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
                            for (String name : ListNames) {
                                if (book.getName().equalsIgnoreCase(name)) {
                                    MyBooks.add(book);
                                    break;
                                }
                            }
                        }
                        BooksAdapter adapter = new BooksAdapter(getActivity(), MyBooks);
                        my_Books.setAdapter(adapter);

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


    public class BooksAdapter extends ArrayAdapter<Book> {

        public BooksAdapter(@NonNull Context context, List<Book> list) {
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


}