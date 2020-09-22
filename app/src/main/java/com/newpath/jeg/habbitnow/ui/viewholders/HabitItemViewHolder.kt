package com.newpath.jeg.habbitnow.ui.viewholders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.databinding.ItemHabitBinding
import com.newpath.jeg.habbitnow.models.MyHabit
import org.jetbrains.annotations.NotNull


class HabitItemViewHolder private constructor(private val binding: ItemHabitBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): HabitItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemHabitBinding.inflate(layoutInflater, parent, false)
            return HabitItemViewHolder(binding)
        }
    }

    fun bind(habitItem: MyHabit) {
        binding.tvHabitName.text = "" + habitItem.habitName
        binding.tvDaysActive.text = "" + habitItem.alarmType
        binding.ibHabitItemMenu.setOnClickListener { view ->
            when(view.id){
                R.id.ib_habit_item_menu -> openItemMenu(this.itemView.context,this)
            }
        }
    }

    private fun openItemMenu(context: Context, holder: HabitItemViewHolder){

        //creating a popup menu
        val popup = PopupMenu(context, holder.binding.ibHabitItemMenu)

        //inflating menu from xml resource
        popup.inflate(R.menu.menu_habit_item)

        //adding click listener
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit ->                         //handle menu1 click
                    true
                R.id.action_enable ->                         //handle menu2 click
                    true
                R.id.action_delete ->                         //handle menu3 click
                    true
                else -> false
            }
        }
        //displaying the popup
        popup.show()
    }
}