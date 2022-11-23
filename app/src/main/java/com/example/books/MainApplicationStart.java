package com.example.books;

import android.app.Application;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import com.example.books.data.UserModel;

/**
 * MainApplicationStart
 */
public class MainApplicationStart extends Application {

    public final static String BROADCAST_REFRESH_BOOKS = "broadcast.refresh.books";
    public final static String BROADCAST_REOPEN_LOGIN = "broadcast.reopen.login";
    private static Context mContext;

    public static UserModel currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }


    public static SpannableString getSpannableString(String keywords, String source, int prefix) {
        SpannableString spannableString = new SpannableString(source);
        if (null == keywords || keywords.trim().length() == 0) {
            return spannableString;
        }

        int color = MainApplicationStart.getContext().getResources().getColor(R.color.taobao_color);

        String k = keywords.trim().toLowerCase();
        int keywordsLength = k.length();
        String text = source.toLowerCase().substring(prefix);
        int offset = 0;
        while (true) {
            int start = text.indexOf(k);
            if (-1 == start) {
                break;
            }
            int end = start + keywordsLength;
            text = text.substring(end);
            spannableString.setSpan(new ForegroundColorSpan(color), prefix + offset + start, prefix + offset + end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            offset += end;
        }
        return spannableString;
    }
}
