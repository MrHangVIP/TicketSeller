package com.zy.ticketseller.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;

import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.adapter.TicketListAdapter;
import com.zy.ticketseller.ui.widget.recyclerview.RecyclerViewLayout;
import com.zy.ticketseller.ui.widget.recyclerview.listener.RecyclerDataLoadListener;
import com.zy.ticketseller.ui.widget.recyclerview.listener.RecyclerListener;

/**
 * 收藏页面
 * Created by Songzhihang on 2018/3/4.
 */
public class TicketStarActivity extends BaseActivity implements RecyclerDataLoadListener {
    private static final String TAG = "TicketStarActivity";
    private RecyclerViewLayout atsl_rcy_list;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_ticket_star_layout);
    }

    @Override
    protected void findViews() {
        atsl_rcy_list = (RecyclerViewLayout) findViewById(R.id.atsl_rcy_list);
    }

    @Override
    protected void initData() {
        setTitle("我的收藏");
        toolbar.setBackgroundColor(Color.WHITE);
        atsl_rcy_list.setAdapter(new TicketListAdapter(this));
        atsl_rcy_list.setListLoadCall(this);
        atsl_rcy_list.setBackgroundColor(Color.parseColor("#F7F7F7"));
        atsl_rcy_list.setPullLoadEnable(true);
        atsl_rcy_list.setItemAnimator(new DefaultItemAnimator());
        atsl_rcy_list.setEmpty_tip(getResources().getString(R.string.no_data));
        atsl_rcy_list.showLoading();
        atsl_rcy_list.onRefresh();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onLoadMore(RecyclerListener recyclerLayout, final boolean isRefresh) {
        atsl_rcy_list.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    atsl_rcy_list.showData(true); // 显示的数据
                } else {
                    atsl_rcy_list.setPullLoadEnable(false);
                }
            }
        }, 1000);
    }
}
