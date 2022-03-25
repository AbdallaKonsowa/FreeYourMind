package com.alaaelshazly.freeyourmind.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alaaelshazly.freeyourmind.Book;
import com.alaaelshazly.freeyourmind.R;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment implements View.OnClickListener {

    EditText searchET;
    TextView introTV;
    String SearchText;
    ListView ResultList;
    ImageView introImage, micImage, SearchImageView;
    ArrayList<Book> ResultBooks = new ArrayList<>();
    ArrayList<Book> AllBooks = new ArrayList<>();
    boolean changeImage = true;


    public Search() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_search, container, false);

        introTV = view.findViewById(R.id.introTV);
        introImage = view.findViewById(R.id.introImage);

        micImage = view.findViewById(R.id.mic);
        micImage.setOnClickListener(this);

        SearchImageView = view.findViewById(R.id.SearchImageView);
        SearchImageView.setOnClickListener(this);
        ResultList = view.findViewById(R.id.result_LV);

        if (AllBooks.isEmpty()) {
            Load_Data loadData = new Load_Data();
            loadData.execute();
        }

        searchET = view.findViewById(R.id.searchET);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int startIndex, int endIndex) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int startIndex, int endIndex) {
                if (endIndex != i) {
                    changeImage = true;
                    introTV.setVisibility(View.INVISIBLE);
                    introImage.setVisibility(View.INVISIBLE);
                    micImage.setImageResource(R.drawable.clear);
                    ResultList.setVisibility(View.VISIBLE);


                } else if (endIndex == i) {
                    ResultBooks.clear();
                    introTV.setVisibility(View.VISIBLE);
                    introImage.setVisibility(View.VISIBLE);
                    micImage.setImageResource(R.drawable.mic);
                    ResultList.setVisibility(View.INVISIBLE);
                    //     search.setBackgroundResource(R.drawable.search);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SearchText = searchET.getText().toString();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.mic:
                if (changeImage) {
                    searchET.setText("");
                    micImage.setImageResource(R.drawable.mic);
                    changeImage = false;
                }
                break;

            case R.id.SearchImageView:
                ResultBooks.clear();
                for (Book book : AllBooks) {
                    if (book.getName().equalsIgnoreCase(SearchText)) {
                        ResultBooks.add(book);
                    }
                }


                if (ResultBooks.isEmpty())
                    Toast.makeText(getActivity(), "This Book is't Exist", Toast.LENGTH_LONG).show();
                else {
                    BooksAdapter adapter = new BooksAdapter(getActivity(), ResultBooks);
                    ResultList.setAdapter(adapter);
                }
                break;
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

    class Load_Data extends AsyncTask<Void, Void, String> {

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