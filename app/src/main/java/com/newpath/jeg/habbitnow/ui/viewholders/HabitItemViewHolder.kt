package com.newpath.jeg.habbitnow.ui.viewholders

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.constants.AlarmConstants
import com.newpath.jeg.habbitnow.databinding.ItemHabitBinding
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.utils.StringGenerator


class HabitItemViewHolder private constructor(val binding: ItemHabitBinding): RecyclerView.ViewHolder(
    binding.root
) {


    companion object {

        const val ACTION_EDIT_HABIT: Int = 0
        const val ACTION_EDIT_TIME: Int = 1
        var noDaysSelectedString: String = ""
        var alarmDrawable: Drawable? = null
        var notifDrawable: Drawable? = null

        fun from(parent: ViewGroup): HabitItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemHabitBinding.inflate(layoutInflater, parent, false)

            //we want to grab our resources during oncreate process instead of bind
            noDaysSelectedString = binding.root.resources.getString(R.string.no_day_selected)

            alarmDrawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //>= API 21
                binding.root.resources.getDrawable(R.drawable.ic_baseline_alarm_24, binding.root.context.applicationContext.theme)
            } else {
                binding.root.resources.getDrawable(R.drawable.ic_baseline_alarm_24);
            }

            notifDrawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //>= API 21
                binding.root.resources.getDrawable(R.drawable.ic_baseline_notifications_active_24, binding.root.context.applicationContext.theme)
            } else {
                binding.root.resources.getDrawable(R.drawable.ic_baseline_notifications_active_24);
            }

            return HabitItemViewHolder(binding)
        }

    }

    fun bind(habitItem: MyHabit, myCallback: (result: MyHabit, action: Int) -> Unit) {

        binding.tvHabitName.text = "" + habitItem.habitName
        binding.tvDaysActive.text = StringGenerator.getDaysFromByte(habitItem.daysActive, binding.root.context)
        binding.viewBackground

        if (binding.tvDaysActive.text=="")
            binding.tvDaysActive.text = noDaysSelectedString

        formatTimeDisplay(habitItem, binding.tvTimeBegins)

        if (habitItem.alarmType==AlarmConstants.AlarmType.ALARM)
            binding.ivHabitIcon.setImageDrawable(alarmDrawable)
        else binding.ivHabitIcon.setImageDrawable(notifDrawable)


//        binding.ibHabitItemMenu.setOnClickListener { view ->
//            when(view.id){
//                R.id.ib_habit_item_menu -> myCallback(habitItem, ACTION_MENU)
//            }
//        }

        binding.tvTimeBegins.setOnClickListener { view ->
            when(view.id){
                R.id.tv_time_begins -> myCallback(habitItem, ACTION_EDIT_TIME)
            }
        }

        binding.clViewForeground.setOnClickListener { view ->
            when(view.id){
                R.id.cl_view_foreground ->
                    myCallback(habitItem, ACTION_EDIT_HABIT)
            }
            return@setOnClickListener
        }
    }

    private fun formatTimeDisplay(habit: MyHabit, tvTime: TextView) {

        val alarmHour: Int = habit.alarmTimeHours
        val alarmMinute: Int = habit.alarmTimeMinutes

        tvTime.text = StringGenerator.getTime(alarmHour,alarmMinute)

    }


}