package com.zy.ticketseller.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zy.ticketseller.BaseFragment;
import com.zy.ticketseller.R;
import com.zy.ticketseller.bean.TicketItem;
import com.zy.ticketseller.ui.adapter.TicketListAdapter;
import com.zy.ticketseller.ui.widget.recyclerview.RecyclerViewLayout;
import com.zy.ticketseller.ui.widget.recyclerview.listener.RecyclerDataLoadListener;
import com.zy.ticketseller.ui.widget.recyclerview.listener.RecyclerListener;
import com.zy.ticketseller.util.MyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @discription 首页列表fragment
 * @autor songzhihang
 * @time 2017/10/13  下午2:25
 **/
public class TicketListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerDataLoadListener {
    private static final String TAG = "QuestionnaireFragment";

    private Banner fcl_banner;
    private RecyclerViewLayout xRefreshRecycleView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<TicketItem> ticketItems = new ArrayList<>();
    private List<Integer> images = new ArrayList<>();
    private String type;//栏目类型

    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_content_layout, container, false);
    }

    @Override
    protected void initView(View view) {
        xRefreshRecycleView = (RecyclerViewLayout) view.findViewById(R.id.fcl_rv_recycleview);
        xRefreshRecycleView.setAdapter(new TicketListAdapter(getActivity()));
        xRefreshRecycleView.setListLoadCall(this);
        xRefreshRecycleView.setBackgroundColor(Color.parseColor("#F7F7F7"));
        xRefreshRecycleView.setPullLoadEnable(true);
        xRefreshRecycleView.setItemAnimator(new DefaultItemAnimator());
        xRefreshRecycleView.setEmpty_tip(context.getResources().getString(R.string.no_data));
        xRefreshRecycleView.showData(true);
        xRefreshRecycleView.showLoading();
        xRefreshRecycleView.onRefresh();
        initHeadView();
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fcl_sr_swiperefresh);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
//                android.R.color.holo_red_light, android.R.color.holo_orange_light,
//                android.R.color.holo_green_light);
    }

    @Override
    protected void initData() {
        type = getArguments().getString("type");
        fcl_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        fcl_banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        images.add(R.drawable.img_indexpic);
        images.add(R.drawable.img_indexpic);
        images.add(R.drawable.img_indexpic);
        fcl_banner.setImages(images);
        //设置banner动画效果
        fcl_banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//        fcl_banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        fcl_banner.isAutoPlay(true);
        //设置轮播时间
        fcl_banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        fcl_banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        fcl_banner.start();
    }

    private void initHeadView() {
        fcl_banner = new Banner(context);
        View headView = LayoutInflater.from(context).inflate(R.layout.banner_head_layout, null);
        fcl_banner = (Banner) headView.findViewById(R.id.bhl_banner);
//        fcl_banner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyUtil.toDip(100)));
        xRefreshRecycleView.setHeaderView(headView);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getData() {
//        Map<String, String> map = new HashMap<>();
//        map.put("type", type);
//        if(!TextUtils.isEmpty(userId)){
//            map.put("userId", userId);
//        }
//        ProgressDialogUtil.showProgressDialog(getActivity(), true);
//        OkHttpHelp<ResultItem> okHttpHelp = OkHttpHelp.getInstance();
//        okHttpHelp.httpRequest("", Constant.GET_QUESTIONNAIRELIST, map, new ResponseListener<ResultItem>() {
//            @Override
//            public void onSuccess(ResultItem object) {
//                swipeRefreshLayout.setRefreshing(false);
//                ProgressDialogUtil.dismissProgressdialog();
//                if ("fail".equals(object.getResult())) {
//                    toast("网络错误，请重试！");
//                    return;
//                }
////                handler.sendEmptyMessage(refresh);
//                JSONArray jsonArray = null;
//                questionnaireItemList.clear();
//                try {
//                    jsonArray = new JSONArray(object.getData());
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        QuestionnaireItem postBarItem = new Gson().fromJson(jsonArray.get(i).toString(), QuestionnaireItem.class);
//                        questionnaireItemList.add(postBarItem);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailed(String message) {
//                ProgressDialogUtil.dismissProgressdialog();
//                swipeRefreshLayout.setRefreshing(false);
//                toast("网络错误，请重试！");
//            }
//
//            @Override
//            public Class<ResultItem> getEntityClass() {
//                return ResultItem.class;
//            }
//        });
    }

    @Override
    public void onLoadMore(RecyclerListener recyclerLayout, final boolean isRefresh) {
        xRefreshRecycleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    if (TextUtils.equals("演唱会", type)) {
                        xRefreshRecycleView.showEmpty(); // 显示的数据
                    } else {
                        xRefreshRecycleView.showData(false); // 显示的数据
                    }
                } else {
                    xRefreshRecycleView.setPullLoadEnable(false);
                }
            }
        }, 2000);
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }
}
