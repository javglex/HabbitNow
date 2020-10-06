package com.newpath.jeg.habbitnow.ui.edithabit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.databinding.FragmentEditHabitBinding


/**
 * Used to create new habits or edit existing ones
 */
class EditHabitFragment : Fragment() {

    private lateinit var editHabitViewModel: EditHabitViewModel
    private val TAG: String = "EditHabitFragment"

    companion object{
        const val HABIT_NAME_KEY: String = "HABIT_NAME_KEY"
        const val HABIT_ID_KEY: String = "HABIT_ID_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //using bundle because we're not always receiving arguments (safe args)
        //for example, if arguments are empty, it means we are creating a new habit rather than editing
        val habitName = arguments?.getString(HABIT_NAME_KEY)
        val habitId = arguments?.getLong(HABIT_ID_KEY)

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentEditHabitBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_habit, container, false
        )

        editHabitViewModel = ViewModelProvider(this).get(EditHabitViewModel::class.java)

        if (habitId!=null){
            editHabitViewModel.mHabitId = habitId
            Log.d(TAG, "recognized id, editing habit..")
            if (habitName != null) {
                editHabitViewModel.mHabitName = habitName
            }
        } else Log.d(TAG, "seems like it's a new habit, creating..")

        binding.lifecycleOwner = this
        binding.editHabitViewModel = editHabitViewModel
        binding.etHabitName.setText(editHabitViewModel.mHabitName)
        binding.etHabitName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 == null || p0.isEmpty())
                    return
                editHabitViewModel.mHabitName = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_done, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_settings)?.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        when(id) {
            R.id.action_done -> {
                Log.d(TAG, "checkmark hit!")
                findNavController().popBackStack()
                editHabitViewModel.submitHabit()
            }
            else -> super.onOptionsItemSelected(item)
        }

        return true;

    }

}