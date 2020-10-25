package com.newpath.jeg.habbitnow.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.databinding.FragmentHomeBinding
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.ui.adapters.HabitsAdapter
import com.newpath.jeg.habbitnow.ui.adapters.RecyclerItemTouchHelper
import com.newpath.jeg.habbitnow.ui.edithabit.EditHabitFragment
import com.newpath.jeg.habbitnow.ui.viewholders.HabitItemViewHolder


class HomeFragment : Fragment(), RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mAdapter: HabitsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        mBinding = binding  //for use by onResume callback
        mHomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.lifecycleOwner = this
        binding.homeFragmentViewModel = mHomeViewModel

        mAdapter = HabitsAdapter(mHomeViewModel)
        binding.rvHabitList.adapter = mAdapter

        mHomeViewModel.allHabits.observe(viewLifecycleOwner, Observer {
            it?.let {
                mAdapter.submitList(it)
            }
        })

        mHomeViewModel.navigateToEditHabit.observe(viewLifecycleOwner, {
            it?.let { habit ->
                if (habit != null)
                    onNavigateToEditHabitClicked(habit)
                mHomeViewModel.onNavigateToEditHabitComplete()
            }
        })

        val itemTouchHelperCallbackLeft: ItemTouchHelper.SimpleCallback =
            RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallbackLeft).attachToRecyclerView(binding.rvHabitList)

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            mBinding?.rvHabitList.smoothScrollToPosition(0)
        }, 250)
    }

    private fun onNavigateToEditHabitClicked(habit: MyHabit){
        //since fab doesn't have the navcontroller, we have to find it
        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment?.navController

        //pass our arguments so that our fragment recognizes we are editing
        val bundle = bundleOf(
            EditHabitFragment.HABIT_NAME_KEY to habit.habitName,
            EditHabitFragment.HABIT_ID_KEY to habit.id,
            EditHabitFragment.HABIT_HOUR_KEY to habit.alarmTimeHours,
            EditHabitFragment.HABIT_MIN_KEY to habit.alarmTimeMinutes,
            EditHabitFragment.HABIT_TYPE_KEY to habit.alarmType
        )
        //navigate to our editHabitFragment
        navController?.navigate(R.id.action_nav_home_to_editHabitFragment, bundle)
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        if (viewHolder is HabitItemViewHolder) {
            if (direction == ItemTouchHelper.LEFT) {

                val habitToDelete: MyHabit = mAdapter.currentList.get(viewHolder.adapterPosition)
                val habitName: String = habitToDelete.habitName

                //Create a backup of the deleted item in case user wants to undo delete

                //Remove the item from RecyclerView
                mHomeViewModel.delete(habitToDelete)
                val snackbar = Snackbar.make(
                    this.requireView(),
                    "$habitName removed from list!", Snackbar.LENGTH_LONG
                )
                snackbar.setAction("UNDO") { // undo is selected, restore the deleted item
                    mHomeViewModel.insert(habitToDelete)
                }
                snackbar.show()
            }
        }
    }

}