package com.newpath.jeg.habbitnow.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.databinding.FragmentHomeBinding
import com.newpath.jeg.habbitnow.ui.adapters.HabitsAdapter

class HomeFragment : Fragment() {

    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        mBinding = binding  //for use by onResume callback
        mHomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.lifecycleOwner = this
        binding.homeFragmentViewModel = mHomeViewModel

        val adapter = HabitsAdapter(mHomeViewModel)
        binding.rvHabitList.adapter = adapter

        mHomeViewModel.allHabits.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            mBinding?.rvHabitList.smoothScrollToPosition(0)
        }, 250)
    }
}