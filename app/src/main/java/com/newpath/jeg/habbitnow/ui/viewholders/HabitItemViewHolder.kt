package com.newpath.jeg.habbitnow.ui.viewholders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.databinding.ItemHabitBinding
import com.newpath.jeg.habbitnow.models.MyHabit


class HabitItemViewHolder private constructor(val binding: ItemHabitBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): HabitItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemHabitBinding.inflate(layoutInflater, parent, false)
            return HabitItemViewHolder(binding)
        }
    }

    fun bind(habitItem: MyHabit, myCallback: (result: MyHabit) -> Unit) {

        binding.tvHabitName.text = "" + habitItem.habitName
        binding.tvDaysActive.text = "" + habitItem.id
        binding.ibHabitItemMenu.setOnClickListener { view ->
            when(view.id){
                R.id.ib_habit_item_menu -> myCallback(habitItem)
            }
        }
    }



}