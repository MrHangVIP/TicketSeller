package com.zy.ticketseller.ui.activity;

import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;

/**
 * 订单确认
 * Created by Songzhihang on 2018/3/11.
 */
public class OrdersConfirmActivity extends BaseActivity {
    private static final String TAG = "OrdersConfirmActivity";


    @Override
    protected void setView() {
        setContentView(R.layout.activity_order_confirm_layout);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initData() {
        setTitle("订单确认");
    }

    @Override
    protected void setListener() {

    }
}
