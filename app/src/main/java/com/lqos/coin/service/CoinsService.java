package com.lqos.coin.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.lqos.coin.model.Task;
import com.lqos.coin.utils.ScreenUtils;

import java.util.List;
import java.util.Random;

import androidx.annotation.RequiresApi;

/**
 * @author liuliqiang
 * @date 2020/4/15
 */
public class CoinsService extends AccessibilityService implements Runnable {
    private String packName;
    private int nextTime;
    private boolean isFinish;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread executorService = new Thread(this);
        executorService.start();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int ac = event.getEventType();
            if (ac == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || ac == AccessibilityEvent.TYPE_WINDOWS_CHANGED || ac == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                String temp = event.getPackageName().toString();
                if (!TextUtils.isEmpty(temp) && (!temp.contains("android") || !temp.contains("java"))) {
                    packName = event.getPackageName().toString();
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void swipe(String packName) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return;
        }
        if (packName == null || !PackName.PACKLIST.containsKey(packName)) {
            return;
        }
        if (nextTime % 4 == 0) {
            Task task = PackName.PACKLIST.get(packName);
            if (tt(packName, task)) {
                return;
            }
        }
        Path path = new Path();
        int sh = ScreenUtils.getScreenWidthAndHeight(this)[1];

        path.moveTo(10, sh * 0.9f);//从屏幕的2/3处开始滑动
        path.lineTo(10, sh * 0.1f);
        final GestureDescription.StrokeDescription sd = new GestureDescription.StrokeDescription(path, 0, 500);
        dispatchGesture(new GestureDescription.Builder().addStroke(sd).build(), new AccessibilityService.GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                Toast.makeText(getApplicationContext(), "手势成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
                Toast.makeText(getApplicationContext(), "手势失败，请重新尝试", Toast.LENGTH_SHORT).show();

            }
        }, null);
    }

    private boolean hasInter() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
            if (accessibilityNodeInfo == null) {
                return false;
            }
            List<AccessibilityNodeInfo> list = accessibilityNodeInfo.findAccessibilityNodeInfosByText("广告");
            return list != null && !list.isEmpty();
        }
        return false;
    }

    private boolean tt(String packName, Task task) {
        if (task == null) {
            return false;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
            if (accessibilityNodeInfo == null) {
                return false;
            }
            List<AccessibilityNodeInfo> list = accessibilityNodeInfo.findAccessibilityNodeInfosByText(task.getLabel());
            if (list == null || list.isEmpty()) {
                return false;
            }
            AccessibilityNodeInfo nodeInfo = null;
            for (AccessibilityNodeInfo te : list) {
                if (te.getPackageName().equals(packName)) {
                    nodeInfo = te;
                    break;
                }
            }
            try {
                //模拟点击事件
                if (nodeInfo != null) {
                    int count = 8;
                    switch (task.getEvent()) {
                        case Task.EventType.EVENT_LONG_CLICK:
                            for (int i = 0; i < count && nodeInfo != null; i++) {
                                if (nodeInfo.isLongClickable()) {
                                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                                    break;
                                } else {
                                    nodeInfo = nodeInfo.getParent();
                                }
                            }
                            break;
                        case Task.EventType.EVENT_CLICK:
                            for (int i = 0; i < count && nodeInfo != null; i++) {
                                if (nodeInfo.isClickable()) {
                                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    break;
                                } else {
                                    nodeInfo = nodeInfo.getParent();
                                }
                            }
                            break;
                        case Task.EventType.EVENT_DOUBLE_CLICK:
                            for (int i = 0; i < count && nodeInfo != null; i++) {
                                if (nodeInfo.isClickable()) {
                                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    Thread.sleep(50);
                                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    break;
                                } else {
                                    nodeInfo = nodeInfo.getParent();
                                }
                            }

                            break;
                    }
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            } finally {
                if (nodeInfo != null) {
                    nodeInfo.recycle();
                }
                accessibilityNodeInfo.recycle();
            }

            return true;
        }
        return false;
    }

    @Override
    public void run() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Random random = new Random();
            for (; !isFinish; ) {
                if (PackName.PACKLIST.containsKey(packName)) {
                    try {
                        if (!hasInter()) {
                            Thread.sleep(nextTime * 1000);
                        }
                        nextTime = random.nextInt(20);
//                        System.out.println("CoinsService.run = " + nextTime);
                        swipe(packName);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFinish = true;
    }
}
