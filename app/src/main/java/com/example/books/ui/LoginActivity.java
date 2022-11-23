package com.example.books.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.books.MainApplicationStart;
import com.example.books.R;
import com.example.books.data.DbHelper;
import com.example.books.data.UserModel;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        EditText usernameEt = findViewById(R.id.login_username);
        EditText passwordEt = findViewById(R.id.login_password);
        // to register
        Button loginBtn = findViewById(R.id.login_submit);
        loginBtn.setOnClickListener(view -> {
            String username = usernameEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();
            doLogin(username, password);
        });

        // to register
        View toRegister = findViewById(R.id.login_to_register);
        toRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

    }

    private void doLogin(String username, String password) {
        if (username.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter Username!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        DbHelper db = DbHelper.getInstance();
        UserModel user = db.getUser(username);
        if (null == user) {
            Toast.makeText(getApplicationContext(), "Username: " + username + " not exist!", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean succ = db.login(username, password);
        if (succ) {
            LoginActivity.this.finish();
            String topic = user.getTopic();
            MainApplicationStart.currentUser = user;// set currentUser

            Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), TopicActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Password Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // close app
        System.exit(0);
    }
}