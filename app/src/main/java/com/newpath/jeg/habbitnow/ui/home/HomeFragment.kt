package com.newpath.jeg.habbitnow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.databinding.FragmentHomeBinding
import com.newpath.jeg.habbitnow.ui.adapters.HabitsAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.lifecycleOwner = this
        binding.homeFragmentViewModel = homeViewModel

        val adapter = HabitsAdapter()
        binding.rvHabitList.adapter = adapter


        homeViewModel.allHabits.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })



        return binding.root
    }
}