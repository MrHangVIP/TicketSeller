package com.zy.ticketseller.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.adapter.TicketListAdapter;
import com.zy.ticketseller.ui.widget.NoticeDialog;
import com.zy.ticketseller.ui.widget.ShareDialog;
import com.zy.ticketseller.ui.widget.recyclerview.RecyclerViewLayout;
import com.zy.ticketseller.ui.widget.recyclerview.listener.RecyclerDataLoadListener;
import com.zy.ticketseller.ui.widget.recyclerview.listener.RecyclerListener;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author songzhihang
 * @discription 票务详情页
 * @time 2018/2/27  下午4:05
 **/
public class TicketDetailActivity extends BaseActivity implements RecyclerDataLoadListener {
    private static final String TAG = "TicketDetailActivity";
    private ImageView atdl_iv_bg;
    private RecyclerViewLayout atdl_rcy_list;
    private ImageView atdl_iv_star;
    private TextView atdl_tv_buy;
    private NoticeDialog noticeDialog;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_ticket_detail_layout);
    }

    @Override
    protected void findViews() {
        atdl_iv_bg = (ImageView) findViewById(R.id.atdl_iv_bg);
        atdl_rcy_list = (RecyclerViewLayout) findViewById(R.id.atdl_rcy_list);
        atdl_iv_star = (ImageView) findViewById(R.id.atdl_iv_star);
        atdl_tv_buy = (TextView) findViewById(R.id.atdl_tv_buy);
        initHeadViews();
    }

    private void initHeadViews() {
        View headView = LayoutInflater.from(mContext).inflate(R.layout.layout_ticket_detail_head, null);
        ImageView ltdh_iv_index = (ImageView) headView.findViewById(R.id.ltdh_iv_index);
        TextView ltdh_tv_title = (TextView) headView.findViewById(R.id.ltdh_tv_title);
        TextView ltdh_tv_price = (TextView) headView.findViewById(R.id.ltdh_tv_price);
        TextView ltdh_tv_time = (TextView) headView.findViewById(R.id.ltdh_tv_time);
        TextView ltdh_tv_address = (TextView) headView.findViewById(R.id.ltdh_tv_address);
        TextView ltdh_tv_biref = (TextView) headView.findViewById(R.id.ltdh_tv_biref);
        RelativeLayout ltdh_ll_address_layout = (RelativeLayout) headView.findViewById(R.id.ltdh_ll_address_layout);
        RelativeLayout ltdh_ll_notice = (RelativeLayout) headView.findViewById(R.id.ltdh_ll_notice);
        ltdh_ll_address_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "南京.奥体中心");
                jumpToNext(MapDetailActivity.class, bundle);
            }
        });
        //弹窗
        ltdh_ll_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noticeDialog==null){
                    noticeDialog=new NoticeDialog(TicketDetailActivity.this);
                }
                noticeDialog.show();
            }
        });
        atdl_rcy_list.setHeaderView(headView);
    }

    @Override
    protected void initData() {
        setTitle("");
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.inflateMenu(R.menu.ticket_detail__share_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //分享弹窗
                new ShareDialog((Activity) TicketDetailActivity.this).
                        setShareContent("快来看看（" + "title" + "）这个问卷，快来看看吧！")
                        .show();
                return true;
            }
        });
        Glide.with(mContext).load(R.drawable.img_app_bg).
                bitmapTransform(new BlurTransformation(mContext, 23, 1)). // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                crossFade(1000).
                into(atdl_iv_bg);
        atdl_rcy_list.setAdapter(new TicketListAdapter(mContext));
        atdl_rcy_list.setBackgroundColor(Color.TRANSPARENT);
        atdl_rcy_list.setListLoadCall(this);
        atdl_rcy_list.setPullLoadEnable(false);
        atdl_rcy_list.setPullRefreshEnable(false);
        atdl_rcy_list.setItemAnimator(new DefaultItemAnimator());
        atdl_rcy_list.setEmpty_tip(mContext.getResources().getString(R.string.no_data));
        atdl_rcy_list.showData(true);
        atdl_rcy_list.showLoading();
        atdl_rcy_list.onRefresh();
        atdl_rcy_list.showData(true);
    }

    @Override
    protected void setListener() {
        atdl_iv_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atdl_iv_star.setImageResource(R.drawable.icon_star_select);
            }
        });
    }

    @Override
    public void onLoadMore(RecyclerListener recyclerLayout, boolean isRefresh) {

    }

    @Override
    public Drawable setBackIcon() {
        return getDrawableRes(R.drawable.icon_back_white);
    }

    /**
     * 设置状态栏透明
     */
    private void setWindowTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // 透明状态栏
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
