# Photo Frame TV

Opens one fixed URL full-screen. Back = close settings / press twice to exit. Also works as a screensaver.

1. Edit `app/src/main/java/com/photoframe/tv/Config.java` and set `URL`.
2. Build the APK (no Android Studio needed): create a GitHub repository, upload everything in this folder
   (including the hidden `.github` folder), open the Actions tab, run "Build APK", then download the
   `photoframe-apk` artifact and unzip it to get `app-debug.apk`.
3. Install on the TV:  `adb connect <tv-ip>`  then  `adb install -r app-debug.apk`
   (or copy it to the TV and open it with a file manager app).
4. Optional screensaver:
   `adb shell settings put secure screensaver_components com.photoframe.tv/.PhotoDream`
   Restore Google's default:
   `adb shell settings put secure screensaver_components com.google.android.apps.tv.dreamx/.service.Backdrop`
