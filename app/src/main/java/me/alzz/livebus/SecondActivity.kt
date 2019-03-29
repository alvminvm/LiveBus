package me.alzz.awsl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import me.alzz.livebus.R

class SecondActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goBtn.text = "发送通知"
        goBtn.setOnClickListener {
            Events.addTrans.send("orderId 1")
            Events.count.send(1)
            Events.addTrans.send("orderId 2")
            Events.sticky.send("stick 2")
        }

//        Events.sticky.sticky(this) {
//            Log.d("liveeventbus", "event data = $it")
//        }
//
//        Events.notSticky.observe(this) {
//            Log.d("liveeventbus", "event data = $it")
//        }

//        Events.notSticky.send("notSticky 2")

        Events.cmb.sticky(this) {
            Log.d("liveeventbus", "cmb event name = $it")
        }
    }

    companion object {
        fun navigateTo(context: Context) {
            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        }
    }
}