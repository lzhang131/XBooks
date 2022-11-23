package com.example.books.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.books.MainApplicationStart;
import com.example.books.R;
import com.example.books.adapter.BookAdapter;
import com.example.books.data.BookModel;
import com.example.books.data.DbHelper;
import com.example.books.data.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SearchActivity
 */
public class SearchActivity extends AppCompatActivity {

    private BookAdapter mAdapter;
    private List<BookModel> mBooks = new ArrayList<>();
    private String keywords = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }
        ImageView left = findViewById(R.id.action_bar_left);
        left.setImageResource(R.drawable.home_menu);
        left.setOnClickListener(v -> this.finish());// do back

        EditText search = findViewById(R.id.action_bar_search);
        search.setVisibility(View.VISIBLE);// show

        findViewById(R.id.action_bar_title).setVisibility(View.GONE);// hide

        findViewById(R.id.action_bar_right).setOnClickListener(v -> {
            keywords = search.getText().toString();
            initData();
        });
        // listView
        ListView listView = findViewById(R.id.search_list_view);
        mAdapter = new BookAdapter(this, R.layout.book_item, mBooks, true);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.i(SearchActivity.class.getSimpleName(), "onItemClick" + i);
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("book", mBooks.get(i));
            intent.putExtra("keywords", keywords);
            startActivity(intent);
        });
        initData();

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(SearchActivity.class.getSimpleName(), "broadcast_refresh_books");
                initData();
            }
        }, new IntentFilter(MainApplicationStart.BROADCAST_REFRESH_BOOKS));
    }


    private void initData() {

        mAdapter.setKeywords(keywords);

        UserModel currentUser = MainApplicationStart.currentUser;
        String userId = currentUser.getId();
        List<String> categories = Arrays.asList(currentUser.getTopic().split(","));
        DbHelper db = DbHelper.getInstance();
        List<BookModel> books = db.searchBooks(userId, keywords, categories);
        mBooks.clear();
        mBooks.addAll(books);

        if (books.isEmpty()) {
            findViewById(R.id.search_list_view).setVisibility(View.GONE);
            findViewById(R.id.search_empty).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.search_list_view).setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

}