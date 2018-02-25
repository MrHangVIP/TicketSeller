package com.zy.ticketseller.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zy.ticketseller.BaseFragment;
import com.zy.ticketseller.R;
import com.zy.ticketseller.bean.TicketItem;
import com.zy.ticketseller.ui.adapter.TicketListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @discription 首页列表fragment
 * @autor songzhihang
 * @time 2017/10/13  下午2:25
 **/
public class TicketListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "QuestionnaireFragment";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<TicketItem> ticketItems = new ArrayList<>();
    private String type;//栏目类型
    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_content_layout, container, false);
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fcl_rv_recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fcl_sr_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }

    @Override
    protected void initData() {
        type = getArguments().getString("type");
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
        onRefresh();
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
                    mRecyclerView.setAdapter(new TicketListAdapter(getActivity(), new ArrayList<TicketItem>()));
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
}
