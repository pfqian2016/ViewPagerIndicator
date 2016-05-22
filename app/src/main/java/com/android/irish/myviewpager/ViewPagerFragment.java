package com.android.irish.myviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Irish on 2016/5/13.
 */
public class ViewPagerFragment extends Fragment{
    private String mTitle;
    private static final String BUNDLE_TITLE="title";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        if(bundle!=null){
            mTitle=bundle.getString(BUNDLE_TITLE);
        }
        TextView tv=new TextView(getActivity());
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);

        return tv;
    }

    public static ViewPagerFragment newInstance(String title){
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLE_TITLE, title);

        ViewPagerFragment fragment=new ViewPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
