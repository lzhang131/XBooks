package com.example.books.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.books.MainApplicationStart;
import com.example.books.R;
import com.example.books.data.DbHelper;

/**
 * RegisterActivity
 */
public class UpdatePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        // hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        EditText oldPasswordEt = findViewById(R.id.update_password_old_password);
        EditText newPasswordEt = findViewById(R.id.update_password_new_password);
        EditText passwordConfirmEt = findViewById(R.id.update_password_password_confirm);
        // to register
        Button loginBtn = findViewById(R.id.update_password_submit);
        loginBtn.setOnClickListener(view -> {
            String oldPassword = oldPasswordEt.getText().toString().trim();
            String newPassword = newPasswordEt.getText().toString().trim();
            String passwordConfirm = passwordConfirmEt.getText().toString().trim();
            doUpdatePassword(oldPassword, newPassword, passwordConfirm);
        });

        // back home
        View toHome = findViewById(R.id.update_password_to_home);
        toHome.setOnClickListener(view -> {
            UpdatePasswordActivity.this.finish();
        });

    }

    private void doUpdatePassword(String oldPassword, String newPassword, String passwordConfirm) {
        if (oldPassword.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter Old Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassword.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter New Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPassword.equals(passwordConfirm)) {
            Toast.makeText(getApplicationContext(), "Two passwords are different", Toast.LENGTH_SHORT).show();
            return;
        }
        DbHelper db = DbHelper.getInstance();
        if (!db.login(MainApplicationStart.currentUser.getUsername(), oldPassword)) {
            Toast.makeText(getApplicationContext(), "Old Password Error!", Toast.LENGTH_SHORT).show();
            return;
        }
        db.updatePassword(MainApplicationStart.currentUser.getId(), newPassword);
        Toast.makeText(getApplicationContext(), "Update Password successfully", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Please login again!", Toast.LENGTH_LONG).show();
        sendBroadcast(new Intent(MainApplicationStart.BROADCAST_REOPEN_LOGIN));
        UpdatePasswordActivity.this.finish();
    }
}