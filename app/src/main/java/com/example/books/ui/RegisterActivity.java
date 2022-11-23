package com.example.books.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.books.R;
import com.example.books.data.DbHelper;

/**
 * RegisterActivity
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        EditText usernameEt = findViewById(R.id.register_username);
        EditText passwordEt = findViewById(R.id.register_password);
        EditText passwordConfirmEt = findViewById(R.id.register_password_confirm);
        // to register
        Button loginBtn = findViewById(R.id.register_submit);
        loginBtn.setOnClickListener(view -> {
            String username = usernameEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();
            String passwordConfirm = passwordConfirmEt.getText().toString().trim();
            doRegister(username, password, passwordConfirm);
        });

        // to login
        View toLogin = findViewById(R.id.register_to_login);
        toLogin.setOnClickListener(view -> {
            RegisterActivity.this.finish();
        });

    }

    private void doRegister(String username, String password, String passwordConfirm) {
        if (username.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter Username!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(passwordConfirm)) {
            Toast.makeText(getApplicationContext(), "Two passwords are different", Toast.LENGTH_SHORT).show();
            return;
        }
        DbHelper db = DbHelper.getInstance();
        if (db.existUser(username)) {
            Toast.makeText(getApplicationContext(), "Username:" + username + " exist! Please input new", Toast.LENGTH_SHORT).show();
            return;
        }
        db.register(username, password);
        Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Please login", Toast.LENGTH_LONG).show();
        RegisterActivity.this.finish();
    }
}