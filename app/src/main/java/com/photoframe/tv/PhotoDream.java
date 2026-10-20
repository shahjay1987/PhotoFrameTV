package com.photoframe.tv;

import android.service.dreams.DreamService;
import android.webkit.WebView;

/** Screensaver version: shows the same page; any remote key wakes the TV. */
public class PhotoDream extends DreamService {
    private WebView web;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setScreenBright(true);
        web = new WebView(this);
        Web.setup(web, null);
        setContentView(web);
        web.loadUrl(Config.URL);
    }

    @Override
    public void onDetachedFromWindow() {
        if (web != null) {
            web.destroy();
            web = null;
        }
        super.onDetachedFromWindow();
    }
}
