package com.zy.ticketseller.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zy.ticketseller.BaseFragment;
import com.zy.ticketseller.R;
import com.zy.ticketseller.bussiness.PublishTicketsAvtity;
import com.zy.ticketseller.ui.activity.LoginActivity;
import com.zy.ticketseller.ui.activity.SettingActivity;
import com.zy.ticketseller.ui.activity.TicketStarActivity;
import com.zy.ticketseller.ui.widget.CircleImageView;
import com.zy.ticketseller.util.Constant;
import com.zy.ticketseller.util.SpfUtil;

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
    private LinearLayout ful_ll_second;
    private int type;

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
        ful_ll_second = (LinearLayout) contentView.findViewById(R.id.ful_ll_second);
        fcl_ll_all_ticket = (LinearLayout) contentView.findViewById(R.id.fcl_ll_all_ticket);
        fcl_ll_all_sell = (LinearLayout) contentView.findViewById(R.id.fcl_ll_all_sell);
    }

    @Override
    protected void initData() {
        type = SpfUtil.getInt(Constant.LOGIN_TYPE, 0);
        if (type == Constant.TYPE_BISSINESS) {
            ful_ll_second.setVisibility(View.GONE);
        }
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
        fcl_ll_all_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToNext(PublishTicketsAvtity.class);
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
