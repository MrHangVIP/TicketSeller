package com.zy.ticketseller.ui.activity;

import android.graphics.Color;
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

import com.google.gson.Gson;
import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.BaseApplication;
import com.zy.ticketseller.R;
import com.zy.ticketseller.api.OkHttpHelp;
import com.zy.ticketseller.bean.BusinessItem;
import com.zy.ticketseller.bean.ResultItem;
import com.zy.ticketseller.bean.UserItem;
import com.zy.ticketseller.listener.ResponseListener;
import com.zy.ticketseller.util.Constant;
import com.zy.ticketseller.util.MyUtil;
import com.zy.ticketseller.util.ProgressDialogUtil;
import com.zy.ticketseller.util.SpfUtil;
import com.zy.ticketseller.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private int type;

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
        type = SpfUtil.getInt(Constant.LOGIN_TYPE, 0);
        setTitle("登录");
        toolbarTitle.setTextColor(Color.parseColor("#333333"));
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.inflateMenu(R.menu.login_cancle_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (type == Constant.TYPE_BISSINESS) {
                    goToNext(TypeSelectActivity.class);
                } else {
                    finish();
                }
                return true;
            }
        });
        last_login_name = SpfUtil.getString(Constant.LOGIN_USERPHONE, "");
        buttonColor = Color.parseColor("#DC3C38");
        if (type == Constant.TYPE_BISSINESS) {
            login4_ll_et_userphone.setHint("会员号");
            login4_ll_tv_forget_pass.setVisibility(View.GONE);
            login4_ll_tv_register.setVisibility(View.GONE);
        }
    }

    protected boolean showBackDrawable() {
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
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.OPRATION_TYPE, 1);
        jumpToNext(ForgetPassWordActivity.class, bundle);
    }

    void registerAction() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.OPRATION_TYPE, 1);
        jumpToNext(RegistActivity.class, bundle);
    }

    public void loginAction(String name, String pwd) {
        if (type == Constant.TYPE_BISSINESS) {
            businessLogin(name, pwd);
        } else {
            if (!StringUtils.isMobile(name)) {
                toast("请输入正确的手机号");
                loginFail();
            }
            if (pwd.length() < 8) {
                toast("密码长度不能低于8位");
                loginFail();
                return;
            }
            userLogin(name, pwd);
        }
    }

    private void businessLogin(String username, String userpass) {
        Map<String, String> params = new HashMap<>();
        params.put("userPhone", username);
        params.put("userPass", userpass);
        params.put("MAC", Constant.MacAddress);
        OkHttpHelp<ResultItem> httpHelp = OkHttpHelp.getInstance();
        //登录操作
        ProgressDialogUtil.showProgressDialog(mContext, false);
        httpHelp.httpRequest("post", Constant.LOGIN_URL, params, new ResponseListener<ResultItem>() {
            @Override
            public void onSuccess(ResultItem object) {
                ProgressDialogUtil.dismissProgressdialog();
                if (!object.getResult().equals("fail")) {
                    toast("登录成功！");
                    SpfUtil.saveString(Constant.TOKEN, object.getResult());
                    JSONObject userJson = null;
                    try {
                        userJson = new JSONObject(object.getData());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BusinessItem userItem = (new Gson()).fromJson(userJson.toString(), BusinessItem.class);
                    BaseApplication.getAPPInstance().setBusinessUser(userItem);
                    SpfUtil.saveBoolean(Constant.IS_LOGIN, true);
                    SpfUtil.saveString(Constant.LOGIN_USERPHONE, userItem.getUserPhone());
                    goToNext(MainTabActivity.class);
                } else {
                    toast("用户名或密码错误");
                    loginFail();
                }
            }

            @Override
            public void onFailed(String message) {
                ProgressDialogUtil.dismissProgressdialog();
            }

            @Override
            public Class<ResultItem> getEntityClass() {
                return ResultItem.class;
            }
        });
    }

    private void userLogin(String userPhone, String userPass) {
        Map<String, String> params = new HashMap<>();
        params.put("userPhone", userPhone);
        params.put("userPass", userPass);
        params.put("MAC", Constant.MacAddress);
        OkHttpHelp<ResultItem> httpHelp = OkHttpHelp.getInstance();
        ProgressDialogUtil.showProgressDialog(mContext, false);
        httpHelp.httpRequest("post", Constant.LOGIN_URL, params, new ResponseListener<ResultItem>() {
            @Override
            public void onSuccess(ResultItem object) {
                ProgressDialogUtil.dismissProgressdialog();
                if (!object.getResult().equals("fail")) {
                    toast("登录成功！");
                    SpfUtil.saveString(Constant.TOKEN, object.getResult());
                    JSONObject userJson = null;
                    try {
                        userJson = new JSONObject(object.getData());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    UserItem userItem = (new Gson()).fromJson(userJson.toString(), UserItem.class);
                    BaseApplication.getAPPInstance().setmUser(userItem);
                    SpfUtil.saveBoolean(Constant.IS_LOGIN, true);
                    SpfUtil.saveString(Constant.LOGIN_USERPHONE, userItem.getUserPhone());
                    clearLogins();
                } else {
                    toast("用户名或密码错误");
                    loginFail();
                }
            }

            @Override
            public void onFailed(String message) {
                ProgressDialogUtil.dismissProgressdialog();
            }

            @Override
            public Class<ResultItem> getEntityClass() {
                return ResultItem.class;
            }
        });
    }

    private void loginFail() {
        login4_ll_et_userphone.setText("");
        login4_ll_et_userphone.requestFocus();
        login4_ll_et_password.setText("");
    }
}
