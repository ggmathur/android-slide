package com.ggmathur.android.slide;

import android.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends ListActivity {

    private static int APP_SELECT = 3;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = new Intent(this, AppSelectActivity.class);
        startActivityForResult(intent, APP_SELECT);
        /*
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, LIST_ITEMS);
        setListAdapter(listAdapter);
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_SELECT) {
            finish();
        }
    }

    @Override
    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final String listItemString = getListAdapter().getItem(position).toString();

        if (LIST_ITEM_SELECT_APP.equals(listItemString)) {
            final Intent appSelectIntent = new Intent(this, AppSelectActivity.class);
            startActivity(appSelectIntent);
        } else if (LIST_ITEM_SELECT_THEME.equals(listItemString)) {

        } else if (LIST_ITEM_ABOUT.equals(listItemString)) {

        } else {
            Log.e(TAG, "Invalid position: " + position + " " + listItemString);
        }
    }

    private static final String TAG = "SettingsActivity";

    private static final String LIST_ITEM_SELECT_APP = "Select Launch App";
    private static final String LIST_ITEM_SELECT_THEME = "Select Theme";
    private static final String LIST_ITEM_ABOUT = "About";

    private static final String[] LIST_ITEMS = new String[] {
            LIST_ITEM_SELECT_APP, LIST_ITEM_SELECT_THEME, LIST_ITEM_ABOUT
    };
}
