package com.newpath.jeg.habbitnow.ui.edithabit

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.constants.AlarmConstants
import com.newpath.jeg.habbitnow.databinding.FragmentEditHabitBinding
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.ui.viewholders.HabitItemViewHolder
import com.newpath.jeg.habbitnow.utils.StringGenerator
import java.util.*


/**
 * Used to create new habits or edit existing ones
 */
class EditHabitFragment : Fragment() {

    private lateinit var mEditHabitViewModel: EditHabitViewModel
    private val TAG: String = "EditHabitFragment"

    companion object{
        const val HABIT_NAME_KEY: String = "HABIT_NAME_KEY"
        const val HABIT_ID_KEY: String = "HABIT_ID_KEY"
        const val HABIT_HOUR_KEY: String = "HABIT_HOUR_KEY"
        const val HABIT_MIN_KEY: String = "HABIT_MIN_KEY"
        const val HABIT_TYPE_KEY: String = "HABIT_TYPE_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //using bundle because we're not always receiving arguments (safe args)
        //for example, if arguments are empty, it means we are creating a new habit rather than editing
        val habitName:String? = arguments?.getString(HABIT_NAME_KEY,"New Habit")
        val habitId:Long? = arguments?.getLong(HABIT_ID_KEY)
        val habitHours:Int? = arguments?.getInt(HABIT_HOUR_KEY,0)
        val habitMinutes:Int? = arguments?.getInt(HABIT_MIN_KEY,0)
        val habitType:Int? = arguments?.getInt(HABIT_TYPE_KEY,0)

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentEditHabitBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_habit, container, false
        )

        mEditHabitViewModel = ViewModelProvider(this).get(EditHabitViewModel::class.java)

        if (habitId!=null){
            mEditHabitViewModel.mHabitId = habitId
            Log.d(TAG, "recognized id, editing habit..")
            mEditHabitViewModel.mHabitName = habitName!!
            mEditHabitViewModel.mHabitAlarmHour = habitHours!!
            mEditHabitViewModel.mHabitAlarmMin = habitMinutes!!
            mEditHabitViewModel.mHabitType = habitType!!

        } else Log.d(TAG, "seems like it's a new habit, creating..")

        binding.lifecycleOwner = this
        binding.editHabitViewModel = mEditHabitViewModel
        binding.etHabitName.setText(mEditHabitViewModel.mHabitName)

        if (habitHours == null || habitMinutes == null){
            binding.tvTime.text = StringGenerator.getTime(0,0)
        } else
            binding.tvTime.text = StringGenerator.getTime(habitHours,habitMinutes)


        binding.etHabitName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 == null || p0.isEmpty())
                    return
                mEditHabitViewModel.mHabitName = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.tvTime.setOnClickListener{
            openTimePicker(binding.tvTime)
        }

        binding.btnSelectSun.setOnCheckedChangeListener{
            _, isChecked ->
            if (isChecked){
                mEditHabitViewModel.setDay(0)
            }else {
                mEditHabitViewModel.unsetDay(0)
            }
        }

        binding.btnSelectMon.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked){
                mEditHabitViewModel.setDay(1)
            }else {
                mEditHabitViewModel.unsetDay(1)
            }
        }

        binding.btnSelectTue.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked){
                mEditHabitViewModel.setDay(2)
            }else {
                mEditHabitViewModel.unsetDay(2)
            }
        }

        binding.btnSelectWed.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked){
                mEditHabitViewModel.setDay(3)
            }else {
                mEditHabitViewModel.unsetDay(3)
            }
        }

        binding.btnSelectThu.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked){
                mEditHabitViewModel.setDay(4)
            }else {
                mEditHabitViewModel.unsetDay(4)
            }
        }

        binding.btnSelectFri.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked){
                mEditHabitViewModel.setDay(5)
            }else {
                mEditHabitViewModel.unsetDay(5)
            }
        }

        binding.btnSelectSat.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked){
                mEditHabitViewModel.setDay(6)
            }else {
                mEditHabitViewModel.unsetDay(6)
            }
        }

        binding.tbtnAlarm1.setOnCheckedChangeListener { view, isChecked ->

            binding.tbtnGentle1.isChecked = !isChecked
            mEditHabitViewModel.mHabitType = if (isChecked)
                AlarmConstants.AlarmType.ALARM else AlarmConstants.AlarmType.NOTIFICATION

        }

        binding.tbtnGentle1.setOnCheckedChangeListener{ view, isChecked ->
            binding.tbtnAlarm1.isChecked = !isChecked
            mEditHabitViewModel.mHabitType = if (!isChecked)
                AlarmConstants.AlarmType.ALARM else AlarmConstants.AlarmType.NOTIFICATION
        }

        binding.tbtnAlarm1.isChecked = habitType==AlarmConstants.AlarmType.ALARM
        binding.tbtnGentle1.isChecked = habitType==AlarmConstants.AlarmType.NOTIFICATION


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

        when(item.itemId) {
            R.id.action_done -> {
                Log.d(TAG, "checkmark hit!")
                findNavController().popBackStack()
                mEditHabitViewModel.submitHabit()
            }
            android.R.id.home -> {
                findNavController().popBackStack()
            }
            else -> super.onOptionsItemSelected(item)
        }

        return true;

    }

    private fun openTimePicker(tv: TextView){

        val cal: Calendar = Calendar.getInstance()
        val currHour: Int = cal.get(Calendar.HOUR_OF_DAY)
        val currMin: Int = cal.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            { view, hourOfDay, minute ->

                mEditHabitViewModel.mHabitAlarmHour = hourOfDay
                mEditHabitViewModel.mHabitAlarmMin = minute
                tv.text = StringGenerator.getTime(hourOfDay,minute)

            },
            currHour,
            currMin,
            false
        )
        timePickerDialog.show()
    }

}