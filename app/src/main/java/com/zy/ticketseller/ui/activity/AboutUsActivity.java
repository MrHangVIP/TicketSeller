package com.zy.ticketseller.ui.activity;


import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;

/**
 * 关于我们
 * Created by Songzhihang on 2017/12/10.
 */
public class AboutUsActivity extends BaseActivity {
    private static final String TAG = "AboutUsActivity";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_about_us_layout);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initData() {
        setTitle("关于我们");
    }

    @Override
    protected void setListener() {

    }
}
