package com.example.mycalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
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
    private lateinit var daysButtons: Array<Button>
    private lateinit var notesTextView: TextView
    private lateinit var notesEditText: EditText
    private lateinit var yearViewButton: Button
    private lateinit var saveNoteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        daysButtons = Array(42) {
            val buttonId = "day_btn_" + (it + 1).toString().padStart(2, '0')
            val buttonIdIntCode = R.id::class.java.getDeclaredField(buttonId).getInt(R.id::class)
            findViewById(buttonIdIntCode)
        }
        notesTextView = findViewById(R.id.notes_text_view)
        notesEditText = findViewById(R.id.notes_edit_text)
        yearViewButton = findViewById(R.id.year_view_btn)
        saveNoteButton = findViewById(R.id.save_note_btn)
    }

    private fun setupOnSwipeListeners() {
        val allElements: MutableList<View> = mutableListOf(
            findViewById(R.id.root_layout),
            findViewById(R.id.top_layout),
            findViewById(R.id.calendar_layout),
            findViewById(R.id.monday_label),
            findViewById(R.id.tuesday_label),
            findViewById(R.id.wednesday_label),
            findViewById(R.id.thursday_label),
            findViewById(R.id.friday_label),
            findViewById(R.id.saturday_label),
            findViewById(R.id.sunday_label),
            findViewById(R.id.notes_text_view),
            monthSpinner,
            yearSpinner,
            notesTextView,
            yearViewButton,
            saveNoteButton)
        allElements.addAll(daysButtons)
        allElements.forEach { it.setOnTouchListener(onSwipeListener) }
    }

    private fun setupOnItemSelectedListeners() {
        monthSpinner.onItemSelectedListener = onItemSelectedListener
        yearSpinner.onItemSelectedListener = onItemSelectedListener
    }

    private fun setupOnClickListeners() {
        daysButtons.forEach { btn -> btn.setOnClickListener { notesTextView.text = btn.text } }
        notesTextView.setOnClickListener {
            notesTextView.visibility = View.GONE
            notesEditText.visibility = View.VISIBLE
            yearViewButton.visibility = View.GONE
            saveNoteButton.visibility = View.VISIBLE
        }
        yearViewButton.setOnClickListener {
            val intent = Intent(this@MonthActivity, YearActivity::class.java)
            startActivity(intent)
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0, Color.TRANSPARENT)
        }
        saveNoteButton.setOnClickListener {
            notesTextView.text = notesEditText.text
            notesEditText.visibility = View.GONE
            notesTextView.visibility = View.VISIBLE
            saveNoteButton.visibility = View.GONE
            yearViewButton.visibility = View.VISIBLE
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
        val isLeapSelectedYear = firstOfSelectedMonth.isLeapYear

        val selectedMonth = Month.of(selectedMonthValue)
        val previousMonth = Month.of(if (selectedMonthValue > 1) selectedMonthValue - 1 else 12)

        val selectedMonthLength = selectedMonth.length(isLeapSelectedYear)
        val previousMonthLength = previousMonth.length(isLeapSelectedYear)

        val monthStartIndex = firstOfSelectedMonth.dayOfWeek.value - 1
        val monthEndIndex = monthStartIndex + selectedMonthLength

        var dateToSet = previousMonthLength - monthStartIndex + 1
        for (i in 0..<monthStartIndex) {
            val textSize = 16f
            val textColor = R.color.grey_day_out
            val backgroundColor = if (i in holidayIndexes) R.color.red_holiday_out else R.color.blue_weekday_out
            setButtonParams(daysButtons[i], dateToSet++, textSize, textColor, backgroundColor)
        }
        dateToSet = 1
        for (i in monthStartIndex..<monthEndIndex) {
            val textSize = 20f
            val textColor = R.color.white
            val backgroundColor = if (i in holidayIndexes) R.color.red_holiday else R.color.blue_weekday
            setButtonParams(daysButtons[i], dateToSet++, textSize, textColor, backgroundColor)
        }
        dateToSet = 1
        for (i in monthEndIndex ..<42) {
            val textSize = 16f
            val textColor = R.color.grey_day_out
            val backgroundColor = if (i in holidayIndexes) R.color.red_holiday_out else R.color.blue_weekday_out
            setButtonParams(daysButtons[i], dateToSet++, textSize, textColor, backgroundColor)
        }

        if (selectedYear == today.year && selectedMonth == today.month) {
            val todayIndex = monthStartIndex - 1 + today.dayOfMonth
            val drawableId = if (todayIndex in holidayIndexes) R.drawable.today_holiday else R.drawable.today_weekday
            daysButtons[todayIndex].background = ContextCompat.getDrawable(this@MonthActivity, drawableId)
        }
    }

    private fun setButtonParams(button: Button,
                                dateToSet: Int,
                                textSize: Float,
                                textColor: Int,
                                backgroundColor: Int) {
        button.text = "$dateToSet"
        button.textSize = textSize
        button.setTextColor(resources.getColor(textColor, null))
        button.setBackgroundColor(resources.getColor(backgroundColor, null))
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
