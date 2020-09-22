package com.newpath.jeg.habbitnow.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.ui.viewholders.HabitItemViewHolder

class HabitsAdapter: ListAdapter<MyHabit, HabitItemViewHolder>(HabitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitItemViewHolder {

        return HabitItemViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: HabitItemViewHolder, position: Int) {
        val habitItem: MyHabit = getItem(position)
        val res = holder.itemView.context.resources //not used for now
        holder.bind(habitItem)

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