package me.alzz.awsl

import me.alzz.livebus.Event

class Events {
    companion object {
        val addTrans by Event<String>()
        val count by Event<Int>()

        val notSticky by Event<String>()
        val sticky by Event<String>()
        val cmb = sticky + notSticky
    }
}