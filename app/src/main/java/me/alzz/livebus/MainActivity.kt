package me.alzz.awsl

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import me.alzz.livebus.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Events.addTrans.observe(this) {
            Log.d("liveeventbus", "event data = $it")
        }

        (Events.addTrans + Events.count).observe(this) {
            Log.d("liveeventbus", "total event name = $it")
        }

//        Events.notSticky.send("notSticky 1")
//        Events.sticky.send("stick 1")
//        Events.sticky.send("stick 2")

        Events.cmb.observe(this) {
            Log.d("liveeventbus", "main cmb = $it")
        }

        goBtn.setOnClickListener {

            SecondActivity.navigateTo(this)
        }
    }
}
