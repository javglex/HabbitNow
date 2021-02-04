package com.newpath.jeg.habbitnow

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.newpath.jeg.habbitnow.databinding.ActivityAlarmBinding


class AlarmActivity: AppCompatActivity()  {

    val TAG:String = "AlarmActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //turns on screen
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

        val binding: ActivityAlarmBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_alarm
        )

        binding.btnAccept.setOnClickListener{ view ->
            if (view.id==R.id.btn_accept) {
                Log.i(TAG, "user accepted")
                finish()
            }
        }

        binding.btnDefer.setOnClickListener{ view ->
            if (view.id==R.id.btn_defer) {
                Log.i(TAG, "user deferred")
                finish()
            }
        }

    }
}