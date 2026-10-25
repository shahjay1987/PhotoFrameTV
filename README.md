# Photo Frame TV

Opens one fixed URL full-screen. Back closes settings, otherwise exits. The page also exits on any key other than Left/Right/OK/volume. Also works as a screensaver.

1. Edit `app/src/main/java/com/photoframe/tv/Config.java` and set `URL`.
2. Build the APK (no Android Studio needed): create a GitHub repository, upload everything in this folder
   (including the hidden `.github` folder), open the Actions tab, run "Build APK", then download the
   `photoframe-apk` artifact and unzip it to get `app-debug.apk`.
3. Install on the TV:  `adb connect <tv-ip>`  then  `adb install -r app-debug.apk`
   (or copy it to the TV and open it with a file manager app).
4. Optional screensaver (interactive: Left/Right/OK work; any other key leaves):
   `adb shell settings put secure screensaver_components com.photoframe.tv/.PhotoDream`
   Restore Google's default:
   `adb shell settings put secure screensaver_components com.google.android.apps.tv.dreamx/.service.Backdrop`

Long press Left/Right in the page changes the song (the app sends a media key to the music player). This needs this app version.

## Updating the app without uninstalling
The project now signs every build with the same key (`app/photoframe.keystore`) and gives every GitHub build a higher version number.
- The first time you move to this version you must still uninstall the old app once (`adb uninstall com.photoframe.tv`), because it was signed with a different key.
  Settings saved inside the old app (selected albums, tick boxes) are lost with it.
- After that, a new build installs over the old one with `adb install -r app-debug.apk`, and your settings are kept.
- Upload the whole folder to GitHub, including `app/photoframe.keystore`. Keep the repository private; the key is only for this home project.
