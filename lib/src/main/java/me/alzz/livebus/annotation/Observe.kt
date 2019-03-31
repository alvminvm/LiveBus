package me.alzz.livebus.annotation

import android.support.v7.app.AppCompatActivity
import me.alzz.livebus.Event

/**
 * 事件注解，用于简化事件订阅
 * Created by JeremyHe on 2019/3/31.
 */

@Target(AnnotationTarget.FUNCTION)
annotation class Observe(val e: AppCompatActivity)