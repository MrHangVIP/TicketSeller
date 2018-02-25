package com.zy.ticketseller.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zy.ticketseller.BaseFragment;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.adapter.MyCommonNavigatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Songzhihang on 2017/10/6.
 * 首页fragment
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    private ViewPager fhl_viewpager;
    private MagicIndicator magicIndicator;
    private int curIndex = 0;
    private List<String> titleDataList=new ArrayList<>();
    private ArrayList<Fragment> fragments=new ArrayList<>();
    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_layout, container, false);
    }

    @Override
    protected void initView(View view) {
        magicIndicator = (MagicIndicator)contentView.findViewById(R.id.fhl_magic_indicator);

        fhl_viewpager = (ViewPager) contentView.findViewById(R.id.fhl_viewpager);
    }

    @Override
    protected void initData() {
        titleDataList.add("全部");
        titleDataList.add("音乐会");
        titleDataList.add("歌剧话剧");
        titleDataList.add("舞蹈芭蕾");
        titleDataList.add("演唱会");
        titleDataList.add("体育比赛");
        titleDataList.add("儿童亲子");
        fragments.clear();
        for (int i=0;i<titleDataList.size();i++){
            Bundle bundle = new Bundle();
            TicketListFragment ticketListFragment = new TicketListFragment();
            bundle.putString("type", titleDataList.get(i));
            ticketListFragment.setArguments(bundle);
            fragments.add(ticketListFragment);
        }
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new MyCommonNavigatorAdapter(titleDataList,fhl_viewpager,curIndex));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, fhl_viewpager);
        fhl_viewpager.setAdapter(new MyFragmentAdapter(getChildFragmentManager()));
        fhl_viewpager.setCurrentItem(curIndex);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View view) {

    }

    class MyFragmentAdapter extends FragmentPagerAdapter {


        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * 返回需要展示的fragment
         *
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        /**
         * 返回需要展示的fangment数量
         *
         * @return
         */
        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
