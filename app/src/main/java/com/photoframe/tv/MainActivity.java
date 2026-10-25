package com.photoframe.tv;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView web;
    private final Handler handler = new Handler(Looper.getMainLooper());

    /** Lets the page call AndroidApp.exit() */
    public class Bridge {
        @JavascriptInterface
        public void exit() {
            runOnUiThread(MainActivity.this::finish);
        }

        @JavascriptInterface
        public void media(String which) {
            Web.mediaKey(MainActivity.this, which);
        }
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        web = new WebView(this);
        Web.setup(web, new Bridge());
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView v, WebResourceRequest r, WebResourceError e) {
                if (r.isForMainFrame()) {
                    v.loadDataWithBaseURL(null,
                        "<body style='background:#000;color:#888;font:3vw sans-serif;padding:8vw'>"
                        + "Cannot reach the photo frame server. Retrying...</body>",
                        "text/html", "utf-8", null);
                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(() -> web.loadUrl(Config.URL), 10000);
                }
            }
        });
        setContentView(web);
        web.requestFocus();
        web.loadUrl(Config.URL);
    }

    private void immersive() {
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) immersive();
    }

    /** Back: the page closes its settings or exits; if the page isn't loaded, exit here. */
    @Override
    public void onBackPressed() {
        web.evaluateJavascript("(typeof onBack==='function')?(onBack(),1):0", v -> {
            if (!"1".equals(v)) nativeBack();
        });
    }

    private void nativeBack() {
        finish();   // page not loaded (server unreachable): Back simply exits
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        web.loadUrl(Config.URL);   // always start from the fixed URL when reopened
    }

    @Override
    protected void onStart() {
        super.onStart();
        web.onResume();
    }

    @Override
    protected void onStop() {
        web.onPause();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        web.destroy();
        super.onDestroy();
    }
}
