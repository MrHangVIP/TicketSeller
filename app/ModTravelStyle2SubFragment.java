package com.hoge.android.factory;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hoge.android.factory.adapter.ModTravelStyle2FilterAdapter;
import com.hoge.android.factory.adapter.ModTravelStyle2SubAdapter;
import com.hoge.android.factory.base.BaseSimpleFragment;
import com.hoge.android.factory.bean.DBListBean;
import com.hoge.android.factory.bean.TravelStyle2SortsDataBean;
import com.hoge.android.factory.bean.TravelStyle2SubDataBean;
import com.hoge.android.factory.constants.ModuleData;
import com.hoge.android.factory.modtravelstyle2.R;
import com.hoge.android.factory.util.ConfigureUtils;
import com.hoge.android.factory.util.DataRequestUtil;
import com.hoge.android.factory.util.TravelStyle2DataParseUtil;
import com.hoge.android.factory.util.Util;
import com.hoge.android.factory.util.ValidateHelper;
import com.hoge.android.factory.util.json.JsonUtil;
import com.hoge.android.factory.variable.Variable;
import com.hoge.android.factory.views.recyclerview.RecyclerViewLayout;
import com.hoge.android.factory.views.recyclerview.listener.RecyclerDataLoadListener;
import com.hoge.android.factory.views.recyclerview.listener.RecyclerListener;
import com.hoge.android.util.CustomToast;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @discription 旅游样式2首页
 * @autor songzhihang
 * @time 2017/12/11  下午3:26
 **/
public class ModTravelStyle2SubFragment extends BaseSimpleFragment implements RecyclerDataLoadListener {
    private static final String TAG = "ModTravelStyle2SubActivity";
    private RelativeLayout contentView;
    private RecyclerViewLayout xRefreshRecycleView;
    private RelativeLayout filterLayout;
    private RecyclerView filterRecycleView;
    private View travel2_sfl_bg;
    private TextView travel2_shl_tv_area;
    private TextView travel2_shl_tv_sort;

    private ModTravelStyle2SubAdapter adapter;
    private ModTravelStyle2FilterAdapter modTravelStyle2FilterAdapter;
    private ModTravelStyle2SubActivity modTravelStyle2SubActivity;

    private ArrayList<TravelStyle2SortsDataBean> sortLists;
    private ArrayList<TravelStyle2SortsDataBean> areasLists;
    private ArrayList<TravelStyle2SubDataBean> listDatas;

    private AnimatorSet filterViewShowAnimator;
    private AnimatorSet filterViewHideAnimator;

    private int buttonColor;
    private int height = 0;
    private String id;
    private String distance_id = "";
    private String type;//0代表距离 ，1 代表 地区id

    @Override
    protected View getContentView(LayoutInflater inflater) {
        initViews();
        initFilterView();
        getFilterData();
        initDatas();
        return contentView;
    }

    private void initDatas() {
        buttonColor = ConfigureUtils.getMultiColor(module_data, ModuleData.ButtonBgColor, "#31B4BC");
        id = getArguments().getString("id");
        updateSelectDrawable(travel2_shl_tv_sort, false);
        updateSelectDrawable(travel2_shl_tv_area, false);
    }

    private void initViews() {
        contentView = new RelativeLayout(mContext);
        initHeadViews();
        xRefreshRecycleView = new RecyclerViewLayout(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = Util.toDip(42);
        contentView.addView(xRefreshRecycleView, params);
        xRefreshRecycleView.setBackgroundColor(ConfigureUtils.getMultiColor(module_data, ModuleData.ListBackground, "#F7F7F7"));
        adapter = new ModTravelStyle2SubAdapter(mContext, sign);
        xRefreshRecycleView.setAdapter(adapter);
        xRefreshRecycleView.setPullRefreshEnable(true);
        xRefreshRecycleView.setItemAnimator(new DefaultItemAnimator());
        xRefreshRecycleView.setListLoadCall(this);
        xRefreshRecycleView.showLoading();
    }

    private void initHeadViews() {
        View headView = LayoutInflater.from(mContext).inflate(R.layout.travel2_sub_head_layout, null);
        travel2_shl_tv_area = (TextView) headView.findViewById(R.id.travel2_shl_tv_area);
        travel2_shl_tv_sort = (TextView) headView.findViewById(R.id.travel2_shl_tv_sort);
        Util.setCompoundDrawables(travel2_shl_tv_area, Util.toDip(21), Util.toDip(11), 2);
        travel2_shl_tv_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterLayout.isShown()) {
                    hideFilterView();
                    return;
                }
                type = "0";
                updateSelectDrawable(travel2_shl_tv_area, true);
                updateSelectDrawable(travel2_shl_tv_sort, false);
                showFilterView(areasLists.get(0).getSortsDataBeans());
            }
        });
        travel2_shl_tv_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterLayout.isShown()) {
                    hideFilterView();
                    return;
                }
                type = "1";
                updateSelectDrawable(travel2_shl_tv_sort, true);
                updateSelectDrawable(travel2_shl_tv_area, false);
                showFilterView(sortLists);
            }
        });
        contentView.addView(headView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.toDip(42)));
    }

    private void initFilterView() {
        filterLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.travel2_sub_filter_layout, null);
        filterRecycleView = (RecyclerView) filterLayout.findViewById(R.id.travel2_sfl_recycleView);
        travel2_sfl_bg = filterLayout.findViewById(R.id.travel2_sfl_bg);
        filterRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        modTravelStyle2FilterAdapter = new ModTravelStyle2FilterAdapter(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = Util.toDip(42);
        contentView.addView(filterLayout, params);
        filterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFilterView();
            }
        });
        filterLayout.setVisibility(View.GONE);
        filterRecycleView.setAdapter(modTravelStyle2FilterAdapter);
        modTravelStyle2FilterAdapter.setOnItemClickListener(new ModTravelStyle2FilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArrayList list, int position) {
                TravelStyle2SortsDataBean bean = (TravelStyle2SortsDataBean) list.get(position);
                if (TextUtils.equals("0", type)) {
                    distance_id = bean.getId();
                    travel2_shl_tv_area.setText(bean.getName());
                } else {
                    id = bean.getId();
                    travel2_shl_tv_sort.setText(bean.getName());
                }
                xRefreshRecycleView.showLoading();
                onLoadMore(xRefreshRecycleView, true);
                hideFilterView();
            }
        });
    }

    //初始化动画
    private void initAnimator(int size) {
        int durtime = size > 7 ? 7 * 70 : size * 70;
        ObjectAnimator translationYIn = ObjectAnimator.ofFloat(filterRecycleView, "translationY", -height, 0);
        ObjectAnimator alphaIn = ObjectAnimator.ofFloat(travel2_sfl_bg, "alpha", 0, 0.5f);
        filterViewShowAnimator = new AnimatorSet();
        filterViewShowAnimator.play(translationYIn).with(alphaIn);
        filterViewShowAnimator.setDuration(durtime);
        filterViewShowAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                filterLayout.setVisibility(View.VISIBLE);
                filterRecycleView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ObjectAnimator translationYOut = ObjectAnimator.ofFloat(filterRecycleView, "translationY", 0, -height);
        ObjectAnimator alphaOut = ObjectAnimator.ofFloat(travel2_sfl_bg, "alpha", 0.5f, 0);
        filterViewHideAnimator = new AnimatorSet();
        filterViewHideAnimator.play(translationYOut).with(alphaOut);
        filterViewHideAnimator.setDuration(durtime);
        filterViewHideAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                filterLayout.setVisibility(View.GONE);
                filterRecycleView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void showFilterView(ArrayList list) {
        modTravelStyle2FilterAdapter.updateItems(list);
        if (list.size() > 7) {
            filterRecycleView.getLayoutParams().height = Util.toDip(40 * 7);
            height = Util.toDip(40 * 7);
        } else {
            height = Util.toDip(40 * list.size());
            filterRecycleView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        }
        initAnimator(list.size());
        filterViewShowAnimator.start();
    }

    private void hideFilterView() {
        filterViewHideAnimator.start();
        updateSelectDrawable(travel2_shl_tv_sort, false);
        updateSelectDrawable(travel2_shl_tv_area, false);
    }

    private void getFilterData() {
        String url = ConfigureUtils.getUrl(api_data, "travel_sort");
        mDataRequestUtil.request(url, new DataRequestUtil.SuccessResponseListener() {
            @Override
            public void successResponse(String response) {
                xRefreshRecycleView.onRefresh();
                if (!ValidateHelper.isHogeValidData(mContext, response, false)) {
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    sortLists = TravelStyle2DataParseUtil.getSortsDataBeans(JsonUtil.parseJsonBykey(jsonObject, "sort"));
                    areasLists = TravelStyle2DataParseUtil.getSortsDataBeans(JsonUtil.parseJsonBykey(jsonObject, "areas"));
                    setTabData();
                } catch (Exception e) {
                    xRefreshRecycleView.showFailure();
                }
            }
        }, new DataRequestUtil.ErrorResponseListener() {
            @Override
            public void errorResponse(String error) {

            }
        });

    }

    private void setTabData() {
        for (TravelStyle2SortsDataBean bean : sortLists) {
            if (TextUtils.equals(id, bean.getId())) {
                travel2_shl_tv_sort.setText(bean.getName());
                break;
            }
            if (TextUtils.equals("1", bean.getSelected())) {

            }
        }
    }

    private void updateSelectDrawable(TextView view, boolean isSelect) {
        int resId = 0;
        if (isSelect) {
            resId = R.drawable.travel2_icon_arrow_up_select;
            view.setTextColor(buttonColor);
        } else {
            resId = R.drawable.travel2_icon_arrow_down;
            view.setTextColor(Color.parseColor("#626161"));
        }
        Util.updateCompoundDrawables(resId, view, Util.toDip(21), Util.toDip(11), 2);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        actionBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMore(RecyclerListener recyclerLayout, final boolean isRefresh) {
        final String url = ConfigureUtils.getUrl(api_data, "travel_list") +
                "&offset=" + (isRefresh ? 0 : recyclerLayout.getAdapter().getAdapterItemCount()) +
                "&count=" + Variable.DEFAULT_COUNT +
                "&count=10&sort_ids=" + id +
                (TextUtils.isEmpty(distance_id) ? "" : "&distance_id=" + distance_id) +
                "&local_baidu_longitude=" + Variable.LNG +
                "&local_baidu_latitude=" + Variable.LAT;

        if (isRefresh && adapter.getAdapterItemCount() == 0) {// 数据库有数据且列表为空时从数据库取数据并添加到列表//
            // 初次加载或刷新
            DBListBean bean = Util.find(fdb, DBListBean.class, url);
            if (null != bean) {
                ArrayList<TravelStyle2SubDataBean> newItems = TravelStyle2DataParseUtil.getSubDataBeans(bean.getData());
                adapter.clearData();
                adapter.appendData(newItems);
                listDatas = newItems;
                xRefreshRecycleView.showData(false);
                modTravelStyle2SubActivity.showContentView();
            }
        }

        mDataRequestUtil.request(url, new DataRequestUtil.SuccessResponseListener() {

            @Override
            public void successResponse(String response) {
                try {
                    if (!ValidateHelper.isValidData(mActivity, response)) {
                        if (isRefresh && adapter.getAdapterItemCount() == 0) {
                            xRefreshRecycleView.showEmpty();
                            modTravelStyle2SubActivity.showContentView();
                        }
                        return;
                    }
                    if (isRefresh) {
                        Util.save(fdb, DBListBean.class, response, url);// 保存
                    }
                    ArrayList<TravelStyle2SubDataBean> newItems = TravelStyle2DataParseUtil.getSubDataBeans(response);
                    listDatas = newItems;
                    if (newItems.size() == 0) {
                        if (!isRefresh) {
                            CustomToast.showToast(mContext, Util.getString(R.string.no_more_data), CustomToast.DEFAULT);
                        } else {
                            xRefreshRecycleView.showEmpty();
                            modTravelStyle2SubActivity.showContentView();
                            return;
                        }
                    } else {
                        if (isRefresh) {// 仅保存第一页的数据
                            adapter.clearData();
                        }
                        adapter.appendData(newItems);
                    }
                    xRefreshRecycleView.setPullLoadEnable(newItems.size() >= Variable.DEFAULT_COUNT);
                    xRefreshRecycleView.showData(false); // 显示的数据
                    modTravelStyle2SubActivity.showContentView();
                } catch (Exception e) {

                } finally {

                }
            }
        }, new DataRequestUtil.ErrorResponseListener() {

            @Override
            public void errorResponse(String error) {
                xRefreshRecycleView.showFailure();
                ValidateHelper.showFailureError(mActivity, error);
                modTravelStyle2SubActivity.showContentView();
            }
        });
    }

    @Override
    public void left2Right() {
    }

    @Override
    public void right2Left() {
    }

    public ArrayList<TravelStyle2SubDataBean> getListDatas() {
        if (listDatas == null) {
            return new ArrayList<TravelStyle2SubDataBean>();
        }
        return listDatas;
    }

    public void setModTravelStyle2SubActivity(ModTravelStyle2SubActivity modTravelStyle2SubActivity){
        this.modTravelStyle2SubActivity=modTravelStyle2SubActivity;
    }
}
