package com.zy.ticketseller.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.fragment.HomeFragment;
import com.zy.ticketseller.ui.fragment.UserCenterFragment;
import com.zy.ticketseller.util.Constant;
import com.zy.ticketseller.util.SpfUtil;


/**
 * Created by Songzhihang on 2017/10/6.
 * 首页tabActivity
 */
public class MainTabActivity extends BaseActivity {
    private static final String TAG = "MainTabActivity";
    private FrameLayout contentFL;
    private LinearLayout tabLL;
    private LinearLayout aml_ll_left;
    private LinearLayout aml_ll_right;
    private ImageView aml_iv_left;
    private ImageView aml_iv_right;
    private HomeFragment homeFragment;
    private UserCenterFragment userCenterFragment;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_maintab_layout);
    }

    @Override
    protected void findViews() {
        contentFL = (FrameLayout) findViewById(R.id.aml_fl_content);
        tabLL = (LinearLayout) findViewById(R.id.aml_ll_tab_layout);
        aml_ll_left = (LinearLayout) findViewById(R.id.aml_ll_left);
        aml_ll_right = (LinearLayout) findViewById(R.id.aml_ll_right);
        aml_iv_left = (ImageView) findViewById(R.id.aml_iv_left);
        aml_iv_right = (ImageView) findViewById(R.id.aml_iv_right);
    }

    @Override
    protected void initData() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        replaceFragment(homeFragment);
    }

    @Override
    protected void setListener() {
        aml_ll_right.setOnClickListener(this);
        aml_ll_left.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SpfUtil.getBoolean(Constant.IS_LOGIN, false)) {//已登陆获取用户信息
//            getUserData();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.aml_ll_left) {
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
            }
            replaceIcon(0);
            replaceFragment(homeFragment);
        }
        if (v.getId() == R.id.aml_ll_right) {
            if (userCenterFragment == null) {
                userCenterFragment = new UserCenterFragment();
            }
            replaceIcon(1);
            replaceFragment(userCenterFragment);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.aml_fl_content, fragment);
        transaction.commit();
    }


    private void replaceIcon(int positon) {
        if (positon == 0) {
            aml_iv_left.setImageResource(R.drawable.icon_home_press);
            aml_iv_right.setImageResource(R.drawable.icon_mine_normal);
        } else {
            aml_iv_left.setImageResource(R.drawable.icon_home_normal);
            aml_iv_right.setImageResource(R.drawable.icon_mine_press);
        }
    }

//    private void getUserData() {
//        Map<String, String> params = new HashMap<>();
//        params.put("token", SpfUtil.getString(Constant.TOKEN, ""));
//        params.put("userPhone", SpfUtil.getString(Constant.LOGIN_USERPHONE, ""));
//        OkHttpHelp<ResultItem> httpHelp = OkHttpHelp.getInstance();
//        httpHelp.httpRequest("", Constant.GET_USER_URL, params, new ResponseListener<ResultItem>() {
//                    @Override
//                    public void onSuccess(ResultItem object) {
//                        if ("fail".equals(object.getResult())) {
//                            if ("token error".equals(object.getData())) {
//                                toast("token失效,请重新登录");
//                                tokenError();
//                            }
//                        } else {
//                            JSONObject userJson = null;
//                            try {
//                                userJson = new JSONObject(object.getData());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            UserItem userItem = (new Gson()).fromJson(userJson.toString(), UserItem.class);
//                            UserItem mUser = userItem;
//                            ProgressDialogUtil.dismissProgressdialog();
//                            BaseApplication.getAPPInstance().setmUser(userItem);
//                        }
//                    }
//
//                    @Override
//                    public void onFailed(String message) {
//                        ProgressDialogUtil.dismissProgressdialog();
//                    }
//
//                    @Override
//                    public Class getEntityClass() {
//                        return ResultItem.class;
//                    }
//                }
//
//        );
//    }
}
