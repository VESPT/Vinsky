package com.myproject.vinsky.app;

/*
* エントリーポイント
* */

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myproject.vinsky.app.UI.Item;
import com.myproject.vinsky.app.UI.RssListAdapter;
import com.myproject.vinsky.app.UI.RssParserTask;
import com.myproject.vinsky.app.backend.VinDatabase;

import java.util.ArrayList;
import java.util.List;

public class RssReaderActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence lastTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // フラグメントの設定
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        lastTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        /*
        // TODO ダミーデータ周りは処理実装完了後に削除すること.
        // DB接続 & 最新ブログ記事取得処理
        // 1. オブジェクトの作成と同時に(なければ)DBファイルが作られる.
        VinDatabase helper = new VinDatabase(this);
        SQLiteDatabase vinDb = helper.getReadableDatabase();
        // 2. 一覧表示用のデータを取得.
        // TODO 一覧表示処理を追加する
        List<Item> articles = helper.getAllArticles(vinDb);
        */

    }

    // クリック処理(NavigationDrawerじゃない方)
    /*
    @Override
    protected void onListItemClick( ListView l, View v, int position, long id){
        Log.d("RssReaderActivity", "onListItemClick");
    }*/

    //フラグメントの選択
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        // Fragmentをここで更新
        fragmentManager.beginTransaction()
                .replace(R.id.container, MainFragmentClass.newInstance(position + 1))
                .commit();
        Log.d("RssReaderActivity", "Click?");
    }

    public void onSectionAttached(int number) {
        Log.d("onSectionAttached", "Click?");
        switch (number) {
            case 1:
                lastTitle = getString(R.string.title_section1);
                break;
            case 2:
                lastTitle = getString(R.string.title_section2);
                break;
            case 3:
                lastTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(lastTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("onOptionsItemSelected", "Click?");

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((RssReaderActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
