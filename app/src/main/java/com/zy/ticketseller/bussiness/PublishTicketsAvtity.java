package com.zy.ticketseller.bussiness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zy.ticketseller.BaseActivity;
import com.zy.ticketseller.R;
import com.zy.ticketseller.ui.widget.WheelViewDialog;
import com.zy.ticketseller.util.Constant;
import com.zy.ticketseller.util.FileUtils;
import com.zy.ticketseller.util.MyUtil;
import com.zy.ticketseller.util.SelectPhotoTools;
import com.zy.ticketseller.util.WheelUtil;
import com.zy.ticketseller.util.WheelViewDialogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 发布页面
 * Created by Songzhihang on 2018/3/11.
 */
public class PublishTicketsAvtity extends BaseActivity {
    private static final String TAG = "PublishTicketsAvtity";
    private List<String> titleDataList = new ArrayList<>();
    private WheelViewDialog typeDialog;
    private ImageView aptl_iv_add;
    private EditText aptl_rt_brief;
    private EditText aptl_rt_title;
    private EditText aptl_rt_price;
    private TextView aptl_tv_date_add;
    private TextView aptl_tv_address;
    private Uri photoUri;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_publish_tickets_layout);
    }

    @Override
    protected void findViews() {
        aptl_rt_title = (EditText) findViewById(R.id.aptl_rt_title);
        aptl_rt_brief = (EditText) findViewById(R.id.aptl_rt_brief);
        aptl_rt_price = (EditText) findViewById(R.id.aptl_rt_price);
        aptl_tv_address = (TextView) findViewById(R.id.aptl_tv_address);
        aptl_tv_date_add = (TextView) findViewById(R.id.aptl_tv_date_add);
        aptl_iv_add = (ImageView) findViewById(R.id.aptl_iv_add);
    }

    @Override
    protected void initData() {
        titleDataList.add("音乐会");
        titleDataList.add("歌剧话剧");
        titleDataList.add("舞蹈芭蕾");
        titleDataList.add("演唱会");
        titleDataList.add("体育比赛");
        titleDataList.add("儿童亲子");
        toolbarTitle.setTextColor(Color.parseColor("#333333"));
        MyUtil.setCompoundDrawables(toolbarTitle, MyUtil.toDip(10), MyUtil.toDip(10), 2, R.drawable.icon_arrow_down);
        toolbarTitle.setCompoundDrawablePadding(MyUtil.toDip(5));
        toolbar.inflateMenu(R.menu.publish_submit_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (photoUri != null) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showTypeSelectDialog();
            }
        }, 200);
    }

    @Override
    protected void setListener() {
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeSelectDialog();
            }
        });

        aptl_iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePhoto();
            }
        });
    }

    private void showTypeSelectDialog() {
        //弹框提示
        final WheelUtil typeWheelUtil = new WheelUtil(mContext);
        typeWheelUtil.setDatas(titleDataList.toArray());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinkedHashMap<View, LinearLayout.LayoutParams> map = new LinkedHashMap<>();
        map.put(typeWheelUtil.getWheelView(), params);
        typeDialog = WheelViewDialogUtil.showWheelViewDialog(PublishTicketsAvtity.this, "类型选择", new WheelViewDialog.DialogSubmitListener() {
            @Override
            public void onSubmitClick(View v) {
                toolbarTitle.setText(typeWheelUtil.getItems()[typeWheelUtil.getWheelView().getCurrentItem()].toString());
                typeDialog.dismiss();
            }
        }, map);
        typeDialog.setCancelable(false);
    }


    /**
     * 点击头像按钮
     */
    private void changePhoto() {
        if (!FileUtils.isSdCardExist()) {
            toast("没有找到SD卡，请检查SD卡是否存在");
            return;
        }
        try {
            photoUri = FileUtils.getUriByFileDirAndFileName(Constant.SystemPicture.SAVE_DIRECTORY, Constant.SystemPicture.SAVE_PIC_NAME);
        } catch (IOException e) {
            toast("创建文件失败");
            return;
        }
        SelectPhotoTools.openDialog(this, photoUri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                break;
            case Constant.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
                if (data == null)
                    return;
                photoUri = data.getData();
                break;
        }
        File file = FileUtils.getFileByUri(PublishTicketsAvtity.this, photoUri);
        MyUtil.MyLogE(TAG, file.toString());
        Glide.with(mContext).load(file).into(aptl_iv_add);
    }
}
