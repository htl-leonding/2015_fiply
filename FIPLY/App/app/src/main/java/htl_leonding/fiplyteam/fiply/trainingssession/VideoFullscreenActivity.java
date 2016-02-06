package htl_leonding.fiplyteam.fiply.trainingssession;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import htl_leonding.fiplyteam.fiply.R;

public class VideoFullscreenActivity extends Activity {

    String videoLink;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_videofullscreen);
        webView = (WebView) findViewById(R.id.wvUebungsVideoFull);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(getIntent().getStringExtra("videoLink"));
    }
}
