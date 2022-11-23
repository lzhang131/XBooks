package com.example.books.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.books.MainApplicationStart;

import java.util.*;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "books-c.db";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 2);
    }

    private static DbHelper instance;

    public static DbHelper getInstance() {
        if (null == instance) {
            instance = new DbHelper(MainApplicationStart.getContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(DbHelper.class.getSimpleName(), "onCreate " + DB_NAME);
        db.execSQL("CREATE TABLE user (id VARCHAR PRIMARY KEY NOT NULL, username VARCHAR, password VARCHAR, topic VARCHAR)");
        db.execSQL("CREATE TABLE book_bookmark (book_id VARCHAR, user_id VARCHAR)");
        db.execSQL("CREATE TABLE book_like (book_id VARCHAR, user_id VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(DbHelper.class.getSimpleName(), String.format("onUpgrade oldVersion:%d newVersion:%d", oldVersion, newVersion));
        db.execSQL("DROP TABLE IF EXISTS user " );
        db.execSQL("DROP TABLE IF EXISTS book_bookmark " );
        db.execSQL("DROP TABLE IF EXISTS book_like " );
        onCreate(db);
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from user where username = ? and password = ? ", new String[]{username, password});
        return result.getCount() > 0;
    }

    public boolean existUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from user where username = ? ", new String[]{username});
        return result.getCount() > 0;
    }

    /**
     * register user
     *
     * @param username
     * @param password
     */
    public void register(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();
        cn.put("id", UUID.randomUUID().toString());
        cn.put("username", username);
        cn.put("password", password);
        db.insert("user", null, cn);
    }

    /**
     * register user
     *
     * @param id
     * @param password
     */
    public boolean updatePassword(String id, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();
        cn.put("id", id);
        cn.put("password", password);

        long res = db.update("user", cn, "id=?", new String[]{id});
        return res > 0;
    }

    /**
     * query user
     * @param username
     * @return
     */
    @SuppressLint("Range")
    public UserModel getUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username = ? ", new String[]{username});
        if (null != cursor && cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String topic = cursor.getString(cursor.getColumnIndex("topic"));

            UserModel user = new UserModel();
            user.setId(id);
            user.setUsername(name);
            user.setPassword(password);
            user.setTopic(topic);
            return user;
        }
        return null;
    }

    public boolean updateUserTopic(String id, String topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();
        cn.put("id", id);
        cn.put("topic", topic);

        long res = db.update("user", cn, "id=?", new String[]{id});
        return res > 0;

    }

    /**
     * load categories book
     * categories == null ? all
     * @param userId
     * @param categories
     * @return
     */
    @SuppressLint("Range")
    public List<BookModel> getBooks(String userId, List<String> categories) {
        List<BookModel> books = new ArrayList<>();
        for (String category : Books.hub.keySet()) {
            if (null == categories || categories.contains(category)) {
                books.addAll(Books.hub.get(category));
            }
        }
        SQLiteDatabase db = this.getWritableDatabase();

        // bookmark ids
        Cursor cursor = db.rawQuery("select * from book_bookmark where user_id = ? ", new String[]{userId});
        List<String> bookIds = new ArrayList<>();
        while (null != cursor && cursor.moveToNext()) {
            String bookId = cursor.getString(cursor.getColumnIndex("book_id"));
            bookIds.add(bookId);
        }
        for (BookModel book : books) {
            book.setBookmark(bookIds.contains(book.getId()));
        }
        bookIds.clear();

        Cursor cursorBookmarkGroup = db.rawQuery("select count(*) total, book_id from book_bookmark group by book_id ", null);
        Map<String, Integer> bookmarkIds = new HashMap<>();
        while (null != cursorBookmarkGroup && cursorBookmarkGroup.moveToNext()) {
            String bookId = cursorBookmarkGroup.getString(cursorBookmarkGroup.getColumnIndex("book_id"));
            int total = cursorBookmarkGroup.getInt(cursorBookmarkGroup.getColumnIndex("total"));
            bookmarkIds.put(bookId, total);
        }
        for (BookModel book : books) {
            Integer total = bookmarkIds.get(book.getId());
            book.setBookmarkCount(null == total ? 0 : total);
        }

        // like ids
        Cursor cursorLike = db.rawQuery("select * from book_like where user_id = ? ", new String[]{userId});
        while (null != cursorLike && cursorLike.moveToNext()) {
            String bookId = cursorLike.getString(cursorLike.getColumnIndex("book_id"));
            bookIds.add(bookId);
        }
        for (BookModel book : books) {
            book.setLike(bookIds.contains(book.getId()));
        }
        Map<String, Integer> likeIds = new HashMap<>();
        Cursor cursorLikeGroup = db.rawQuery("select count(*) total, book_id from book_like group by book_id ", null);
        while (null != cursorLikeGroup && cursorLikeGroup.moveToNext()) {
            String bookId = cursorLikeGroup.getString(cursorLikeGroup.getColumnIndex("book_id"));
            int total = cursorLikeGroup.getInt(cursorLikeGroup.getColumnIndex("total"));
            likeIds.put(bookId, total);
        }

        for (BookModel book : books) {
            Integer total = likeIds.get(book.getId());
            book.setLikeCount(null == total ? 0 : total);
        }
        return books;
    }

    public void bookmark(String bookId, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into book_bookmark(book_id, user_id) values (?, ?)", new Object[]{bookId, userId});
        db.close();
    }

    public void unbookmark(String bookId, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from book_bookmark where book_id = ? and user_id = ? ", new Object[]{bookId, userId});
        db.close();
    }

    public void likeBook(String bookId, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into book_like(book_id, user_id) values (?, ?)", new Object[]{bookId, userId});
        db.close();
    }

    public void unlikeBook(String bookId, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from book_like where book_id = ? and user_id = ? ", new Object[]{bookId, userId});
        db.close();
    }

    public List<BookModel> searchBooks(String userId, String keywords, List<String> categories) {
        List<BookModel> books = getBooks(userId, categories);
        List<BookModel> result = new ArrayList<>();
        // default all
        boolean empty = null == keywords || keywords.trim().length() == 0;
        for (BookModel book : books) {
            if (empty) {
                result.add(book);
            } else {
                String k = keywords.trim().toLowerCase();
                if (book.getTitle().toLowerCase().contains(k) ||
                        book.getAuthor().toLowerCase().contains(k) ||
                        book.getPublisher().toLowerCase().contains(k)) {
                    result.add(book);
                }
            }
        }
        return result;
    }

    public List<BookModel> listBookmark(String userId) {
        List<BookModel> books = getBooks(userId, null);
        List<BookModel> result = new ArrayList<>();
        for (BookModel book : books) {
            if (book.isBookmark()) {
                result.add(book);
            }
        }
        return result;
    }

    public List<BookModel> listLike(String userId) {
        List<BookModel> books = getBooks(userId, null);
        List<BookModel> result = new ArrayList<>();

        for (BookModel book : books) {
            if (book.isLike()) {
                result.add(book);
            }
        }
        return result;
    }

    @SuppressLint("Range")
    public int countBookmark(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) total from book_bookmark where user_id = ? ", new String[]{userId});
        while (null != cursor && cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("total"));
        }
        return 0;
    }

    @SuppressLint("Range")
    public int countLike(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) total from book_like where user_id = ? ", new String[]{userId});
        while (null != cursor && cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("total"));
        }
        return 0;
    }

    @SuppressLint("Range")
    public int countAllBookmark(String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) total from book_bookmark where book_id = ? ", new String[]{bookId});
        while (null != cursor && cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("total"));
        }
        return 0;
    }

    @SuppressLint("Range")
    public int countAllLike(String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) total from book_like where book_id = ? ", new String[]{bookId});
        while (null != cursor && cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("total"));
        }
        return 0;
    }
}

