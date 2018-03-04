package com.zy.ticketseller.ui.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;
import com.zy.ticketseller.util.Constant;
import com.zy.ticketseller.util.MyUtil;
import com.zy.ticketseller.util.ProgressDialogUtil;
import com.zy.ticketseller.util.SpfUtil;

/**
 * @discription 登录
 * @autor songzhihang
 * @time 2018/2/28  下午3:57
 **/
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    private EditText login4_ll_et_userphone;
    private ImageView login4_ll_iv_delete;
    private EditText login4_ll_et_password;
    private ImageView login4_ll_iv_pass_delete;
    private Button login4_ll_bt_login;
    private TextView login4_ll_tv_forget_pass;
    private TextView login4_ll_tv_register;
    private LinearLayout login4_ll_ll_third_layout;
    private int buttonColor;
    private String last_login_name;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_login_layout);
    }

    @Override
    protected void findViews() {
        login4_ll_et_userphone = (EditText) findViewById(R.id.login4_ll_et_userphone);
        login4_ll_iv_delete = (ImageView) findViewById(R.id.login4_ll_iv_delete);
        login4_ll_et_password = (EditText) findViewById(R.id.login4_ll_et_password);
        login4_ll_iv_pass_delete = (ImageView) findViewById(R.id.login4_ll_iv_pass_delete);
        login4_ll_bt_login = (Button) findViewById(R.id.login4_ll_bt_login);
        //忘记密码和注册
        login4_ll_tv_forget_pass = (TextView) findViewById(R.id.login4_ll_tv_forget_pass);
        login4_ll_tv_register = (TextView) findViewById(R.id.login4_ll_tv_register);
        //第三方
        login4_ll_ll_third_layout = (LinearLayout) findViewById(R.id.login4_ll_ll_third_layout);
        login4_ll_bt_login.setEnabled(false);
    }

    @Override
    protected void initData() {
        setTitle("登录");
        toolbarTitle.setTextColor(Color.parseColor("#333333"));
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.inflateMenu(R.menu.login_cancle_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                return true;
            }
        });
        last_login_name = SpfUtil.getString(Constant.LOGIN_USERPHONE, "");
        buttonColor = Color.parseColor("#DC3C38");
    }

    protected boolean showBackDrawable(){
        return false;
    }

    @Override
    protected void setListener() {
        login4_ll_et_userphone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = !TextUtils.isEmpty(login4_ll_et_userphone.getText().toString());
                if (isEmpty) {
                    login4_ll_iv_delete.setVisibility(View.VISIBLE);
                } else {
                    login4_ll_iv_delete.setVisibility(View.GONE);
                }
                isEmpty = isEmpty && !TextUtils.isEmpty(login4_ll_et_password.getText().toString());
                login4_ll_bt_login.setEnabled(isEmpty);
            }
        });

        login4_ll_et_userphone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !TextUtils.isEmpty(login4_ll_et_userphone.getText())) {
                    login4_ll_iv_delete.setVisibility(View.VISIBLE);
                } else {
                    login4_ll_iv_delete.setVisibility(View.GONE);
                }
            }
        });

        login4_ll_et_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = !TextUtils.isEmpty(login4_ll_et_password.getText().toString());
                if (isEmpty) {
                    login4_ll_iv_pass_delete.setVisibility(View.VISIBLE);
                } else {
                    login4_ll_iv_pass_delete.setVisibility(View.GONE);
                }
                isEmpty = isEmpty && !TextUtils.isEmpty(login4_ll_et_userphone.getText().toString());
                login4_ll_bt_login.setEnabled(isEmpty);
            }
        });

        login4_ll_et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !TextUtils.isEmpty(login4_ll_et_password.getText())) {
                    login4_ll_iv_pass_delete.setVisibility(View.VISIBLE);
                } else {
                    login4_ll_iv_pass_delete.setVisibility(View.GONE);
                }
            }
        });

        login4_ll_iv_pass_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login4_ll_et_password.setText("");
            }
        });

        login4_ll_iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login4_ll_et_userphone.setText("");
            }
        });

        login4_ll_bt_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!login4_ll_bt_login.isEnabled()) {
                    return;
                }
                MyUtil.hideSoftInput(login4_ll_et_userphone);
                String name = login4_ll_et_userphone.getText().toString();
                String pwd = login4_ll_et_password.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    toast(R.string.user_name_empty);
                } else if (TextUtils.isEmpty(pwd)) {
                    toast(R.string.user_pwd_empty);
                } else {
                    loginAction(name, pwd);
                }
            }
        });

        login4_ll_tv_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPwdAction();
            }
        });

        login4_ll_tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAction();
            }
        });
    }

    protected void forgetPwdAction() {
        Bundle bundle=new Bundle();
        bundle.putInt(Constant.OPRATION_TYPE,1);
        jumpToNext(ForgetPassWordActivity.class,bundle);
    }

    void registerAction() {
        Bundle bundle=new Bundle();
        bundle.putInt(Constant.OPRATION_TYPE,1);
        jumpToNext(RegistActivity.class,bundle);
    }

    public void initLoginPlat(String t) {
//        if (TextUtils.isEmpty(t)) {
//            login4_ll_ll_third_layout.setVisibility(View.GONE);
//            return;
//        }
//        try {
//            ArrayList<UserBean> list1 = JsonUtil.getSettingList(t);
//            if (list1 != null) {
//                ArrayList<UserBean> list = new ArrayList<UserBean>();
//                for (int i = 0, l = list1.size(); i < l; i++) {
//                    String mark = list1.get(i).getMark();
//                    if ("sina".equals(mark) || "qq".equals(mark) || "weixin".equals(mark) || "shouji".equals(mark)) {
//                        list.add(list1.get(i));
//                    }
//                }
//                intiThirdPlat(list);
//            } else {
//                login4_ll_ll_third_layout.setVisibility(View.GONE);
//            }
//            if (t.contains("config")) {
//                ArrayList<UserConfigBean> configBeans = LoginJsonUtil.getSettingConfigList(t);
//                handlerPlatConfig(configBeans);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            login4_ll_ll_third_layout.setVisibility(View.GONE);
//        }
    }

    //第三方平台初始化
//    private void intiThirdPlat(final ArrayList<UserBean> list) {
//        if (list.size() == 0) {
//            login4_ll_ll_third_layout.setVisibility(View.GONE);
//            return;
//        }
//        login4_ll_ll_third_layout.setVisibility(View.VISIBLE);
//        login4_ll_ll_third_layout.removeAllViews();
//        for (int i = 0; i < list.size(); i++) {
//            final UserBean bean = list.get(i);
//            ImageView imageView = new ImageView(mContext);
//            if (TextUtils.equals("qq", list.get(i).getMark())) {
//                imageView.setImageResource(R.drawable.login4_icon_qq);
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loginOfQQ(bean);
//                    }
//                });
//            }
//            if (TextUtils.equals("weixin", list.get(i).getMark())) {
//                imageView.setImageResource(R.drawable.login4_icon_wx);
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loginOfWeixin(bean);
//                    }
//                });
//            }
//            if (TextUtils.equals("sina", list.get(i).getMark())) {
//                imageView.setImageResource(R.drawable.login4_icon_wb);
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loginOfSina(bean);
//                    }
//                });
//            }
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Util.toDip(50), Util.toDip(50));
//            params.leftMargin = (Variable.WIDTH - Util.toDip(106) - Util.toDip(50) * list.size()) / (list.size() + 1);
//            login4_ll_ll_third_layout.addView(imageView, params);
//        }
//    }

    public void loginAction(String name, String pwd) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            toast(R.string.user_name_empty);
            return;
        }
        //登录操作
        ProgressDialogUtil.showProgressDialog(mContext,false);
    }
}
