package com.ggmathur.android.slide;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppSelectActivity extends ListActivity {

    public static int APP_SELECTED = 10;
    private List<ResolveInfo> appList;
    private AppListAdapter appListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String callingAction = getIntent().getAction();
        final SlidePreferences.App savedAppPreference = SlidePreferences.getSavedApp(this);

        if (Intent.ACTION_ASSIST.equals(callingAction)) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        } else {

        }

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appList = this.getPackageManager().queryIntentActivities( mainIntent, 0);
        final ResolveInfoComparator resolveInfoComparator = new ResolveInfoComparator(getPackageManager());
        Collections.sort(appList, resolveInfoComparator);

        appListAdapter = new AppListAdapter(this, R.layout.app_list_item, appList, savedAppPreference);
        setListAdapter(appListAdapter);
        final ListView listView = getListView();
        listView.setTextFilterEnabled(true);
    }

    @Override
    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final ResolveInfo resolveInfo = appList.get(position);
        v.setSelected(true);
        SlidePreferences.saveSharedApp(this, resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        Toast.makeText(this, "Updated app preference.", Toast.LENGTH_LONG);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AppListAdapter extends ArrayAdapter<ResolveInfo> {
        private final PackageManager packageManager;
        private SlidePreferences.App selectedApp;

        public AppListAdapter(final Context context, final int resource, final List<ResolveInfo> objects, final SlidePreferences.App selectedApp) {
            super(context, resource, objects);
            this.packageManager = context.getPackageManager();
            this.selectedApp = selectedApp;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent)
        {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item, parent, false);
            }

            final ResolveInfo resolveInfo = super.getItem(position);

            final String appIdentifier = resolveInfo.toString();

            if (appIdentifier.equals(selectedApp)) {
                //Mark app as selected
            }

            final String text = resolveInfo.loadLabel(packageManager).toString();
            ((TextView) convertView.findViewById(R.id.appName)).setText(text);
            final Drawable drawable = resolveInfo.activityInfo.applicationInfo.loadIcon(packageManager);
            ((ImageView)convertView.findViewById(R.id.appIcon)).setImageDrawable(drawable);

            return convertView;
        }
    }

    private class ResolveInfoComparator implements Comparator<ResolveInfo> {
        final PackageManager packageManager;

        public ResolveInfoComparator(final PackageManager packageManager) {
            this.packageManager = packageManager;
        }

        @Override
        public int compare(final ResolveInfo resolveInfo, final ResolveInfo resolveInfo2) {
            final String appLabel = resolveInfo.loadLabel(packageManager).toString();
            final String appLabel2 = resolveInfo2.loadLabel(packageManager).toString();
            return appLabel.compareTo(appLabel2);
        }
    }
}
