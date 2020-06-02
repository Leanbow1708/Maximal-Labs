package com.iniesta.learningbackgroundtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowTweetsActivity extends AppCompatActivity {


    PreferenceHelper helper;
    TextView txtTweets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tweets);

        txtTweets = findViewById(R.id.txtTweets);
        helper = new PreferenceHelper(ShowTweetsActivity.this);
        txtTweets.setText(helper.getData());
    }
}
