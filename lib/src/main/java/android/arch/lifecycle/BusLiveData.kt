package android.arch.lifecycle

open class BusLiveData<T> : MutableLiveData<T>() {

    private val wrappers = mutableMapOf<Observer<T>, BusWrapper>()

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        val wrapper = DefaultWrapper(observer)
        wrappers[observer] = wrapper
        super.observe(owner, wrapper)
    }

    open fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, observer)
    }

    override fun observeForever(observer: Observer<T>) {
        val wrapper = DefaultWrapper(observer)
        wrappers[observer] = wrapper
        super.observeForever(wrapper)
    }

    fun observeStickyForever(observer: Observer<T>) {
        super.observeForever(observer)
    }

    override fun removeObserver(observer: Observer<T>) {
        val key = if (observer is BusWrapper) {
            super.removeObserver(observer)
            super.removeObserver(observer.observer)

            observer.observer
        } else {
            super.removeObserver(observer)
            observer
        }

        wrappers.remove(key)
    }

    inner class DefaultWrapper(observer: Observer<T>): BusWrapper(observer) {
        override fun onChanged(t: T?) {
            if (lastVersion >= version) {
                return
            }

            lastVersion = version
            observer.onChanged(t)
        }

    }

    abstract inner class BusWrapper(val observer: Observer<T>): Observer<T> {
        var lastVersion = version
    }
}