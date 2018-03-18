package com.zy.ticketseller.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.adapter.TicketsTimeAdapter;
import com.zy.ticketseller.util.MyUtil;
import com.zy.ticketseller.util.ShapeUtil;

/**
 * Created by Songzhihang on 2018/3/10.
 */
public class BuyTicketsFirstActivity extends BaseActivity {
    private static final String TAG = "BuyTicketsFirstActivity";
    private RecyclerView abtfl_rcy_list;
    private TextView ibtl_tv_time;
    private TextView abtfl_tv_buy;
    private TextView abtfl_tv_last;
    private TextView abtfl_tv_mlus;
    private TextView abtfl_tv_num;
    private TextView abtfl_tv_plus;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_buy_tickets_first_layout);
    }

    @Override
    protected void findViews() {
        abtfl_rcy_list = (RecyclerView) findViewById(R.id.abtfl_rcy_list);
        ibtl_tv_time = (TextView) findViewById(R.id.ibtl_tv_time);
        abtfl_tv_buy = (TextView) findViewById(R.id.abtfl_tv_buy);

        abtfl_tv_last = (TextView) findViewById(R.id.abtfl_tv_last);
        abtfl_tv_mlus = (TextView) findViewById(R.id.abtfl_tv_mlus);
        abtfl_tv_num = (TextView) findViewById(R.id.abtfl_tv_num);
        abtfl_tv_plus = (TextView) findViewById(R.id.abtfl_tv_plus);
    }

    @Override
    protected void initData() {
        setTitle("选座购买");
        ibtl_tv_time.setBackground(ShapeUtil.getDrawable(MyUtil.toDip(3), Color.WHITE, MyUtil.toDip(1), getResources().getColor(R.color.theme_color)));
        ibtl_tv_time.setText("12:00-16：00");
        abtfl_rcy_list.setLayoutManager(new GridLayoutManager(mContext, 3));
        abtfl_rcy_list.setAdapter(new TicketsTimeAdapter(mContext));
    }

    @Override
    protected void setListener() {
        abtfl_tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToNext(OrdersConfirmActivity.class);
            }
        });

        abtfl_tv_mlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNum(false);
            }
        });


        abtfl_tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNum(true);
            }
        });
    }

    private void getNum(boolean add) {
        int num = Integer.parseInt(abtfl_tv_num.getText().toString());
        int last = Integer.parseInt(abtfl_tv_last.getText().toString());
        if (add) {
            if (last == 0) {
                toast("票量不足");
                return;
            }
            last = last - 1;
            num = num + 1;
        } else {
            if (num == 0) {
                toast("您还未添加");
                return;
            }
            num = num - 1;
            last = last + 1;
        }
        if (num == 0) {
            abtfl_tv_num.setTextColor(Color.RED);
        } else {
            abtfl_tv_num.setTextColor(Color.parseColor("#333333"));
        }
        if (last == 0) {
            abtfl_tv_last.setTextColor(Color.RED);
        } else {
            abtfl_tv_last.setTextColor(Color.parseColor("#333333"));
        }
        abtfl_tv_num.setText(num + "");
        abtfl_tv_last.setText(last + "");
    }
}
