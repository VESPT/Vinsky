package com.myproject.vinsky.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.myproject.vinsky.app.UI.RssListAdapter;
import com.myproject.vinsky.app.UI.RssParserTask;

import java.util.ArrayList;

/**
 * Created by vesp on 16/03/13.
 */
public class MainFragmentClass extends Fragment {
    // RSS用メンバ変数
    public static final String RSS_FEED_URL = "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
    private ArrayList mItems;
    private RssListAdapter mAdapter;
    protected ListView rssLV;

    public ListView getRssLV(){
        return rssLV;
    }

    // コンストラクタ
    public MainFragmentClass() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragmentClass newInstance(int sectionNumber) {
        MainFragmentClass fragment = new MainFragmentClass();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    //FragmentのUIが描画されるタイミングでよびだされる
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //fragment_main.xmlを使っている
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        /** パースしてリストビューに登録 **/
        // Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
        mItems = new ArrayList();
        mAdapter = new RssListAdapter(getActivity().getApplicationContext(), mItems);

        // アダプタをリストビューにセットする
        rssLV = (ListView)rootView.findViewById(R.id.contents_listview);
        rssLV.setAdapter(mAdapter);

        // NavigationViewで押したポジションの情報をfragmentから取得する
        Bundle mFrgBundle = getArguments();
        int sectionNum = mFrgBundle.getInt(ARG_SECTION_NUMBER);

        /*
        // サンプル用に空のItemオブジェクトをセットする
        for (int i = 0; i < 10; i++) {
            mAdapter.add(new Item());
        }
        */

        // タスクを起動する
        RssParserTask task = new RssParserTask((RssReaderActivity)getActivity(), this, mAdapter);
        switch(sectionNum){
            case 1:
                task.execute("http://blog.livedoor.jp/dqnplus/index.rdf");
                break;
            default:
                task.execute(RSS_FEED_URL);
                break;
        }

        return rootView;
    }

    //ActivityにAttachされると呼び出されれる
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((RssReaderActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
