package com.example.mycalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridLayout
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.Month


@SuppressLint("NewApi", "ClickableViewAccessibility")
class MonthActivity : ComponentActivity() {

    private var today = LocalDate.now()

    private var onItemSelectedListener = getOnItemSelectedListener { setDayButtonsAttributes() }
    private var onSwipeListener = getOnSwipeListener({ doOnSwipeLeft() }, { doOnSwipeRight() })

    private lateinit var monthSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var calendarLayout: GridLayout
    private lateinit var daysButtons: Array<Button>
    private lateinit var yearViewButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_month)

        initAllElements()

        setupOnSwipeListeners()
        setupOnItemSelectedListeners()
        setupOnClickListeners()

        setupMonthSpinner()
        setupYearSpinner()

        setDayButtonsAttributes()
    }

    private fun initAllElements() {
        monthSpinner = findViewById(R.id.month_spinner)
        yearSpinner = findViewById(R.id.year_spinner)
        calendarLayout = findViewById(R.id.calendar_layout)
        daysButtons = Array(42) { layoutInflater.inflate(R.layout.day_button, calendarLayout, false) as Button }
        daysButtons.forEach { calendarLayout.addView(it) }
        yearViewButton = findViewById(R.id.year_view_btn)
    }

    private fun setupOnSwipeListeners() {
        val allElements: MutableList<View> = mutableListOf(
            findViewById(R.id.root_layout),
            findViewById(R.id.top_layout),
            findViewById(R.id.bottom_layout),
            findViewById(R.id.monday_label),
            findViewById(R.id.tuesday_label),
            findViewById(R.id.wednesday_label),
            findViewById(R.id.thursday_label),
            findViewById(R.id.friday_label),
            findViewById(R.id.saturday_label),
            findViewById(R.id.sunday_label),
            monthSpinner,
            yearSpinner,
            calendarLayout,
            yearViewButton
        )
        allElements.addAll(daysButtons)
        allElements.forEach { it.setOnTouchListener(onSwipeListener) }
    }

    private fun setupOnItemSelectedListeners() {
        monthSpinner.onItemSelectedListener = onItemSelectedListener
        yearSpinner.onItemSelectedListener = onItemSelectedListener
    }

    private fun setupOnClickListeners() {
        yearViewButton.setOnClickListener {
            val intent = Intent(this@MonthActivity, YearActivity::class.java)
            startActivity(intent)
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0, Color.TRANSPARENT)
        }
    }

    private fun setupMonthSpinner() {
        val months = resources.getStringArray(R.array.months)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        monthSpinner.adapter = adapter
        setSelectedMonthValue(today.monthValue)
    }

    private fun setupYearSpinner() {
        val years = Array(201) { 1900 + it }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        yearSpinner.adapter = adapter
        setSelectedYear(today.year)
        shortenSpinnerPopup(yearSpinner)
    }

    private fun setDayButtonsAttributes() {
        val selectedMonthValue = getSelectedMonthValue()
        val selectedYear = getSelectedYear()

        val firstOfSelectedMonth = LocalDate.of(selectedYear, selectedMonthValue, 1)
        val selectedMonth = Month.of(selectedMonthValue)
        val selectedMonthLength = selectedMonth.length(firstOfSelectedMonth.isLeapYear)

        val monthStartIndex = firstOfSelectedMonth.dayOfWeek.value - 1
        val monthEndIndex = monthStartIndex + selectedMonthLength

        var dateToSet = 1
        for (i in monthStartIndex..<monthEndIndex) {
            val textColor = if (i in holidayIndexes) R.color.green_day_holiday else R.color.white
            setButtonParams(daysButtons[i], dateToSet++, textColor)
        }

        if (selectedYear == today.year && selectedMonth == today.month) {
            val todayIndex = monthStartIndex - 1 + today.dayOfMonth
            val todayCircle = ContextCompat.getDrawable(this@MonthActivity, R.drawable.today_circle)
            daysButtons[todayIndex].background = todayCircle
        }
    }

    private fun setButtonParams(button: Button, dateToSet: Int, textColor: Int) {
        button.text = "$dateToSet"
        button.setTextColor(resources.getColor(textColor, null))
    }

    private fun doOnSwipeLeft() {
        val selectedMonthValue = getSelectedMonthValue()
        if (selectedMonthValue < 12) {
            setSelectedMonthValue(selectedMonthValue + 1)
        } else {
            setSelectedMonthValue(1)
            val selectedYear = getSelectedYear()
            setSelectedYear(if (selectedYear < 2100) selectedYear + 1 else 2100)
        }
        setDayButtonsAttributes()
    }

    private fun doOnSwipeRight() {
        val selectedMonthValue = getSelectedMonthValue()
        if (selectedMonthValue > 1) {
            setSelectedMonthValue(selectedMonthValue - 1)
        } else {
            setSelectedMonthValue(12)
            val selectedYear = getSelectedYear()
            setSelectedYear(if (selectedYear > 1900) selectedYear - 1 else 1900)
        }
        setDayButtonsAttributes()
    }

    private fun getSelectedMonthValue() = monthSpinner.selectedItemPosition + 1
    private fun getSelectedYear() = yearSpinner.selectedItemPosition + 1900

    private fun setSelectedMonthValue(monthValue: Int) = monthSpinner.setSelection(monthValue - 1)
    private fun setSelectedYear(year: Int) = yearSpinner.setSelection(year - 1900)

}
