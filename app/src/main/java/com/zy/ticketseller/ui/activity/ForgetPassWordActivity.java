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
import com.zy.ticketseller.R;
import com.zy.ticketseller.util.Constant;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @discription忘记密码和重置密码页面
 * @autor songzhihang
 * @time 2018/2/28  下午4:30
 **/
public class ForgetPassWordActivity extends BaseActivity {
    private static final String TAG = "ForgetPassWordActivity";

    private final int RIGHT_MENU = 11;
    private int opration_type = 1;//发送验证码和注册2个页面
    private LinearLayout contentView;
    //注册输入手机号页面
    private EditText login4_rscl_et_userphone;
    private ImageView login4_rscl_iv_pass_delete;
    private Button login4_rscl_bt_send;
    //注册输入验证码和密码页面
    private TextView login4_rnl_tv_text;
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
    protected void setView() {
        login_activities.add(this);
        if (bundle != null) {
            opration_type = bundle.getInt(Constant.OPRATION_TYPE, 1);
            mobileStr = bundle.getString("phone_number", "");
        }
        buttonColor = Color.parseColor("#DC3C38");
        initView();
        setContentView(contentView);
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


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        bundle = intent.getExtras();
        if (bundle != null) {
            opration_type = bundle.getInt(Constant.OPRATION_TYPE, 1);
        }
        initView();
        setContentView(contentView);
        setListener();
    }

    private void initView() {
        if (opration_type == 1) {//发送验证码
            contentView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.activity_register_layout, null);
            login4_rscl_et_userphone = (EditText) contentView.findViewById(R.id.login4_rscl_et_userphone);
            login4_rscl_iv_pass_delete = (ImageView) contentView.findViewById(R.id.login4_rscl_iv_pass_delete);
            login4_rscl_bt_send = (Button) contentView.findViewById(R.id.login4_rscl_bt_send);
        } else {
            contentView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.activity_register_next_layout, null);
            login4_rnl_tv_text = (TextView) contentView.findViewById(R.id.login4_rnl_tv_text);
            login4_rnl_tv_notice = (TextView) contentView.findViewById(R.id.login4_rnl_tv_notice);
            login4_rnl_et_code = (EditText) contentView.findViewById(R.id.login4_rnl_et_code);
            login4__rnl_tv_send_code = (TextView) contentView.findViewById(R.id.login4__rnl_tv_send_code);
            login4_rnl_et_password1 = (EditText) contentView.findViewById(R.id.login4_rnl_et_password1);
            login4_rnl_iv_show = (ImageView) contentView.findViewById(R.id.login4_rnl_iv_show);
            login4_rnl_et_password2 = (EditText) contentView.findViewById(R.id.login4_rnl_et_password2);
            login4_rnl_bt_register = (Button) contentView.findViewById(R.id.login4_rnl_bt_register);
            login4_rnl_bt_register.setText("确认");
            countDown();
            login4_rnl_tv_text.setVisibility(View.VISIBLE);
            login4_rnl_tv_notice.setText(String.format(getString(R.string.login4_regist_next_notice), mobileStr));
        }
    }

    protected void setListener() {
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
                        loginUtil.getValidatePlat(mobile, new MobileLoginUtil.IMobileLogin() {

                            @Override
                            public void callBack(Object obj) {
                                if (obj == null) {
                                    // 未注册:提示手机号不正确
                                    showToast(R.string.login4_forget_pass_send_notice, CustomToast.DEFAULT);
                                    return;
                                }
                                List<BindPlatBean> list = (List<BindPlatBean>) obj;
                                if (list == null || list.size() == 0) {
                                    // 未注册:提示手机号不正确
                                    showToast(R.string.login4_forget_pass_send_notice, CustomToast.DEFAULT);
                                } else {
                                    for (int i = 0, l = list.size(); i < l; i++) {
                                        BindPlatBean platBean = list.get(i);
                                        if ("shouji".equals(platBean.getType())) {
                                            // 已注册:发送验证码
                                            loginUtil.sendMobileCode(mobile, MobileLoginUtil.FORGET_BY_MOBILE,
                                                    platBean, new MobileLoginUtil.IMobileLogin() {

                                                        @Override
                                                        public void callBack(Object obj) {//发送失败
                                                            showToast(R.string.user_validate_code_send_fail, CustomToast.DEFAULT);
                                                        }
                                                    }, null);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt(MobileLoginUtil.OPRATION_TYPE, 0);
                                            bundle.putString("phone_number", mobile);
                                            bundle.putSerializable("bind_plat", platBean);
                                            Go2Util.startDetailActivity(mContext, sign, "ForgetPassWordStyle4", null, bundle);
                                        }
                                    }
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
                    loginUtil.sendMobileCode(mobileStr, MobileLoginUtil.FORGET_BY_MOBILE,
                            bindPlatBean, new MobileLoginUtil.IMobileLogin() {

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
                        login4_rnl_iv_show.setImageResource(R.drawable.login4_icon_show_pass);
                    } else {
                        login4_rnl_et_password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        login4_rnl_et_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        login4_rnl_iv_show.setImageResource(R.drawable.login4_icon_hide_pass);
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
                    resetPassword(login4_rnl_et_password1.getText().toString(), login4_rnl_et_code.getText().toString());
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
                                login4__rnl_tv_send_code.setText(getResources().getString(R.string.login4_regist_code_send));
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
     * 重置密码
     *
     * @param password
     * @param code
     */
    public void resetPassword(String password, String code) {
//        if (mDialog == null) {
//            mDialog = MMProgress.showProgressDlg(mContext, null, R.string.user_pwd_reseting, false, true, null);
//        } else {
//            mDialog.show();
//        }
//        HashMap<String, String> map = new HashMap<>();
//        map.put("password", password);
//        map.put("verifycode", code);
//        map.put("member_id", bindPlatBean.getMember_id());
//        String url = ConfigureUtils.getUrl(ConfigureUtils.api_map, UserInfoConstants.m_reset_forgetpassword, map);
//        mDataRequestUtil.request(url, new DataRequestUtil.SuccessResponseListener() {
//            @Override
//            public void successResponse(String response) {
//                dismissDialog();
//                if (!ValidateHelper.isHogeValidData(mContext, response, true)) {
//                } else {//成功就弹框
//                    MMAlert.showAlert(mContext, null, "", mContext.getString(R.string.login4_forget_pass_dialog_text),
//                            mContext.getString(R.string.login4_forget_pass_dialog_yes),
//                            null, new OnDialogClick() {
//                                @Override
//                                public void onOkListener(String content) {
//                                    //退出
//                                    finish();
//                                }
//
//                                @Override
//                                public void onClickPreListener(EditText et) {
//                                }
//
//                                @Override
//                                public void onCancelListener(EditText et) {
//                                }
//                            }, false, false);
//                    return;
//                }
//            }
//        }, new DataRequestUtil.ErrorResponseListener() {
//
//            @Override
//            public void errorResponse(String error) {
//                dismissDialog();
//                if (!Util.isConnected()) {
//                    showToast(R.string.no_connection, CustomToast.WARM);
//                } else {
//                    showToast(R.string.error_connection, CustomToast.WARM);
//                }
//            }
//        });
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
        actionBar.setTitle(getResources().getString(R.string.login4_forget_pass));
        TextView menuView = Util.getNewTextView(mContext);
        menuView.setText(getResources().getString(R.string.user_cancel));
        menuView.setGravity(Gravity.CENTER_VERTICAL);
        menuView.setPadding(0, 0, Util.toDip(10), 0);
        menuView.setTextColor(ConfigureUtils.getMultiColor(module_data, ModuleData.ButtonBgColor, "#DC3C38"));
        actionBar.addMenu(RIGHT_MENU, menuView, false);
        Util.setVisibility(actionBar.getBackView(), View.GONE);
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
