package com.zy.ticketseller.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.activity.TicketDetailActivity;
import com.zy.ticketseller.ui.widget.recyclerview.adapter.BaseSimpleRecycleAdapter;
import com.zy.ticketseller.ui.widget.recyclerview.adapter.RVBaseViewHolder;

import java.util.ArrayList;

/**
 * @discription 适配器
 * @autor songzhihang
 * @time 2017/10/13  下午3:22
 **/
public class NoticeListAdapter extends BaseSimpleRecycleAdapter<RVBaseViewHolder> {

    private static final String TAG = "NoticeListAdapter";
    private Context mContext;

    public NoticeListAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    public RVBaseViewHolder getViewHolder(View view) {
        return new RVBaseViewHolder(view);
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_notice_layout, parent, false);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, boolean isItem) {
        super.onBindViewHolder(holder, position, isItem);
    }

    @Override
    public int getAdapterItemCount() {
        return items.size() == 0 ? 5 : items.size();
    }

    @Override
    public void appendData(ArrayList data) {
        super.appendData(data);
    }
}
