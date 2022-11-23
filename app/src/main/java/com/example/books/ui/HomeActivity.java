package com.example.books.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
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
 * MainActivity
 */
public class HomeActivity extends AppCompatActivity {

    private BookAdapter mAdapter;
    private List<BookModel> mBooks = new ArrayList<>();

    TextView mBookmarkCount;
    TextView mLikeCount;

    Dialog mUserDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }
        // custom action bar
        findViewById(R.id.home_action_bookmark).setOnClickListener(v -> startActivity(new Intent(this, BookmarkActivity.class)));
        findViewById(R.id.home_action_like).setOnClickListener(v -> startActivity(new Intent(this, LikeListActivity.class)));
        findViewById(R.id.home_action_user).setOnClickListener(v -> showUserDialog());
        findViewById(R.id.home_action_search).setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));

        mBookmarkCount = findViewById(R.id.home_action_bookmark_count);
        mLikeCount = findViewById(R.id.home_action_like_count);

        // listView
        ListView listView = findViewById(R.id.home_list_view);
        mAdapter = new BookAdapter(this, R.layout.book_item, mBooks, true);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.i(HomeActivity.class.getSimpleName(), "onItemClick" + i);
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("book", mBooks.get(i));
            startActivity(intent);
        });

        initData();

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(HomeActivity.class.getSimpleName(), "broadcast_refresh_books");
                initData();
            }
        }, new IntentFilter(MainApplicationStart.BROADCAST_REFRESH_BOOKS));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                doLogin();
            }
        }, new IntentFilter(MainApplicationStart.BROADCAST_REOPEN_LOGIN));
    }

    /**
     * show user dialog
     */
    private void showUserDialog() {
        if (mUserDialog == null) {
            initUserDialog();
        }
        mUserDialog.show();
    }

    /**
     * 初始化分享弹出框
     */
    private void initUserDialog() {
        mUserDialog = new Dialog(this, R.style.dialog_bottom_full);
        mUserDialog.setCanceledOnTouchOutside(true);
        mUserDialog.setCancelable(true);
        Window window = mUserDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animation);
        View view = View.inflate(this, R.layout.user_dialog, null);
        view.findViewById(R.id.user_dialog_cancel).setOnClickListener(v -> {
            if (mUserDialog != null && mUserDialog.isShowing()) {
                mUserDialog.dismiss();
            }
        });
        // do logout
        view.findViewById(R.id.user_dialog_logout).setOnClickListener(v -> {
            doLogin();
        });
        view.findViewById(R.id.user_dialog_update_password).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, UpdatePasswordActivity.class));
            if (mUserDialog != null && mUserDialog.isShowing()) {
                mUserDialog.dismiss();
            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void doLogin() {
        MainApplicationStart.currentUser = null;
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        HomeActivity.this.finish();
    }

    private void initData() {
        UserModel currentUser = MainApplicationStart.currentUser;
        String userId = currentUser.getId();
        List<String> categories = Arrays.asList(currentUser.getTopic().split("@"));
        DbHelper db = DbHelper.getInstance();
        List<BookModel> books = db.getBooks(userId, categories);
        mBooks.clear();
        mBooks.addAll(books);

        mAdapter.notifyDataSetChanged();

        int countBookmark = db.countBookmark(userId);
        mBookmarkCount.setText(0 < countBookmark ? countBookmark + "" : "");

        int countLike = db.countLike(userId);
        mLikeCount.setText(0 < countLike ? countLike + "" : "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // close app
        System.exit(0);
    }


}