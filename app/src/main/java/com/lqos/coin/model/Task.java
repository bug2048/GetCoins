package com.lqos.coin.model;

/**
 * @author liuliqiang
 * @date 2020/4/18
 */
public class Task {
    private int event;

    private String label;

    public static class EventType {
        public static final int EVENT_CLICK = 1;
        public static final int EVENT_DOUBLE_CLICK = 2;
        public static final int EVENT_LONG_CLICK = 3;
    }

    public Task(int event, String label) {
        this.event = event;
        this.label = label;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
