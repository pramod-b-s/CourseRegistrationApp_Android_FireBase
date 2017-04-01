package com.google.pramodbs.collegehelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class GameActivity extends AppCompatActivity {

    private WebView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        view=(WebView)findViewById(R.id.wv1);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl("https://sharath29.github.io/snake/");
    }
}
