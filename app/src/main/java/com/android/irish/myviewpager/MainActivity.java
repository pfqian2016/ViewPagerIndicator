package com.android.irish.myviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

import com.android.irish.myviewpager.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;

    private List<String> mTitles=Arrays.asList("Homepage","Notification","More");
    private List<ViewPagerFragment> fragments=new ArrayList<ViewPagerFragment>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initDatas();
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager,0);
    }
    private void initView() {
        mViewPager= (ViewPager) findViewById(R.id.view_pager);
        mIndicator= (ViewPagerIndicator) findViewById(R.id.indicator);
    }
    private void initDatas() {
        for(String title:mTitles){
            ViewPagerFragment fragment=ViewPagerFragment.newInstance(title);
            fragments.add(fragment);
        }
        mIndicator.setTabItemTitles(mTitles);
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
    }



}
