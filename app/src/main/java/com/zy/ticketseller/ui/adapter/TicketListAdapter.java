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
import com.zy.ticketseller.util.Constant;
import com.zy.ticketseller.util.SpfUtil;

import java.util.ArrayList;

/**
 * @discription 适配器
 * @autor songzhihang
 * @time 2017/10/13  下午3:22
 **/
public class TicketListAdapter extends BaseSimpleRecycleAdapter<RVBaseViewHolder> {

    private static final String TAG = "TicketListAdapter";
    private Context mContext;

    public TicketListAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    public RVBaseViewHolder getViewHolder(View view) {
        return new RVBaseViewHolder(view);
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_list_layout, parent, false);
        ImageView itll_iv_image = (ImageView) view.findViewById(R.id.itll_iv_image);
        TextView itll_tv_title = (TextView) view.findViewById(R.id.itll_tv_title);
        TextView itll_tv_duration = (TextView) view.findViewById(R.id.itll_tv_duration);
        TextView itll_tv_price = (TextView) view.findViewById(R.id.itll_tv_price);
        TextView itll_tv_biref = (TextView) view.findViewById(R.id.itll_tv_biref);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, boolean isItem) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpfUtil.getInt(Constant.LOGIN_TYPE, 0) == Constant.TYPE_BISSINESS) {
                    return;
                }
                ((BaseActivity) mContext).jumpToNext(TicketDetailActivity.class);
            }
        });
        super.onBindViewHolder(holder, position, isItem);
    }

    @Override
    public int getAdapterItemCount() {
        return items.size() == 0 ? 20 : items.size();
    }

    @Override
    public void appendData(ArrayList data) {
        super.appendData(data);
    }
}
