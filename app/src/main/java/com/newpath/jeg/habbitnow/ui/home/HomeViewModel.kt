package com.newpath.jeg.habbitnow.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newpath.jeg.habbitnow.models.MyHabit

class HomeViewModel : ViewModel() {

    val _property = MutableLiveData<List<MyHabit>>()

    init{
        var myHabit1: MyHabit = MyHabit()
        myHabit1.habitName = "My Habit 1"

        var myHabit2 = MyHabit()
        myHabit2.habitName = "Oyy this habit"

        var myHabits: List<MyHabit> = listOf<MyHabit>(myHabit1,myHabit2)

        _property.value = myHabits


    }

}