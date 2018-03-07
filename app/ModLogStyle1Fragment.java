package com.hoge.android.factory;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v7.widget.DefaultItemAnimator;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hoge.android.factory.adapter.LogStyle1Adapter;
import com.hoge.android.factory.base.BaseSimpleFragment;
import com.hoge.android.factory.bean.DBListBean;
import com.hoge.android.factory.bean.LogStyle1LogBean;
import com.hoge.android.factory.bean.LogStyle1TimeBean;
import com.hoge.android.factory.bean.TagBean;
import com.hoge.android.factory.constants.ModuleData;
import com.hoge.android.factory.interfaces.ModuleBackEvent;
import com.hoge.android.factory.listeners.OnClickEffectiveListener;
import com.hoge.android.factory.modlogstyle1.R;
import com.hoge.android.factory.util.ColorUtil;
import com.hoge.android.factory.util.ConfigureUtils;
import com.hoge.android.factory.util.DataRequestUtil;
import com.hoge.android.factory.util.LogStyle1DataParseUtil;
import com.hoge.android.factory.util.LogStyle1WheelUtil;
import com.hoge.android.factory.util.LogStyle1WheelViewDialog;
import com.hoge.android.factory.util.LogStyle1WheelViewDialogUtil;
import com.hoge.android.factory.util.Util;
import com.hoge.android.factory.util.ValidateHelper;
import com.hoge.android.factory.variable.Variable;
import com.hoge.android.factory.view.LogStyle1FloatingItemDecoration;
import com.hoge.android.factory.views.recyclerview.RecyclerViewLayout;
import com.hoge.android.factory.views.recyclerview.listener.RecyclerDataLoadListener;
import com.hoge.android.factory.views.recyclerview.listener.RecyclerListener;
import com.hoge.android.util.CustomToast;
import com.hoge.android.util.DataConvertUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @discription 行为分析
 * @autor songzhihang
 * @time 2017/11/16  上午11:13
 **/
public class ModLogStyle1Fragment extends BaseSimpleFragment implements RecyclerDataLoadListener, ModuleBackEvent {
    private static final String TAG = "ModLogStyle1Fragment";
    private final int FILTER_ACTION = 0x20;
    private int buttonColor;
    private RelativeLayout mContentView;
    private RecyclerViewLayout xRefreshRecycleView;
    private View filterView;
    private LinearLayout log1_lfl_ll_time;
    private TextView log1_lfl_tv_time;
    private LinearLayout log1_lfl_ll_keywords;
    private EditText log1_lfl_tv_keywords;
    private LinearLayout log1_lfl_ll_person;
    private EditText log1_lfl_tv_person;
    private LinearLayout log1_lfl_ll_action;
    private EditText log1_lfl_tv_action;
    private TextView log1_lfl_tv_reset;
    private TextView log1_lfl_tv_confirm;

    private LogStyle1Adapter adapter;
    private LinkedHashMap<Integer, String> keys;
    private List<String> groupNameList = new ArrayList<>();
    private ArrayList<LogStyle1TimeBean> filterTimeList;
    private String columnId = "";
    private String create_time = "";
    private String keywords = "";
    private String action = "";
    private String username = "";
    private int selectPotison = -1;
    private LogStyle1FloatingItemDecoration floatingItemDecoration;
    private ObjectAnimator filterViewShowAnimator;
    private ObjectAnimator filterViewHideAnimator;
    private LogStyle1WheelViewDialog timeDialog;

    @Override
    protected View getContentView(LayoutInflater inflater) {
        initData();
        initViews();
        initAnimator();
        getcloumnFromNet();
        setListener();
        return mContentView;
    }

    private void initData() {
        keys = new LinkedHashMap<>();
        buttonColor = ConfigureUtils.getMultiColor(module_data, ModuleData.ButtonBgColor, "#0049A3");
    }

    private void initViews() {
        mContentView = new RelativeLayout(mContext);
        xRefreshRecycleView = new RecyclerViewLayout(mContext);
        mContentView.addView(xRefreshRecycleView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        xRefreshRecycleView.setListLoadCall(this);
        xRefreshRecycleView.setBackgroundColor(ConfigureUtils.getMultiColor(module_data, ModuleData.ListBackground, "#F7F7F7"));
        adapter = new LogStyle1Adapter(mContext, sign, buttonColor);
        xRefreshRecycleView.setAdapter(adapter);
        xRefreshRecycleView.setPullLoadEnable(false);
        xRefreshRecycleView.setItemAnimator(new DefaultItemAnimator());
        xRefreshRecycleView.setEmpty_tip(Util.getString(R.string.no_data));
        xRefreshRecycleView.showData(true);
        xRefreshRecycleView.showLoading();
        initFilterView();
    }


    //初始化筛选弹框
    private void initFilterView() {
        filterView = LayoutInflater.from(mContext).inflate(R.layout.log1_flit_layout, null);
        log1_lfl_ll_time = (LinearLayout) filterView.findViewById(R.id.log1_lfl_ll_time);
        log1_lfl_tv_time = (TextView) filterView.findViewById(R.id.log1_lfl_tv_time);
        log1_lfl_ll_keywords = (LinearLayout) filterView.findViewById(R.id.log1_lfl_ll_keywords);
        log1_lfl_tv_keywords = (EditText) filterView.findViewById(R.id.log1_lfl_tv_keywords);
        log1_lfl_ll_person = (LinearLayout) filterView.findViewById(R.id.log1_lfl_ll_person);
        log1_lfl_tv_person = (EditText) filterView.findViewById(R.id.log1_lfl_tv_person);
        log1_lfl_ll_action = (LinearLayout) filterView.findViewById(R.id.log1_lfl_ll_action);
        log1_lfl_tv_action = (EditText) filterView.findViewById(R.id.log1_lfl_tv_action);
        log1_lfl_tv_reset = (TextView) filterView.findViewById(R.id.log1_lfl_tv_reset);
        log1_lfl_tv_confirm = (TextView) filterView.findViewById(R.id.log1_lfl_tv_confirm);
        View log1_lfl_other = filterView.findViewById(R.id.log1_lfl_other);
        log1_lfl_other.setOnClickListener(new OnClickEffectiveListener() {
            @Override
            public void onClickEffective(View v) {
                filterViewHideAnimator.start();
            }
        });
        mContentView.addView(filterView);
        filterView.setVisibility(View.GONE);
    }

    //初始化动画
    private void initAnimator() {
        filterViewShowAnimator = ObjectAnimator.ofFloat(filterView, "translationY", -Util.toDip(210), 0f);
        filterViewShowAnimator.setDuration(400);
        filterViewHideAnimator = ObjectAnimator.ofFloat(filterView, "translationY", 0f, -Util.toDip(210));
        filterViewHideAnimator.setDuration(400);
        filterViewHideAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                filterView.setVisibility(View.GONE);
                Util.hideSoftInput(filterView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void getcloumnFromNet() {
        String url = ConfigureUtils.getUrl(api_data, "log_sort");
        mDataRequestUtil.request(url, new DataRequestUtil.SuccessResponseListener() {
            @Override
            public void successResponse(String response) {
                if (!ValidateHelper.isHogeValidData(mContext, response)) {
                    return;
                }
                ArrayList<TagBean> tagList = (ArrayList<TagBean>) LogStyle1DataParseUtil
                        .getTagBeanList(response);
                columnId = tagList.get(0).getId();
                xRefreshRecycleView.onRefresh();
                filterTimeList = (ArrayList<LogStyle1TimeBean>) LogStyle1DataParseUtil
                        .getFilterTime(response);
            }
        }, new DataRequestUtil.ErrorResponseListener() {
            @Override
            public void errorResponse(String error) {

            }
        });
    }

    private void setListener() {
        //弹框
        log1_lfl_ll_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeDialog != null) {
                    timeDialog.show();
                    return;
                }
                if (filterTimeList == null) {
                    showToast(getString(R.string.log1_get_time_fail));
                    return;
                }
                String[] timeArrs = new String[filterTimeList.size()];
                for (int i = 0; i < filterTimeList.size(); i++) {
                    timeArrs[i] = filterTimeList.get(i).getValue();
                }
                final LogStyle1WheelUtil timeWheelUtil = new LogStyle1WheelUtil(mContext);
                timeWheelUtil.setDatas(timeArrs);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                LinkedHashMap<View, LinearLayout.LayoutParams> map = new LinkedHashMap<>();
                map.put(timeWheelUtil.getWheelView(), params);
                timeDialog = LogStyle1WheelViewDialogUtil.showWheelViewDialog(getActivity(), getString(R.string.log1_select_time), new LogStyle1WheelViewDialog.DialogSubmitListener() {
                    @Override
                    public void onSubmitClick(View v) {
                        log1_lfl_tv_time.setText(timeWheelUtil.getItems()[timeWheelUtil.getWheelView().getCurrentItem()].toString());
                        selectPotison = timeWheelUtil.getWheelView().getCurrentItem();
                        timeDialog.dismiss();
                    }
                }, map);
            }
        });

        //重置
        log1_lfl_tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.hideSoftInput(v);
                clearWords();
                log1_lfl_tv_time.setText(getString(R.string.log1_select_time));
                log1_lfl_tv_keywords.setText("");
                log1_lfl_tv_action.setText("");
                log1_lfl_tv_person.setText("");
            }
        });

        //帅选
        log1_lfl_tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.hideSoftInput(v);
                if (filterTimeList != null && selectPotison != -1) {
                    create_time = filterTimeList.get(selectPotison).getKey();
                }
                keywords = log1_lfl_tv_keywords.getText().toString().trim();
                username = log1_lfl_tv_person.getText().toString().trim();
                action = log1_lfl_tv_action.getText().toString().trim();
                filterViewHideAnimator.start();
                xRefreshRecycleView.showLoading();
                xRefreshRecycleView.onRefresh();
            }
        });
    }

    private void clearWords() {
        selectPotison = -1;
        create_time = "";
        username = "";
        action = "";
        keywords = "";
    }

    //分组数据
    private void groupLogData(List list, boolean isfresh) {
        if (list == null) {
            return;
        }
        if (list.size() == 0) {
            return;
        }
        int offset = 0;
        if (isfresh) {
            keys.clear();
            groupNameList.clear();
        } else {
            offset = adapter.getAdapterItemCount();
        }
        //首先获取零点时间
        long zero = getDayBegin();//今天零点零分零秒的毫秒数
        //获取昨天零点时间
        long yesterdayTime = zero - 1000 * 60 * 60 * 24;
        boolean today = false;
        boolean yesterday = false;
        for (int i = 0; i < list.size(); i++) {
            long time = 0;
            String date = "";
            try {
                date = ((LogStyle1LogBean) list.get(i)).getCreated_at();
                time = DataConvertUtil.stringToTimestamp(date, DataConvertUtil.FORMAT_DATA_TIME);
            } catch (Exception e) {

            }
            if (time >= zero && !today && !groupNameList.contains(getString(R.string.log1_today))) {
                keys.put(offset + i, getString(R.string.log1_today));
                groupNameList.add(getString(R.string.log1_today));
                today = true;
                continue;
            }
            if (yesterdayTime <= time && time < zero && !yesterday && !groupNameList.contains(getString(R.string.log1_yesterday))) {
                keys.put(offset + i, getString(R.string.log1_yesterday));
                groupNameList.add(getString(R.string.log1_yesterday));
                yesterday = true;
                continue;
            }
            if (yesterdayTime > time) {//显示日期
                String data = date.substring(0, 10);
                if (groupNameList.contains(data)) {
                    //存在同组数据不需要分组
                    continue;
                }
                keys.put(offset + i, data);//添加分组
                groupNameList.add(data);
                continue;
            }
        }
        if (floatingItemDecoration == null) {
            initItemDecoration();
        } else {
            floatingItemDecoration.updateKeys(keys);
        }
    }

    private void initItemDecoration() {
        floatingItemDecoration = new LogStyle1FloatingItemDecoration(mContext, ColorUtil.getColor("#dcdcdc"), 0, 0);
        floatingItemDecoration.setKeys(keys);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.log1_icon_time);
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Log.e(TAG, "Width:" + width + "Height:" + height);
        // 取得想要缩放的matrix參數
        Matrix matrix = new Matrix();
        matrix.postScale(Util.toDip(15) / (float) width, Util.toDip(15) / (float) height);
        // 得到新的圖片
        Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        floatingItemDecoration.setImageBitmap(newbm);
        floatingItemDecoration.setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
        floatingItemDecoration.setmBackColor(ColorUtil.getColor("#f1f5f6"));
        floatingItemDecoration.setTextSize(12);
        floatingItemDecoration.setTextColor(Color.parseColor("#8f979e"));
        floatingItemDecoration.setTitlePadding(Util.toDip(32), 0);
        xRefreshRecycleView.getRecyclerview().addItemDecoration(floatingItemDecoration);
    }

    public Long getDayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return cal.getTimeInMillis();
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        actionBar.setTitle(getString(R.string.log1_log_title));
        actionBar.setTitleTextSize(17);
        RelativeLayout layout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Util.toDip(16), Util.toDip(18));
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.rightMargin = Util.toDip(12);
        ImageView filterImage = new ImageView(mContext);
        filterImage.setImageResource(R.drawable.log1_icon_filt);
        layout.addView(filterImage, params);
        actionBar.addMenu(FILTER_ACTION, layout, true);
    }

    @Override
    public void onMenuClick(int mid, View v) {
        super.onMenuClick(mid, v);
        if (mid == FILTER_ACTION) {//弹窗
            if (filterView.getVisibility() == View.VISIBLE) {
                filterViewHideAnimator.start();
            } else {
                filterView.setVisibility(View.VISIBLE);
                filterViewShowAnimator.start();
            }

        }
    }


    @Override
    public void onLoadMore(final RecyclerListener recyclerLayout,
                           final boolean isRefresh) {
        final String url = ConfigureUtils.getUrl(api_data, "log_list") + "&source_system_mark=" + columnId
                + (TextUtils.isEmpty(create_time) ? "" : ("&create_time=" + create_time))
                + (TextUtils.isEmpty(keywords) ? "" : ("&keywords=" + keywords))
                + (TextUtils.isEmpty(action) ? "" : ("&action=" + action))
                + (TextUtils.isEmpty(username) ? "" : ("&username=" + username))
                + "&offset=" + (isRefresh ? 0 : recyclerLayout.getAdapter().getAdapterItemCount())
                + "&count=" + Variable.DEFAULT_COUNT;
        if (isRefresh && adapter.getAdapterItemCount() == 0) {// 数据库有数据且列表为空时从数据库取数据并添加到列表//
            // 初次加载或刷新
            DBListBean bean = Util.find(fdb, DBListBean.class, url);
            if (null != bean) {
                ArrayList<LogStyle1LogBean> newItems = (ArrayList<LogStyle1LogBean>) LogStyle1DataParseUtil
                        .getLogDataList(bean.getData());
                groupLogData(newItems, isRefresh);
                adapter.clearData();
                adapter.appendData(newItems);
            }
        }
        // 网络加载
        mDataRequestUtil.request(url, new DataRequestUtil.SuccessResponseListener() {

            @Override
            public void successResponse(String response) {
                try {
                    if (!ValidateHelper.isValidData(mActivity, response)) {
                        if (isRefresh && adapter.getAdapterItemCount() == 0) {
                            xRefreshRecycleView.showEmpty();
                        }
                        return;
                    }
                    if (isRefresh) {
                        Util.save(fdb, DBListBean.class, response, url);// 保存
                    }
                    ArrayList<LogStyle1LogBean> newItems = (ArrayList<LogStyle1LogBean>) LogStyle1DataParseUtil
                            .getLogDataList(response);
                    if (newItems.size() == 0) {
                        if (!isRefresh) {
                            CustomToast.showToast(mContext, Util.getString(R.string.no_more_data), CustomToast.DEFAULT);
                        } else {
                            xRefreshRecycleView.showEmpty();
                            return;
                        }
                    } else {
                        if (isRefresh) {// 仅保存第一页的数据
                            adapter.clearData();
                        }
                        groupLogData(newItems, isRefresh);
                        adapter.appendData(newItems);
                    }
                    xRefreshRecycleView.setPullLoadEnable(newItems.size() >= Variable.DEFAULT_COUNT);
                    xRefreshRecycleView.showData(false); // 显示的数据
                } catch (Exception e) {
                    xRefreshRecycleView.showData(false); // 显示的数据
                } finally {
                }
            }
        }, new DataRequestUtil.ErrorResponseListener() {

            @Override
            public void errorResponse(String error) {
                xRefreshRecycleView.showFailure();
                ValidateHelper.showFailureError(mActivity, error);
            }
        });

    }

}
