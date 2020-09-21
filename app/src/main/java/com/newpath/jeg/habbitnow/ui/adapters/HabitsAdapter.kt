package com.newpath.jeg.habbitnow.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.ui.viewholders.HabitItemViewHolder

class HabitsAdapter: RecyclerView.Adapter<HabitItemViewHolder>() {

    var myHabitsList =  listOf<MyHabit>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitItemViewHolder {

        return HabitItemViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: HabitItemViewHolder, position: Int) {
        val habitItem: MyHabit = myHabitsList[position]
        val res = holder.itemView.context.resources //not used for now
        holder.bind(habitItem)

    }

    override fun getItemCount(): Int = myHabitsList.size




}