package com.ggmathur.android.slide;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

public class LaunchAppShortcutActivity extends Activity {

    public int APP_SELECT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SlidePreferences.App appToLoad = SlidePreferences.getSavedApp(this);
        if (appToLoad == null) {
            final Intent intent = new Intent(this, AppSelectActivity.class);
            startActivityForResult(intent, APP_SELECT_REQUEST_CODE);
        } else {
            startApp(appToLoad);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_SELECT_REQUEST_CODE) {
            startApp(SlidePreferences.getSavedApp(this));
        } else {
            finish();
        }
    }

    private void startApp(final SlidePreferences.App appToLoad) {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        final ComponentName componentName = new ComponentName(appToLoad.getPackageName(), appToLoad.getActivityName());
        intent.setComponent(componentName);
        startActivity(intent);
    }
}
