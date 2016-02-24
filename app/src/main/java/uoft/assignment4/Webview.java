package uoft.assignment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class Webview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        String name = intent.getStringExtra("query");
        WebView webview= (WebView)findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        name="www.google.ca/search?q=" +name.replace(" ","+");
        webview.loadUrl("https://www.google.ca/search?q="+name+"&gws_rd=ssl");
        Log.i("url",name);
    }
}
