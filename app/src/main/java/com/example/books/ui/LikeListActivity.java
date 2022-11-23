package com.example.books.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.books.MainApplicationStart;
import com.example.books.R;
import com.example.books.adapter.BookAdapter;
import com.example.books.data.BookModel;
import com.example.books.data.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * LikeListActivity
 */
public class LikeListActivity extends AppCompatActivity {

    private BookAdapter mAdapter;
    private List<BookModel> mBooks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_list);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }
        ImageView left = findViewById(R.id.action_bar_left);
        left.setImageResource(R.drawable.home_menu);
        left.setOnClickListener(v -> this.finish());// do back

        TextView title = findViewById(R.id.action_bar_title);
        title.setText("Like");

        findViewById(R.id.action_bar_right).setVisibility(View.GONE);
        // listView
        ListView listView = findViewById(R.id.bookmark_list_view);
        mAdapter = new BookAdapter(this, R.layout.book_item, mBooks, false);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.i(LikeListActivity.class.getSimpleName(), "onItemClick" + i);
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("book", mBooks.get(i));
            startActivity(intent);
        });

        initData();

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(LikeListActivity.class.getSimpleName(), "broadcast_refresh_books");
                initData();
            }
        }, new IntentFilter(MainApplicationStart.BROADCAST_REFRESH_BOOKS));
    }

    private void initData() {
        DbHelper db = DbHelper.getInstance();
        List<BookModel> books = db.listLike(MainApplicationStart.currentUser.getId());
        mBooks.clear();
        mBooks.addAll(books);

        if (books.isEmpty()) {
            findViewById(R.id.bookmark_list_view).setVisibility(View.GONE);
            findViewById(R.id.bookmark_empty).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.bookmark_list_view).setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

}