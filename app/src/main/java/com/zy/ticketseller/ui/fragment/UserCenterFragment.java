package com.zy.ticketseller.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zy.ticketseller.BaseFragment;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.activity.LoginActivity;
import com.zy.ticketseller.ui.activity.SettingActivity;
import com.zy.ticketseller.ui.activity.TicketStarActivity;
import com.zy.ticketseller.ui.widget.CircleImageView;

/**
 * Created by Songzhihang on 2017/10/6.
 * 用户中心fragment
 */
public class UserCenterFragment extends BaseFragment {
    private static final String TAG = "UserCenterFragment";
    private ImageView ful_iv_setting;
    private CircleImageView ful_civ_head;
    private TextView ful_tv_nickname;
    private LinearLayout fcl_ll_star;
    private LinearLayout fcl_ll_money;
    private LinearLayout fcl_ll_all_ticket;
    private LinearLayout fcl_ll_all_sell;

    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_usercenter_layout, container, false);
    }

    @Override
    protected void initView(View view) {
        ful_iv_setting = (ImageView) contentView.findViewById(R.id.ful_iv_setting);
        ful_civ_head = (CircleImageView) contentView.findViewById(R.id.ful_civ_head);
        ful_tv_nickname = (TextView) contentView.findViewById(R.id.ful_tv_nickname);
        fcl_ll_money = (LinearLayout) contentView.findViewById(R.id.fcl_ll_money);
        fcl_ll_star = (LinearLayout) contentView.findViewById(R.id.fcl_ll_star);
        fcl_ll_all_ticket = (LinearLayout) contentView.findViewById(R.id.fcl_ll_all_ticket);
        fcl_ll_all_sell = (LinearLayout) contentView.findViewById(R.id.fcl_ll_all_sell);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        ful_civ_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLogin();
            }
        });
        ful_tv_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLogin();
            }
        });
        ful_iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToNext(SettingActivity.class);
            }
        });

        fcl_ll_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToNext(TicketStarActivity.class);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void goLogin() {
        jumpToNext(LoginActivity.class);
    }

}
