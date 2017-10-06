package com.shubham.instagramclone;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText userName;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ConstraintLayout layount = (ConstraintLayout) findViewById(R.id.background);
        ImageView logo = (ImageView) findViewById(R.id.logoImg);
        layount.setOnClickListener(this);
        logo.setOnClickListener(this);
        if (ParseUser.getCurrentUser().getUsername() != null) {
            showUsers();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void signUp(View view) {
        if (userName.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(this, "A username and a password are required.", Toast.LENGTH_SHORT).show();
        } else {
            ParseUser user = new ParseUser();
            user.setUsername(userName.getText().toString());
            user.setPassword(password.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                        showUsers();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void logIn(View view) {
        ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Toast.makeText(MainActivity.this, "Log in successful", Toast.LENGTH_SHORT).show();
                    showUsers();
                } else {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.background || v.getId() == R.id.logoImg) {
            InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showUsers() {
        Intent intent = new Intent(getApplication(), UserListActivity.class);
        startActivity(intent);
    }
}
