package com.shubham.instagramclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        layout = (LinearLayout) findViewById(R.id.layout);
        Intent intent = getIntent();
        String activeUserName = intent.getStringExtra("username");
        setTitle(activeUserName + "'s Feed");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
        query.whereEqualTo("username", activeUserName);
        query.orderByDescending("createdA");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e== null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            ParseFile file = (ParseFile) object.get("image");
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (e == null && data != null) {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        ImageView img = new ImageView(getApplicationContext());
                                        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        img.setImageBitmap(bitmap);
                                        layout.addView(img);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });

    }
}
