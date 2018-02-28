package com.zy.ticketseller.ui.activity;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.widget.WelcomeCircularProgress;
import com.zy.ticketseller.util.Constant;
import com.zy.ticketseller.util.SpfUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @discription 欢迎页
 * @autor songzhihang
 * @time 2018/2/28  上午10:54
 **/
public class WelComeActivity extends BaseActivity {
    private static final String TAG = "WelComeActivity";
    private WelcomeCircularProgress ad_jump_progress1;
    private RelativeLayout ad_jump_layout1;
    private Timer mTimer;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_welcome_layout);
    }

    @Override
    protected void findViews() {
        ad_jump_layout1 = (RelativeLayout) findViewById(R.id.ad_jump_layout1);
        ad_jump_progress1 = (WelcomeCircularProgress) findViewById(R.id.ad_jump_progress1);
        ad_jump_progress1.setProgressColor(mContext.getResources().getColor(R.color.theme_color));
        ad_jump_progress1.setBacgroundColor(0x90000000);
        ad_jump_progress1.setMaxProgress(5000);
    }

    @Override
    protected void initData() {
        setWindowTranslucent();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ad_jump_progress1.getProgress() < 5000) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ad_jump_progress1.setProgress((int) (ad_jump_progress1.getProgress() + 10));
                        }
                    });
                    return;
                }
                goMain();
            }
        }, 0, 10);
    }

    @Override
    protected void setListener() {
        ad_jump_layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMain();
            }
        });
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

    private void goMain() {
        //以上条件不符合   则暂停计时器
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        SpfUtil.saveBoolean(Constant.IS_FIRST, true);
        if (SpfUtil.getBoolean(Constant.IS_LOGIN, false)) {
            goToNext(MainTabActivity.class);
        } else {
            goToNext(MainTabActivity.class);
        }
    }

    /**
     * 设置状态栏透明
     */
    private void setWindowTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // 透明状态栏
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
