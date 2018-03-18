package com.zy.ticketseller.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zy.ticketseller.R;
import com.zy.ticketseller.util.MyUtil;
import com.zy.ticketseller.util.ShapeUtil;

/**选座购买首页面
 * Created by Songzhihang on 2018/3/10.
 */
public class TicketsTimeAdapter extends RecyclerView.Adapter<TicketsTimeAdapter.ViewHolder> {

    private Context mContext;

    private int selectPosition = -1;

    public TicketsTimeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_buy_time_layout, parent, false);
        TextView ibtl_tv_time = (TextView) itemView.findViewById(R.id.ibtl_tv_time);
        ViewHolder holder = new ViewHolder(itemView, ibtl_tv_time);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.ibtl_tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectPosition == position) {
                    selectPosition = -1;
                } else {
                    selectPosition = position;
                }
                notifyDataSetChanged();
            }
        });

        if (selectPosition == position) {
            holder.ibtl_tv_time.setBackground(ShapeUtil.getDrawable(MyUtil.toDip(3),
                    Color.WHITE, MyUtil.toDip(1),
                    mContext.getResources().getColor(R.color.theme_color)));
            holder.ibtl_tv_time.setTextColor(mContext.getResources().getColor(R.color.theme_color));
        } else {
            holder.ibtl_tv_time.setBackground(ShapeUtil.getDrawable(MyUtil.toDip(3),
                    Color.WHITE, MyUtil.toDip(1),
                    Color.parseColor("#E6E6E6")));
            holder.ibtl_tv_time.setTextColor(Color.parseColor("#333333"));
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ibtl_tv_time;

        public ViewHolder(View itemView, TextView ibtl_tv_time) {
            super(itemView);
            this.ibtl_tv_time = ibtl_tv_time;
        }
    }
}
