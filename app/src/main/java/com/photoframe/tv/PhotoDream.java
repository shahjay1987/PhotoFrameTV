package com.photoframe.tv;

import android.os.Handler;
import android.os.Looper;
import android.service.dreams.DreamService;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * Screensaver version. Interactive: remote keys go to the page (Left/Right = previous/next photo,
 * OK = settings). Press Back twice to leave; the Home button also leaves.
 */
public class PhotoDream extends DreamService {
    private WebView web;
    private final Handler ui = new Handler(Looper.getMainLooper());

    /** Lets the page call AndroidApp.exit() */
    public class Bridge {
        @JavascriptInterface
        public void exit() {
            ui.post(PhotoDream.this::finish);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(true);
        setFullscreen(true);
        setScreenBright(true);
        web = new WebView(this);
        Web.setup(web, new Bridge());
        setContentView(web);
        web.setFocusable(true);
        web.setFocusableInTouchMode(true);
        web.requestFocus();
        web.loadUrl(Config.URL);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (e.getAction() == KeyEvent.ACTION_UP && web != null) {
                web.evaluateJavascript("(typeof onBack==='function')?(onBack(),1):0", v -> {
                    if (!"1".equals(v)) finish();
                });
            }
            return true;
        }
        return super.dispatchKeyEvent(e);
    }

    @Override
    public void onDetachedFromWindow() {
        ui.removeCallbacksAndMessages(null);
        if (web != null) {
            web.destroy();
            web = null;
        }
        super.onDetachedFromWindow();
    }
}
