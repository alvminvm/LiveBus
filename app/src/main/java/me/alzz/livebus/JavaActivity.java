package me.alzz.livebus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by JeremyHe on 2019/3/31.
 */
public class JavaActivity extends AppCompatActivity {
    public static void navigateTo(Context context) {
        Intent intent = new Intent(context, JavaActivity.class);
        context.startActivity(intent);
    }

    private Event<String> e = new Event<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Events.getCmb().observe(this, new Function1<String, Unit>() {
            @Override
            public Unit invoke(String s) {
                Log.d("liveeventbus", "error!! receive cmb in JavaActivity");
                return null;
            }
        });
    }
}
