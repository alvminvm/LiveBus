package me.alzz.livebus;

import android.arch.lifecycle.BusLiveData;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class LiveBus {

    private final Map<String, BusLiveData> bus;

    private LiveBus() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveBus DEFAULT_BUS = new LiveBus();
    }

    public static LiveBus get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    @SuppressWarnings("unchecked")
    public <T> BusLiveData<T> with(String key, Class<T> type) {
        if (bus.containsKey(key)) {
            return bus.get(key);
        }

        synchronized (this) {
            if (!bus.containsKey(key)) {
                bus.put(key, new BusLiveData<T>());
            }
            return bus.get(key);
        }
    }

    public BusLiveData<Object> with(String key) {
        return with(key, Object.class);
    }

}