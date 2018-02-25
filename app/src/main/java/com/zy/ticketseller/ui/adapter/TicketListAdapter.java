package com.zy.ticketseller.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zy.ticketseller.R;
import com.zy.ticketseller.bean.TicketItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @discription 适配器
 * @autor songzhihang
 * @time 2017/10/13  下午3:22
 **/
public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.ViewHolder> {

    private static final String TAG = "TicketListAdapter";
    private Context mContext;

    private List<TicketItem> ticketItems = new ArrayList<>();

    public TicketListAdapter(Context mContext, List<TicketItem> ticketItems) {
        this.mContext = mContext;
        this.ticketItems = ticketItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_list_layout, parent, false);
        ImageView itll_iv_image = (ImageView) view.findViewById(R.id.itll_iv_image);
        TextView itll_tv_title = (TextView) view.findViewById(R.id.itll_tv_title);
        TextView itll_tv_duration = (TextView) view.findViewById(R.id.itll_tv_duration);
        TextView itll_tv_price = (TextView) view.findViewById(R.id.itll_tv_price);
        TextView itll_tv_biref = (TextView) view.findViewById(R.id.itll_tv_biref);
        ViewHolder holder = new ViewHolder(view, itll_iv_image, itll_tv_title, itll_tv_duration, itll_tv_price, itll_tv_biref);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView iql_tv_title;
        public final TextView iql_tv_publisher;
        public final TextView iql_tv_time;
        public final ImageView iql_civ_head;
        public final TextView iql_tv_finish_time;

        public ViewHolder(View view, ImageView iql_civ_head, TextView iql_tv_title, TextView iql_tv_publisher, TextView iql_tv_time, TextView iql_tv_finish_time) {
            super(view);
            mView = view;
            this.iql_civ_head = iql_civ_head;
            this.iql_tv_title = iql_tv_title;
            this.iql_tv_publisher = iql_tv_publisher;
            this.iql_tv_time = iql_tv_time;
            this.iql_tv_finish_time = iql_tv_finish_time;
        }
    }

    private void goDetail(List<TicketItem> item) {
//        if (item.getFinishTimeStmp() < System.currentTimeMillis()) {
//            Toast.makeText(mContext,"此问卷已结束！", Toast.LENGTH_SHORT).show();
//        }else{
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("questionnaireItem", item);
//            ((BaseActivity) mContext).jumpToNext(AnswerQuestionnaireActivity.class, bundle);
//        }
    }
}
