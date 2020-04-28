package com.lqos.coin;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lqos.coin.utils.UmengUtils;
import com.meituan.android.walle.WalleChannelReader;
import com.umeng.commonsdk.utils.UMUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author liuliqiang
 * @date 2020/4/18
 */
public class CoinApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UmengUtils.init(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                UmengUtils.onResume(activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                UmengUtils.onPause(activity);
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    private String getAppChannel() {
        return WalleChannelReader.getChannel(this, UMUtils.getChannelByXML(this));
    }
}
