package com.newpath.jeg.habbitnow.ui.adapters

import android.app.TimePickerDialog
import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.ui.home.HomeViewModel
import com.newpath.jeg.habbitnow.ui.viewholders.HabitItemViewHolder
import com.newpath.jeg.habbitnow.ui.viewholders.HabitItemViewHolder.Companion.ACTION_MENU
import com.newpath.jeg.habbitnow.ui.viewholders.HabitItemViewHolder.Companion.ACTION_TIME
import java.util.*


class HabitsAdapter(private val mHomeViewModel: HomeViewModel): ListAdapter<MyHabit, HabitItemViewHolder>(
    HabitDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitItemViewHolder {
        return HabitItemViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: HabitItemViewHolder, position: Int) {
        val habitItem: MyHabit = getItem(position)
        val context = holder.itemView.context
        val res = context.resources //not used for now
        holder.bind(habitItem) { res, action ->
            if (action==ACTION_MENU)
                openItemMenu(context, holder, res)
            else if (action==ACTION_TIME)
                openTimePicker(context, holder, res)
        }
    }

    private fun openItemMenu(context: Context, holder: HabitItemViewHolder, habit: MyHabit){

        //creating a popup menu
        val popup = PopupMenu(context, holder.binding.ibHabitItemMenu)

        //inflating menu from xml resource
        popup.inflate(R.menu.menu_habit_item)

        //adding click listener
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit -> {
                    mHomeViewModel.onNavigateToEditHabit(habit)//handle menu1 click
                    true
                }
                R.id.action_enable ->                         //handle menu2 click
                    true
                R.id.action_delete -> {
                    mHomeViewModel.delete(habit)
                    true
                }
                else -> false
            }
        }
        //displaying the popup
        popup.show()
    }

    private fun openTimePicker(context: Context, holder: HabitItemViewHolder, habit: MyHabit){

        val cal: Calendar = Calendar.getInstance()
        val currHour: Int = cal.get(Calendar.HOUR_OF_DAY)
        val currMin: Int = cal.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            { view, hourOfDay, minute ->

                habit.alarmTimeHours = hourOfDay
                habit.alarmTimeMinutes = minute
                mHomeViewModel.update(habit)
                notifyItemChanged(holder.adapterPosition)

            },
            currHour,
            currMin,
            false
        )
        timePickerDialog.show()
    }


}

class HabitDiffCallback: DiffUtil.ItemCallback<MyHabit>(){

    //helps determine whether item was added, removed, or moved
    override fun areItemsTheSame(oldItem: MyHabit, newItem: MyHabit): Boolean {
        return oldItem.id == newItem.id
    }

    //helps determine whether item was updated
    override fun areContentsTheSame(oldItem: MyHabit, newItem: MyHabit): Boolean {
        return oldItem.equals(newItem)
    }

}