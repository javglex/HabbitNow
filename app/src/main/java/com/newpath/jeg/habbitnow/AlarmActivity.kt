package com.newpath.jeg.habbitnow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.newpath.jeg.habbitnow.databinding.ActivityAlarmBinding
import com.newpath.jeg.habbitnow.databinding.FragmentHomeBinding

class AlarmActivity: AppCompatActivity()  {

    val TAG:String = "AlarmActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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