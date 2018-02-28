package com.zy.ticketseller.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zy.ticketseller.BaseActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @discription 注册
 * @autor songzhihang
 * @time 2018/2/28  下午5:07
 **/
public class RegistActivity extends BaseActivity{
    @Override
    protected void setView() {

    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    private static final String TAG = "RegisterStyle4";
    private final int RIGHT_MENU = 11;
    private int opration_type = 1;//发送验证码和注册2个页面
    private MobileLoginUtil loginUtil;
    private LinearLayout contentView;
    //注册输入手机号页面
    private EditText login4_rscl_et_userphone;
    private ImageView login4_rscl_iv_pass_delete;
    private Button login4_rscl_bt_send;
    //注册输入验证码和密码页面
    private TextView login4_rnl_tv_notice;
    private EditText login4_rnl_et_code;
    private TextView login4__rnl_tv_send_code;
    private EditText login4_rnl_et_password1;
    private ImageView login4_rnl_iv_show;
    private EditText login4_rnl_et_password2;
    private Button login4_rnl_bt_register;

    private String mobileStr;
    private ProgressDialog mDialog;
    private Timer timer;
    private int TIME;
    private int buttonColor;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mContext = this;
        LoginConstant.login_activities.add(this);
        loginUtil = MobileLoginUtil.getInstance(mActivity, fdb, MobileLoginUtil.LOGIN_STYLE_1);
        if (bundle != null) {
            opration_type = bundle.getInt(MobileLoginUtil.OPRATION_TYPE, 1);
            mobileStr = bundle.getString("phone_number", "");
        }
        buttonColor =  ConfigureUtils.getMultiColor(module_data,
                ModuleData.ButtonBgColor, ConfigureUtils.getMultiValue(
                        ConfigureUtils.config_map,
                        TemplateConstants.colorScheme, "#DC3C38"));
        initView();
        setContentView(contentView);
        setListener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        bundle = intent.getBundleExtra(Constants.EXTRA);
        if (bundle != null) {
            opration_type = bundle.getInt(MobileLoginUtil.OPRATION_TYPE, 1);
        }
        initView();
        setContentView(contentView);
        setListener();
    }

    private void initView() {
        if (opration_type == 1) {//发送验证码
            contentView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.login4_register_send_code_layout, null);
            login4_rscl_et_userphone = (EditText) contentView.findViewById(R.id.login4_rscl_et_userphone);
            login4_rscl_iv_pass_delete = (ImageView) contentView.findViewById(R.id.login4_rscl_iv_pass_delete);
            login4_rscl_bt_send = (Button) contentView.findViewById(R.id.login4_rscl_bt_send);
        } else {
            contentView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.login4_register_next_layout, null);
            login4_rnl_tv_notice = (TextView) contentView.findViewById(R.id.login4_rnl_tv_notice);
            login4_rnl_et_code = (EditText) contentView.findViewById(R.id.login4_rnl_et_code);
            login4__rnl_tv_send_code = (TextView) contentView.findViewById(R.id.login4__rnl_tv_send_code);
            login4_rnl_et_password1 = (EditText) contentView.findViewById(R.id.login4_rnl_et_password1);
            login4_rnl_iv_show = (ImageView) contentView.findViewById(R.id.login4_rnl_iv_show);
            login4_rnl_et_password2 = (EditText) contentView.findViewById(R.id.login4_rnl_et_password2);
            login4_rnl_bt_register = (Button) contentView.findViewById(R.id.login4_rnl_bt_register);
            countDown();
            login4_rnl_tv_notice.setText(String.format(getString(R.string.login4_regist_next_notice), mobileStr));
        }
    }

    private void setListener() {
        if (opration_type == 1) {
            login4_rscl_et_userphone.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    boolean isEmpty = !TextUtils.isEmpty(login4_rscl_et_userphone.getText().toString());
                    if (isEmpty) {
                        login4_rscl_iv_pass_delete.setVisibility(View.VISIBLE);
                    } else {
                        login4_rscl_iv_pass_delete.setVisibility(View.GONE);
                    }
                    login4_rscl_bt_send.setEnabled(isEmpty);
                }
            });

            login4_rscl_et_userphone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus && !TextUtils.isEmpty(login4_rscl_et_userphone.getText())) {
                        login4_rscl_iv_pass_delete.setVisibility(View.VISIBLE);
                    } else {
                        login4_rscl_iv_pass_delete.setVisibility(View.GONE);
                    }
                }
            });
            login4_rscl_iv_pass_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login4_rscl_et_userphone.setText("");
                }
            });

            login4_rscl_bt_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (login4_rscl_bt_send.isEnabled()) {//检测手机号是否合理,手机号是否绑定,跳转到注册
                        final String mobile = login4_rscl_et_userphone.getText().toString();
                        if (!loginUtil.judgeMobile(mobile))
                            return;
                        loginUtil.checkBind(mobile, null, new MobileLoginUtil.IMobileLogin() {

                            @Override
                            public void callBack(Object obj) {
                                if (TextUtils.equals("0", obj.toString())) {
                                    // 未绑定，发送验证码
                                    loginUtil.sendMobileCode(mobile, MobileLoginUtil.REGISTER_BY_MOBILE,
                                            null, new MobileLoginUtil.IMobileLogin() {

                                                @Override
                                                public void callBack(Object obj) {//发送失败
                                                    showToast(R.string.user_validate_code_send_fail, CustomToast.DEFAULT);
                                                }
                                            }, null);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(MobileLoginUtil.OPRATION_TYPE, 0);
                                    bundle.putString("phone_number", mobile);
                                    Go2Util.startDetailActivity(mContext, sign, "RegisterStyle4", null, bundle);
                                } else {
                                    // 注册：已绑定，返回登录
                                    showToast(R.string.user_mobile_registed_and_login, CustomToast.DEFAULT);
                                    goBack();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            loginUtil.listenEditView(login4_rnl_bt_register, login4_rnl_et_code, login4_rnl_et_password1, login4_rnl_et_password2);
            login4__rnl_tv_send_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!login4__rnl_tv_send_code.isEnabled()) {
                        return;
                    }
                    // 未绑定，发送验证码
                    loginUtil.sendMobileCode(mobileStr, MobileLoginUtil.REGISTER_BY_MOBILE,
                            null, new MobileLoginUtil.IMobileLogin() {

                                @Override
                                public void callBack(Object obj) {//发送失败
                                    showToast(R.string.user_validate_code_send_fail, CustomToast.DEFAULT);
                                    timer.cancel();
                                    login4__rnl_tv_send_code.setEnabled(true);
                                    login4__rnl_tv_send_code.setText(Util.getString(R.string.login4_regist_code_send));
                                }
                            }, null);
                    countDown();
                }
            });
            login4_rnl_iv_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean ishow = login4_rnl_iv_show.getTag() != null ? (boolean) login4_rnl_iv_show.getTag() : true;
                    if (ishow) {
                        login4_rnl_et_password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        login4_rnl_et_password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        login4_rnl_iv_show.setImageResource(R.drawable.login4_icon_hide_pass);
                    } else {
                        login4_rnl_et_password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        login4_rnl_et_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        login4_rnl_iv_show.setImageResource(R.drawable.login4_icon_show_pass);
                    }
                    login4_rnl_iv_show.setTag(!ishow);
                }
            });

            login4_rnl_bt_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!login4_rnl_bt_register.isEnabled())
                        return;
                    if (!TextUtils.equals(login4_rnl_et_password1.getText(), login4_rnl_et_password2.getText())) {
                        showToast(R.string.pwd_again, CustomToast.WARM);
                        login4_rnl_et_password1.setText("");
                        login4_rnl_et_password2.setText("");
                        return;
                    }
                    onRegistAction(mobileStr, login4_rnl_et_password1.getText().toString(), "", login4_rnl_et_code.getText().toString());
                }
            });
        }
    }

    /**
     * 倒计时
     */
    public void countDown() {
        try {
            TIME = 60;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    TIME -= 1;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TIME <= 0) {
                                timer.cancel();
                                login4__rnl_tv_send_code.setEnabled(true);
                                login4__rnl_tv_send_code.setText(Util.getString(R.string.login4_regist_code_send));
                            } else {
                                login4__rnl_tv_send_code.setEnabled(false);
                                login4__rnl_tv_send_code.setText(TIME + "s");
                            }
                        }
                    });
                }
            }, 0, 1000);
        } catch (Exception e) {
            e.printStackTrace();
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }

    /**
     * 手机，邮箱注册
     *
     * @param name
     * @param pwd
     * @param type
     * @param code
     */
    private void onRegistAction(final String name, String pwd, String type,
                                String code) {
        if (mDialog == null) {
            mDialog = MMProgress.showProgressDlg(mContext, null, R.string.user_registing, false, true, null);
        } else {
            mDialog.show();
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("member_name", name);
        map.put("password", pwd);
        map.put("type", type);
        map.put("mobile_verifycode", code);
        String url = ConfigureUtils.getUrl(ConfigureUtils.api_map, UserInfoConstants.m_register, map);
        mDataRequestUtil.request(url, new DataRequestUtil.SuccessResponseListener() {
            @Override
            public void successResponse(String t) {
                try {
                    dismissDialog();
                    if (ValidateHelper.isHogeValidData(mActivity, t)) {
                        mSharedPreferenceService.put(LoginConstant.LAST_LOGIN_NAME, name);
                        UserBean bean = JsonUtil.getSettingList(t).get(0);
                        Util.saveUserInfo(bean);
                        Variable.IS_EXIST_PASSWORD = "1";
                        if (LoginUtil.getInstance(mContext).needCallback()) {
                            LoginUtil.getInstance(mContext).post(
                                    new LoginEvent(getResources().getString(R.string.user_login_success),
                                            LoginEvent.LOGIN_STATE.LOGIN_SUCCESS));
                            LoginConstant.clearAll();
                            overridePendingTransition(R.anim.slide_in_left,
                                    R.anim.slide_out_right);
                        } else {
                            LoginConstant.clearAll();
                            overridePendingTransition(R.anim.slide_in_left,
                                    R.anim.slide_out_right);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new DataRequestUtil.ErrorResponseListener() {

            @Override
            public void errorResponse(String error) {
                dismissDialog();
                if (!Util.isConnected()) {
                    showToast(R.string.no_connection, CustomToast.WARM);
                } else {
                    showToast(R.string.error_connection, CustomToast.WARM);
                }
            }
        });
    }

    private void dismissDialog() {
        if (mDialog != null) {
            mDialog.cancel();
            mDialog.dismiss();
        }
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        actionBar.setTitle(getResources().getString(R.string.login4_register));
        Util.setVisibility(actionBar.getBackView(),View.GONE);
        TextView menuView = Util.getNewTextView(mContext);
        menuView.setText(getResources().getString(R.string.user_cancel));
        menuView.setGravity(Gravity.CENTER_VERTICAL);
        menuView.setPadding(0, 0, Util.toDip(10), 0);
        menuView.setTextColor(ConfigureUtils.getMultiColor(module_data, ModuleData.ButtonBgColor, "#DC3C38"));
        actionBar.addMenu(RIGHT_MENU, menuView, false);
        Util.setVisibility(actionBar.getBackView(),View.GONE);
        actionBar.setDividerVisible(false);
        actionBar.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onMenuClick(int mid, View v) {
        super.onMenuClick(mid, v);
        if (mid == RIGHT_MENU) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginUtil.destory();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void goBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
