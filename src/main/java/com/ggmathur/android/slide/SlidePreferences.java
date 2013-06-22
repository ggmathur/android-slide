package com.ggmathur.android.slide;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by gaurav on 6/21/13.
 */
public class SlidePreferences {
    public static String SAVED_APP_PACKAGE_NAME = "saved_app_package_name";
    public static String SAVED_APP_ACTIVITY_NAME = "saved_app_activity_name";
    public static String SLIDE_PREFERENCES = "slide_app_preferences";

    public static App getSavedApp(final Context context) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(SLIDE_PREFERENCES, Context.MODE_PRIVATE);
        final String packageName = sharedPreferences.getString(SAVED_APP_PACKAGE_NAME, null);
        final String activityName = sharedPreferences.getString(SAVED_APP_ACTIVITY_NAME, null);

        if (TextUtils.isEmpty(packageName) && TextUtils.isEmpty(activityName)) {
            return null;
        } else {
            return new App(packageName, activityName);
        }
    }

    public static void saveSharedApp(final Context context, final String packageName, final String activityName) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(SLIDE_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(SAVED_APP_PACKAGE_NAME, packageName)
                .putString(SAVED_APP_ACTIVITY_NAME, activityName)
                .commit();
    }

    public static void saveSharedApp(final Context context, final App appToSave) {
        saveSharedApp(context, appToSave.packageName, appToSave.activityName);
    }

    public static void clearPreference(final Context context) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(SLIDE_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .clear()
                .commit();
    }

    public static class App {
        private final String packageName;
        private final String activityName;

        public App(final String packageName, final String activityName) {
            this.packageName = packageName;
            this.activityName = activityName;
        }

        public String getActivityName() {
            return activityName;
        }

        public String getPackageName() {
            return packageName;
        }
    }
}
