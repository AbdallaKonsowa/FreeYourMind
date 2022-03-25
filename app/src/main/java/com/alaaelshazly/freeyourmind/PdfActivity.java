package com.alaaelshazly.freeyourmind;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class PdfActivity extends AppCompatActivity {
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        wv = findViewById(R.id.wv);

        MyWebViewClient client = new MyWebViewClient();
        wv.setWebViewClient(client);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);

        Book detail = (Book) getIntent().getSerializableExtra("book");
        Book book = new Book();
        wv.loadUrl("https://docs.google.com/viewer?url=" + detail.getPdf());

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}