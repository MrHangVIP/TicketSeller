package com.zy.ticketseller.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zy.ticketseller.ui.widget.MyColorTransitionPagerTitleView;
import com.zy.ticketseller.ui.widget.MyLinePagerIndicator;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.List;

/**
 * Created by Songzhihang on 2018/2/25.
 * 栏目栏指示器的适配器
 */
public class MyCommonNavigatorAdapter extends CommonNavigatorAdapter {

    private List<String> mTitleDataList;

    private int index;

    private ViewPager mViewPager;

    public MyCommonNavigatorAdapter(List<String> mTitleDataList,ViewPager mViewPager,int index) {
        super();
        this.mTitleDataList=mTitleDataList;
        this.index=index;
        this.mViewPager=mViewPager;
    }

    @Override
    public int getCount() {
        return mTitleDataList == null ? 0 : mTitleDataList.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context,final int i) {
        MyColorTransitionPagerTitleView colorTransitionPagerTitleView = new MyColorTransitionPagerTitleView(context);
        colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
        colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
        colorTransitionPagerTitleView.setText(mTitleDataList.get(i));
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(i);
            }
        });
        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new MyLinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        indicator.setColors(Color.parseColor("#D30035")  );
        return indicator;
    }
}
