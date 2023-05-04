package com.example.task11_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        browser=findViewById(R.id.webBrowser);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setWebViewClient(new WebViewClient());
    }

    public void openWeb(View view) {
        Button btn = (Button) view;
        switch (btn.getText().toString()) {
            case "Найти":
                EditText editText = findViewById(R.id.wiki);
                String search = editText.getText().toString();
                browser.loadUrl("http://ru.wikipedia.org/wiki/" + search);
                break;
            case "Назад":
                if (browser.canGoBack())
                    browser.goBack();
                else
                    Toast.makeText(this, "Невозможно перейти назад", Toast.LENGTH_SHORT).show();
                break;
            case "Вперед":
                if (browser.canGoForward())
                    browser.goForward();
                else
                    Toast.makeText(this, "Невозможно перейти вперед", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void closeAll(View view) {
        browser.clearCache(true);
        browser.clearHistory();
        Toast.makeText(this, "История очищена!", Toast.LENGTH_SHORT).show();
    }

    public void goToHttp(View view) {
        Intent intent = new Intent(this, HttpActivity.class);
        startActivity(intent);
    }
}