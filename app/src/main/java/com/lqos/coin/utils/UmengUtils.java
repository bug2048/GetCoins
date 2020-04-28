package com.lqos.coin.utils;

import android.content.Context;

import com.lqos.coin.BuildConfig;
import com.meituan.android.walle.WalleChannelReader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * @author liuliqiang
 * @date 2020/4/18
 */
public class UmengUtils {
    public static void init(Context context) {
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        UMConfigure.setEncryptEnabled(true);
        UMConfigure.init(context, null, WalleChannelReader.getChannel(context, "unkown"), UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    public static void onPause(Context owner) {
        MobclickAgent.onPause(owner);
    }

    public static void onResume(Context owner) {
        MobclickAgent.onResume(owner);
    }
}
