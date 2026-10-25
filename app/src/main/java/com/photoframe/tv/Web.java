package com.photoframe.tv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

final class Web {
    private Web() {}

    /** Sends a media key (next / previous track) to whichever music player is active, e.g. Symfonium. */
    static void mediaKey(Context c, String which) {
        int code = "next".equals(which) ? KeyEvent.KEYCODE_MEDIA_NEXT : KeyEvent.KEYCODE_MEDIA_PREVIOUS;
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        long t = SystemClock.uptimeMillis();
        am.dispatchMediaKeyEvent(new KeyEvent(t, t, KeyEvent.ACTION_DOWN, code, 0));
        am.dispatchMediaKeyEvent(new KeyEvent(t, t, KeyEvent.ACTION_UP, code, 0));
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    static void setup(WebView w, Object bridge) {
        WebSettings s = w.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        w.setBackgroundColor(Color.BLACK);
        w.setOverScrollMode(View.OVER_SCROLL_NEVER);
        w.setVerticalScrollBarEnabled(false);
        w.setHorizontalScrollBarEnabled(false);
        if (bridge != null) w.addJavascriptInterface(bridge, "AndroidApp");
    }
}
