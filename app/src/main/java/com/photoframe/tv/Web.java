package com.photoframe.tv;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

final class Web {
    private Web() {}

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
