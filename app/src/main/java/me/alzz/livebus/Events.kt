package me.alzz.livebus

class Events {
    companion object {
        @JvmStatic
        val addTrans by Event<String>()
        @JvmStatic
        val count by Event<Int>()

        @JvmStatic
        val notSticky by Event<String>()
        @JvmStatic
        val sticky by Event<String>()
        @JvmStatic
        val cmb = sticky + notSticky
    }
}