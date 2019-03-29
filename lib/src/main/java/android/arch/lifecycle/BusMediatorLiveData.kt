package android.arch.lifecycle

import android.annotation.SuppressLint
import android.arch.core.executor.ArchTaskExecutor
import android.arch.core.internal.SafeIterableMap
import android.support.annotation.CallSuper
import android.support.annotation.MainThread

@SuppressLint("RestrictedApi")
class BusMediatorLiveData<T>: BusLiveData<T>() {
    private val mSources = SafeIterableMap<BusLiveData<*>, Source<*>>()

    /**
     * Starts to listen the given `source` LiveData, `onChanged` observer will be called
     * when `source` value was changed.
     *
     *
     * `onChanged` callback will be called only when this `MediatorLiveData` is active.
     *
     *  If the given LiveData is already added as a source but with a different Observer,
     * [IllegalArgumentException] will be thrown.
     *
     * @param source    the `LiveData` to listen to
     * @param onChanged The observer that will receive the events
     * @param <S>       The type of data hold by `source` LiveData
    </S> */
    @MainThread
    fun <S> addSource(source: BusLiveData<S>, onChanged: Observer<S>) {
        val e = Source(source, onChanged)
        val existing = mSources.putIfAbsent(source, e)
        if (existing != null && existing.mObserver !== onChanged) {
            throw IllegalArgumentException(
                "This source was already added with the different observer"
            )
        }
        if (existing != null) {
            return
        }
        if (hasActiveObservers()) {
            e.plug()
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        onActive()
        ArchTaskExecutor.getInstance().postToMainThread {
            super.observe(owner, observer)
        }
    }

    /**
     * Stops to listen the given `LiveData`.
     *
     * @param source `LiveData` to stop to listen
     * @param <S>      the type of data hold by `source` LiveData
    </S> */
    @MainThread
    fun <S> removeSource(source: BusLiveData<S>) {
        @Suppress("NAME_SHADOWING")
        val source = mSources.remove(source)
        source?.unplug()
    }

    @CallSuper
    override fun onActive() {
        for ((_, value) in mSources) {
            value.plug()
        }
    }

    @CallSuper
    override fun onInactive() {
        for ((_, value) in mSources) {
            value.unplug()
        }
    }

    private class Source<V> internal constructor(
        internal val mLiveData: BusLiveData<V>,
        internal val mObserver: Observer<V>
    ) : Observer<V> {
        internal var mVersion = mLiveData.version

        internal fun plug() {
            mLiveData.observeStickyForever(this)
        }

        internal fun unplug() {
            mLiveData.removeObserver(this)
        }

        override fun onChanged(v: V?) {
            if (mVersion != mLiveData.version) {
                mVersion = mLiveData.version
                mObserver.onChanged(v)
            }
        }
    }
}