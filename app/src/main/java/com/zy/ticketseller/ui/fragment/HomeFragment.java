package com.zy.ticketseller.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.zy.ticketseller.BaseFragment;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.adapter.MyCommonNavigatorAdapter;
import com.zy.ticketseller.util.MyUtil;

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
    private MagicIndicator magicIndicator;
    private TextView fhl_tv_city;
    private TextView fhl_tv_time;
    private ViewPager fhl_viewpager;

    private int curIndex = 0;
    private List<String> titleDataList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FrameLayout fhl_ll_time_filter;
    private View fhl_bg_filter;
    private RelativeLayout fhl_rl_all;
    private RelativeLayout fhl_rl_today;
    private RelativeLayout fhl_rl_week;
    private RelativeLayout fhl_rl_month;
    private AnimatorSet filterViewHideAnimator;
    private AnimatorSet filterViewShowAnimator;

    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_layout, container, false);
    }

    @Override
    protected void initView(View view) {
        magicIndicator = (MagicIndicator) contentView.findViewById(R.id.fhl_magic_indicator);
        fhl_tv_city = (TextView) contentView.findViewById(R.id.fhl_tv_city);
        fhl_tv_time = (TextView) contentView.findViewById(R.id.fhl_tv_time);
        fhl_viewpager = (ViewPager) contentView.findViewById(R.id.fhl_viewpager);
        initTimeFilterView();
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
        for (int i = 0; i < titleDataList.size(); i++) {
            Bundle bundle = new Bundle();
            TicketListFragment ticketListFragment = new TicketListFragment();
            bundle.putString("type", titleDataList.get(i));
            ticketListFragment.setArguments(bundle);
            fragments.add(ticketListFragment);
        }
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new MyCommonNavigatorAdapter(titleDataList, fhl_viewpager, curIndex));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, fhl_viewpager);
        fhl_viewpager.setAdapter(new MyFragmentAdapter(getChildFragmentManager()));
        fhl_viewpager.setCurrentItem(curIndex);
    }

    @Override
    protected void initEvent() {
        fhl_tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean tag=!(Boolean) view.getTag();
                int res=R.drawable.icon_arrow_up;
                if(tag){
                    res=R.drawable.icon_arrow_up;
                    filterViewShowAnimator.start();
                }else{
                    res=R.drawable.icon_arrow_down;
                    filterViewHideAnimator.start();
                }
                MyUtil.setCompoundDrawables(fhl_tv_time,MyUtil.toDip(18),MyUtil.toDip(18),2,res);
                view.setTag(tag);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    private void initTimeFilterView(){
        fhl_ll_time_filter=(FrameLayout)contentView.findViewById(R.id.fhl_ll_time_filter);
        fhl_bg_filter=(View)contentView.findViewById(R.id.fhl_bg_filter);
        fhl_rl_all=(RelativeLayout)contentView.findViewById(R.id.fhl_rl_all);
        fhl_rl_today=(RelativeLayout)contentView.findViewById(R.id.fhl_rl_today);
        fhl_rl_week=(RelativeLayout)contentView.findViewById(R.id.fhl_rl_week);
        fhl_rl_month=(RelativeLayout)contentView.findViewById(R.id.fhl_rl_month);
        fhl_tv_time.setTag(false);
        fhl_bg_filter.post(new Runnable() {
            @Override
            public void run() {
                initAnimator();
            }
        });
    }

    //初始化动画
    private void initAnimator() {
        int durtime = 300;
        int height=fhl_bg_filter.getMeasuredHeight();
        ObjectAnimator translationYIn = ObjectAnimator.ofFloat(fhl_ll_time_filter, "translationY", -height, 0);
        ObjectAnimator alphaIn = ObjectAnimator.ofFloat(fhl_bg_filter, "alpha", 0, 0.5f);
        filterViewShowAnimator = new AnimatorSet();
        filterViewShowAnimator.play(translationYIn).with(alphaIn);
        filterViewShowAnimator.setDuration(durtime);
        filterViewShowAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                fhl_ll_time_filter.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ObjectAnimator translationYOut = ObjectAnimator.ofFloat(fhl_ll_time_filter, "translationY", 0, -height);
        ObjectAnimator alphaOut = ObjectAnimator.ofFloat(fhl_bg_filter, "alpha", 0.5f, 0);
        filterViewHideAnimator = new AnimatorSet();
        filterViewHideAnimator.play(translationYOut).with(alphaOut);
        filterViewHideAnimator.setDuration(durtime);
        filterViewHideAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fhl_ll_time_filter.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
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