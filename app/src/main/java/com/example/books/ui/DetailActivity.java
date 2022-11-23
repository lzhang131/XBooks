package com.example.books.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.books.MainApplicationStart;
import com.example.books.R;
import com.example.books.data.BookModel;
import com.example.books.data.DbHelper;

/**
 * DetailActivity
 */
public class DetailActivity extends AppCompatActivity {

    ImageView bookmarkImageView;
    ImageView likeImageView;
    TextView mBookmarkCount;
    TextView mLikeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }
        ImageView left = findViewById(R.id.action_bar_left);
        left.setImageResource(R.drawable.home_menu);
        left.setOnClickListener(v -> this.finish());// do back


        // hide other
        findViewById(R.id.action_bar_title).setVisibility(View.GONE);
        findViewById(R.id.action_bar_search).setVisibility(View.GONE);
        findViewById(R.id.action_bar_right).setVisibility(View.GONE);

        BookModel book = (BookModel) getIntent().getSerializableExtra("book");
        String keywords = getIntent().getStringExtra("keywords");

        // set book info
        TextView title = findViewById(R.id.detail_title);
        title.setText(MainApplicationStart.getSpannableString(keywords, "" + book.getTitle(), 0));
        TextView author = findViewById(R.id.detail_author);
        author.setText(MainApplicationStart.getSpannableString(keywords, "BY:" + book.getAuthor(), 3));
        TextView publisher = findViewById(R.id.detail_publisher);
        if (null == book.getPublisher() || book.getPublisher().length() == 0) {
            publisher.setVisibility(View.GONE);
        } else {
            publisher.setVisibility(View.VISIBLE);
            publisher.setText(MainApplicationStart.getSpannableString(keywords, "PUBLISHER:" + book.getPublisher(), 10));
        }
        TextView intro = findViewById(R.id.detail_book_intro);
        intro.setText(book.getIntro());

        ImageView bookImage = findViewById(R.id.detail_book_image);
        bookImage.setImageResource(book.getDetailImageId());

        mBookmarkCount = findViewById(R.id.detail_bookmark_count);
        mBookmarkCount.setText(0 < book.getBookmarkCount() ? book.getBookmarkCount() + "" : "");

        mLikeCount = findViewById(R.id.detail_like_count);
        mLikeCount.setText(0 < book.getLikeCount() ? book.getLikeCount() + "" : "");

        bookmarkImageView = findViewById(R.id.detail_bookmark);
        bookmarkImageView.setImageResource(book.isBookmark() ? R.drawable.bookmark_select : R.drawable.bookmark_unselect);
        bookmarkImageView.setOnClickListener(v -> {
            book.setBookmark(!book.isBookmark());
            doBookmark(book.getId(), book.isBookmark());
            bookmarkImageView.setImageResource(book.isBookmark() ? R.drawable.bookmark_select : R.drawable.bookmark_unselect);
        });

        likeImageView = findViewById(R.id.detail_like);
        likeImageView.setImageResource(book.isLike() ? R.drawable.like_select : R.drawable.like_unselect);
        likeImageView.setOnClickListener(v -> {
            book.setLike(!book.isLike());
            doLike(book.getId(), book.isLike());
            likeImageView.setImageResource(book.isLike() ? R.drawable.like_select : R.drawable.like_unselect);
        });



    }

    private void doLike(String bookId, boolean like) {
        String userId = MainApplicationStart.currentUser.getId();
        DbHelper db = DbHelper.getInstance();
        if (like) {
            db.likeBook(bookId, userId);
        } else {
            db.unlikeBook(bookId, userId);
        }
        int count = db.countAllLike(bookId);
        mLikeCount.setText(0 < count ? count + "" : "");

    }

    private void doBookmark(String bookId, boolean bookmark) {
        String userId = MainApplicationStart.currentUser.getId();
        DbHelper db = DbHelper.getInstance();
        if (bookmark) {
            db.bookmark(bookId, userId);
        } else {
            db.unbookmark(bookId, userId);
        }
        int count = db.countAllBookmark(bookId);
        mBookmarkCount.setText(0 < count ? count + "" : "");
    }
}