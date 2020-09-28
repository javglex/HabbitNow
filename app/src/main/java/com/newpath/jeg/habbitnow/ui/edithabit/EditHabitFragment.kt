package com.newpath.jeg.habbitnow.ui.edithabit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.databinding.FragmentEditHabitBinding
import com.newpath.jeg.habbitnow.models.MyHabit

/**
 * Used to create new habits or edit existing ones
 */
class EditHabitFragment : Fragment() {

    private lateinit var editHabitViewModel: EditHabitViewModel
    private val TAG: String = "EditHabitFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //using bundle because we're not always receiving arguments (safe args)
        //for example, if arguments are empty, it means we are creating a new habit rather than editing
        val habitName = arguments?.getString("HABIT_NAME")
        val habitId = arguments?.getLong("HABIT_ID")

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentEditHabitBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_habit, container, false)

        editHabitViewModel = ViewModelProvider(this).get(EditHabitViewModel::class.java)

        if (habitId!=null){
            editHabitViewModel.mHabitId = habitId
            if (habitName != null) {
                editHabitViewModel.mHabitName = habitName
            }
        }

        binding.lifecycleOwner = this
        binding.editHabitViewModel = editHabitViewModel
        binding.etHabitName.setText(editHabitViewModel.mHabitName)
        binding.etHabitName.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0==null || p0.isEmpty())
                    return
                editHabitViewModel.mHabitName = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.btnSubmit.setOnClickListener {
            findNavController().popBackStack()
            editHabitViewModel.submitHabit()
        }

        return binding.root
    }



}