package com.zy.ticketseller.ui.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.widget.recyclerview.RecyclerViewLayout;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author songzhihang
 * @discription 票务详情页
 * @time 2018/2/27  下午4:05
 **/
public class TicketDetailActivity extends BaseActivity {
    private static final String TAG = "TicketDetailActivity";
    private ImageView atdl_iv_bg;
    private RecyclerViewLayout atdl_rcy_list;
    private TextView atdl_tv_star;
    private TextView atdl_tv_buy;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_ticket_detail_layout);
    }

    @Override
    protected void findViews() {
        atdl_iv_bg = (ImageView) findViewById(R.id.atdl_iv_bg);
        atdl_rcy_list = (RecyclerViewLayout) findViewById(R.id.atdl_rcy_list);
        atdl_tv_star = (TextView) findViewById(R.id.atdl_tv_star);
        atdl_tv_buy = (TextView) findViewById(R.id.atdl_tv_buy);
    }

    @Override
    protected void initData() {
        Glide.with(mContext).load(R.drawable.img_app_bg).
                bitmapTransform(new BlurTransformation(mContext, 23, 1)). // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                crossFade(1000).
                into(atdl_iv_bg);
    }

    @Override
    protected void setListener() {

    }
}
