package com.example.books.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.books.MainApplicationStart;
import com.example.books.R;
import com.example.books.data.BookModel;
import com.example.books.data.DbHelper;

import java.util.List;

public class BookAdapter extends ArrayAdapter<BookModel> {

    private Context mContext;
    private boolean showCount;
    private String keywords;

    public BookAdapter(@NonNull Context context, int resource, @NonNull List<BookModel> objects, boolean showCount) {
        super(context, resource, objects);
        mContext = context;
        this.showCount = showCount;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BookModel book = getItem(position);
        String bookId = book.getId();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);

        ImageView image = view.findViewById(R.id.book_item_image);
        TextView title = view.findViewById(R.id.book_item_title);
        TextView author = view.findViewById(R.id.book_item_author);
        TextView publisher = view.findViewById(R.id.book_item_publisher);
        TextView likeCount = view.findViewById(R.id.book_item_like_count);
        TextView bookmarkCount = view.findViewById(R.id.book_item_bookmark_count);
        // set value
        image.setImageResource(book.getItemImageId());
        title.setText(MainApplicationStart.getSpannableString(keywords, "" + book.getTitle(), 0));
        author.setText(MainApplicationStart.getSpannableString(keywords, "BY:" + book.getAuthor(), "BY:".length()));
        if (null == book.getPublisher() || book.getPublisher().length() == 0) {
            publisher.setVisibility(View.GONE);
        } else {
            publisher.setVisibility(View.VISIBLE);
            publisher.setText(
                MainApplicationStart.getSpannableString(keywords, "PUBLISHER:" + book.getPublisher(), "PUBLISHER:".length()));
        }
        ImageView bookmark = view.findViewById(R.id.book_item_bookmark);
        bookmark.setImageResource(book.isBookmark() ? R.drawable.bookmark_select : R.drawable.bookmark_unselect);
        bookmark.setOnClickListener(v -> onClickBookmark(bookId, !book.isBookmark()));

        ImageView like = view.findViewById(R.id.book_item_like);
        like.setImageResource(book.isLike() ? R.drawable.like_select : R.drawable.like_unselect);
        like.setOnClickListener((v -> onClickLike(bookId, !book.isLike())));

        likeCount.setText(0 < book.getLikeCount() ? book.getLikeCount() + "" : "");
//        likeCount.setVisibility(showCount ? View.VISIBLE : View.GONE);

        bookmarkCount.setText(0 < book.getBookmarkCount() ? book.getBookmarkCount() + "" : "");
//        bookmarkCount.setVisibility(showCount ? View.VISIBLE : View.GONE);

        View actionView = view.findViewById(R.id.book_item_action);
        actionView.setVisibility(showCount ? View.VISIBLE : View.GONE);
        return view;
    }

    public void onClickBookmark(String bookId, boolean bookmark) {
        Log.i(BookAdapter.class.getSimpleName(), String.format("onClickBookmark:%s %s", bookId, bookmark ? "true" : "false"));
        String userId = MainApplicationStart.currentUser.getId();
        DbHelper db = DbHelper.getInstance();
        if (bookmark) {
            db.bookmark(bookId, userId);
        } else {
            db.unbookmark(bookId, userId);
        }
        refreshBooks();
    }

    private void refreshBooks() {
        Intent intent = new Intent();
        intent.setAction(MainApplicationStart.BROADCAST_REFRESH_BOOKS);
        mContext.sendBroadcast(intent);
    }

    public void onClickLike(String bookId, boolean like) {
        Log.i(BookAdapter.class.getSimpleName(), String.format("onClickLike:%s %s", bookId, like ? "true" : "false"));
        String userId = MainApplicationStart.currentUser.getId();
        DbHelper db = DbHelper.getInstance();
        if (like) {
            db.likeBook(bookId, userId);
        } else {
            db.unlikeBook(bookId, userId);
        }
        refreshBooks();
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

}
