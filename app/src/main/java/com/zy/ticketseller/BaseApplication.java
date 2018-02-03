package com.zy.ticketseller;

import android.app.Application;

import com.jyz.handquestionnaire.bean.UserItem;
import com.jyz.handquestionnaire.util.SpfUtil;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by Songzhihang on 2017/10/5.
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getAPPInstance() {
        return instance;
    }

    private UserItem mUser;
//
//    private static List<EmojiItem> emojiItemList=new ArrayList<>();
//
    {
//        PlatformConfig.setWeixin("wx72057967cf7ca57d", "f9bb616fc8a0d97d10ff6c1ee36d81d2");
//        PlatformConfig.setSinaWeibo("1769100272", "4a4a8bc77bcc74549f12450c97b759bf","http://sns.whalecloud.com");
//        PlatformConfig.setQQZone("1106424833", "8rMXk0o4tTNnLQwj");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化缓存
        SpfUtil.init(this);
        Config.DEBUG = true;
        UMShareAPI.get(this);
    }

    public UserItem getmUser() {
        return mUser;
    }

    public void setmUser(UserItem mUser) {
        this.mUser = mUser;
    }
}
