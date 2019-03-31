package me.alzz.livebus

import android.arch.lifecycle.BusLiveData

object LiveBus {

    private val bus = mutableMapOf<String, BusLiveData<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <T> with(key: String): BusLiveData<T>? {
        if (bus.containsKey(key)) {
            return bus[key] as BusLiveData<T>
        }

        synchronized(this) {
            if (!bus.containsKey(key)) {
                bus[key] = BusLiveData<T>()
            }
            return bus[key] as BusLiveData<T>
        }
    }
}