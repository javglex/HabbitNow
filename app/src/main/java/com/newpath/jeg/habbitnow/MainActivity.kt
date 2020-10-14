package com.newpath.jeg.habbitnow

import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.services.AlarmService
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val newHabitFab: FloatingActionButton = findViewById(R.id.fab)
        newHabitFab.setOnClickListener { view ->
            onNewHabitFabClicked(view)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.allHabits.observe(this,{
            updateAlarmService(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun onNewHabitFabClicked(view: View){
        //since fab doesn't have the navcontroller, we have to find it
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        //navigate to our editHabitFragment
        navController.navigate(R.id.action_nav_home_to_editHabitFragment)
    }

    private fun updateAlarmService(allHabits: List<MyHabit>){

        if (allHabits == null)
            return

        allHabits.forEach { habit ->
            val alarmTime = Calendar.getInstance()
            alarmTime[Calendar.HOUR_OF_DAY] = habit.alarmTimeHours
            alarmTime[Calendar.MINUTE] = habit.alarmTimeMinutes
            alarmTime[Calendar.SECOND] = 0
            AlarmService.setServiceAlarm(this, habit.id.toInt(), alarmTime)
        }

    }
}