package com.zy.ticketseller.ui.activity;

import android.view.View;

import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;
import com.zy.ticketseller.util.Constant;
import com.zy.ticketseller.util.SpfUtil;

import java.util.Timer;
import java.util.TimerTask;


public class WelComeActivity extends BaseActivity {
    private static final String TAG = "WelComeActivity";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_welcome_layout);
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void initData() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SpfUtil.saveBoolean(Constant.IS_FIRST, true);
                if (SpfUtil.getBoolean(Constant.IS_LOGIN, false)) {
                    goToNext(MainTabActivity.class);
                } else {
                    goToNext(MainTabActivity.class);
                }
            }
        },2000);

    }

    @Override
    protected void setListener() {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SpfUtil.getBoolean(Constant.IS_FIRST, true)) {
            goToNext(MainTabActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
