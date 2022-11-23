package com.example.books.ui;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.books.MainApplicationStart;
import com.example.books.R;
import com.example.books.data.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * TopicActivity
 */
public class TopicActivity extends AppCompatActivity {

    private int[] ids = new int[]{R.id.topic_1, R.id.topic_2, R.id.topic_3, R.id.topic_4, R.id.topic_5, R.id.topic_6, R.id.topic_7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        // hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // topic select
        for (int id : ids) {
            findViewById(id).setOnClickListener(view -> {
                TextView textView = (TextView) view;
                Object tag = view.getTag();
                if ("1".equals(tag)) {
                    view.setTag("2");
                    textView.setBackgroundResource(R.drawable.topic_item_select);
                } else {
                    view.setTag("1");
                    textView.setBackgroundResource(R.drawable.topic_item);
                }
            });
        }


        findViewById(R.id.topic_submit).setOnClickListener(v -> doExec());
    }

    private void doExec() {

        List<String> strings = new ArrayList<>();

        for (int id : ids) {
            TextView textView = findViewById(id);
            if ("2".equals(textView.getTag())) {
                strings.add(textView.getText().toString());
            }
        }
        if (strings.isEmpty()) {
            Toast.makeText(this, "Please Select Book category", Toast.LENGTH_SHORT).show();
            return;
        }
        String topic = String.join("@", strings);
        Log.i(TopicActivity.class.getSimpleName(), "topic:" + topic);
        MainApplicationStart.currentUser.setTopic(topic);
        // set user topic
        DbHelper.getInstance().updateUserTopic(MainApplicationStart.currentUser.getId(), topic);
        // to main
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        // over
        TopicActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // close app
        System.exit(0);
    }
}