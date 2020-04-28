package com.lqos.coin.service;

import com.lqos.coin.model.Task;

import java.util.HashMap;

/**
 * @author liuliqiang
 * @date 2020/4/18
 */
public class PackName {
    // 快手极速版
    private static final String PACK_NAME_1 = "com.kuaishou.nebula";
    //刷宝
    private static final String PACK_NAME_2 = "com.jm.video";
    //抖音 火山版
    private static final String PACK_NAME_3 = "com.ss.android.ugc.live";
    //微视
    private static final String PACK_NAME_4 = "com.tencent.weishi";

    public static final HashMap<String, Task> PACKLIST;

    static {
        PACKLIST = new HashMap<>();
        PACKLIST.put(PACK_NAME_1, new Task(Task.EventType.EVENT_CLICK, "发现"));
        PACKLIST.put(PACK_NAME_2, new Task(Task.EventType.EVENT_CLICK, "首页"));

        PACKLIST.put(PACK_NAME_3, new Task(Task.EventType.EVENT_CLICK, "发现"));
        PACKLIST.put(PACK_NAME_4, new Task(Task.EventType.EVENT_CLICK, "推荐"));
    }
}
