package com.pearl.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.pearl.test5.R
import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.Global
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : BaseClass() {

    lateinit var time: EditText
    lateinit var date: EditText
    private val myCalendar = Calendar.getInstance()
    private val currentHour = myCalendar.get(Calendar.HOUR_OF_DAY)
    private val currentMinute = myCalendar.get(Calendar.MINUTE)

/*    lateinit var cTimer : CountDownTimer
    private var am_pm: String? = ""*/

    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView

    override fun setLayoutXml() {
        setContentView(R.layout.activity_form)

    }

    override fun initializeViews() {
        time= findViewById(R.id.timeFET)
        date= findViewById(R.id.dateFET)

        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)
        apptitle.text = "Data Sheet"
    }

    @SuppressLint("SetTextI18n")
    override fun initializeClickListners() {

        ivback.setOnClickListener {
            onBackPressed()
        }
       /* time?.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                am_pm = if (hour < 12) "AM" else "PM"
                var selectedHour : Int
                if (hour > 12){
                    selectedHour = hour - 12
                }else{
                    if(hour == 0){
                        selectedHour = 12

                    }else {
                        selectedHour = hour
                    }
                }
                cal.set(Calendar.HOUR_OF_DAY, selectedHour)
                cal.set(Calendar.MINUTE, minute)
                if (time != null) {
                    if (::cTimer.isInitialized) {
                        cTimer.cancel()
                    }
                    time?.text = SimpleDateFormat("HH:mm:ss").format(cal.time)+" "+am_pm+""
                }
            }
            TimePickerDialog(this@FormActivity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()

        }*/

        time.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this@FormActivity,
                TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minutes ->
                    // Code to be executed when the user sets the time
                    time.text = Editable.Factory.getInstance().newEditable("$hourOfDay:$minutes")
            /*        if (hourOfDay >= 12) {
                        amPm = "PM";
                    } else {
                        amPm = "AM";
                    }
                    time.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm)*/
                },
                currentHour,
                currentMinute,
                false
            )
            timePickerDialog.show()

        }


        date.setOnClickListener {
            showDatePicker()
        }
    }

    override fun initializeInputs() {

    }

    override fun initializeLabels() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Global.language(this,resources)
        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()

    }


    private fun showDatePicker() {
        val date = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateLabel()
        }

        val datePickerDialog = DatePickerDialog(
            this@FormActivity,
            R.style.MyDatePickerDialogTheme, // use your custom theme here
            date,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )

        // Set the maximum and minimum date for the DatePickerDialog
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.datePicker.minDate = getMinimumDate()

        datePickerDialog.show()
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        date.setText(dateFormat.format(myCalendar.time))
    }

    private fun getMinimumDate(): Long {
        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.add(Calendar.YEAR, -5) // Set 100 years ago from now
        return minDateCalendar.timeInMillis
    }
}