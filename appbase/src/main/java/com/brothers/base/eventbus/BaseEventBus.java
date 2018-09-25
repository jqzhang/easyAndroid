package com.brothers.base.eventbus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;
import org.greenrobot.eventbus.meta.SubscriberInfoIndex;

/**
 * @author 刘静修
 * @date 17/11/23 19:22
 * @desc 由于使用 Subscriber Index 优化 register 速度
 */

public class BaseEventBus {
    private static final BaseEventBus mInstance = new BaseEventBus();
    private static EventBus EVENT_BUS;

    public static BaseEventBus getDefault() {
        return mInstance;
    }

    protected BaseEventBus() {

    }

    public void initial(SubscriberInfoIndex[] indexs) {
        if (EVENT_BUS != null) {
            return;
        }

        if (indexs == null) {
            EVENT_BUS = EventBus.getDefault();
            return;
        }
        EventBusBuilder tEventBusBuilder = EventBus.builder();
        for (SubscriberInfoIndex index : indexs) {
            if (index == null) {
                continue;
            }
            tEventBusBuilder.addIndex(index);
        }
        EVENT_BUS = tEventBusBuilder.build();
    }

    public void register(Object object) {
        if (object == null) {
            return;
        }
        if (false == EVENT_BUS.isRegistered(object)) {
            EVENT_BUS.register(object);
        }
    }

    public void unregister(Object object) {
        if (object == null) {
            return;
        }
        if (true == EVENT_BUS.isRegistered(object)) {
            EVENT_BUS.unregister(object);
        }
    }

    public boolean isRegistered(Object object) {
        return EVENT_BUS.isRegistered(object);
    }

    public void post(Object object) {
        EVENT_BUS.post(object);
    }

    public void postSticky(Object object) {
        EVENT_BUS.postSticky(object);
    }

    public void removeSticky(Object event) {
        EVENT_BUS.removeStickyEvent(event);
    }
}
