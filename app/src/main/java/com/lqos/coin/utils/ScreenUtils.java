package com.lqos.coin.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * @author liuliqiang
 * @date 2020/4/18
 */
public class ScreenUtils {
    private ScreenUtils() {
    }

    public static int[] getScreenWidthAndHeight(@NonNull Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }
}
