package com.example.mycalendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListPopupWindow
import android.widget.Spinner
import androidx.activity.ComponentActivity
import java.lang.reflect.Field
import java.time.LocalDate
import java.time.Month

@SuppressLint("NewApi", "DiscouragedPrivateApi", "ClickableViewAccessibility")
class MonthActivity : ComponentActivity() {

    private val holidayIndexes = intArrayOf(5, 6, 12, 13, 19, 20, 26, 27, 33, 34, 40, 41)
    private val onSpinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long) {
            setDayButtonsAttributes()
        }
    }

    private var today = LocalDate.now()

    private lateinit var monthSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var daysButtons: Array<Button>
    private lateinit var yearViewButton: Button
    private lateinit var writeNoteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_month)

        setupMonthSpinner()
        setupYearSpinner()
        setupDayButtons()
        setupYearViewButton()
        setupWriteNoteButton()

        setOnSwipeListenerForPassiveElements()

        setDayButtonsAttributes()
    }

    private fun setupMonthSpinner() {
        monthSpinner = findViewById(R.id.month_spinner)
        val months = resources.getStringArray(R.array.months)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        monthSpinner.adapter = adapter
        monthSpinner.setOnTouchListener(getOnSwipeListener(getContext()))
        monthSpinner.onItemSelectedListener = onSpinnerItemSelectedListener
        setSelectedMonthValue(today.monthValue)
    }

    private fun setupYearSpinner() {
        yearSpinner = findViewById(R.id.year_spinner)
        val years = Array(201) { 1900 + it }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        yearSpinner.adapter = adapter
        yearSpinner.setOnTouchListener(getOnSwipeListener(getContext()))
        yearSpinner.onItemSelectedListener = onSpinnerItemSelectedListener
        setSelectedYear(today.year)
        shortenSpinnerPopup(yearSpinner)
    }

    private fun setupDayButtons() {
        daysButtons = Array(42) {
            val buttonId = "day_btn_" + (it + 1).toString().padStart(2, '0')
            val buttonIdIntCode = R.id::class.java.getDeclaredField(buttonId).getInt(R.id::class)
            findViewById(buttonIdIntCode)
        }
        daysButtons.forEach {
            it.setOnTouchListener(getOnSwipeListener(getContext()))
            it.setOnClickListener { doOnButtonClick() }
        }
    }

    private fun setupYearViewButton() {
        yearViewButton = findViewById(R.id.year_view_btn)
        yearViewButton.setOnTouchListener(getOnSwipeListener(getContext()))
        yearViewButton.setOnClickListener {
            val intent = Intent(this@MonthActivity, YearActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupWriteNoteButton() {
        writeNoteButton = findViewById(R.id.write_note_btn)
        writeNoteButton.setOnTouchListener(getOnSwipeListener(getContext()))
        writeNoteButton.setOnClickListener { doOnButtonClick() }
    }

    private fun setOnSwipeListenerForPassiveElements() {
        val passiveElements: Array<View> = arrayOf(
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
            findViewById(R.id.notes_area),
            findViewById(R.id.bottom_layout))
        passiveElements.forEach { it.setOnTouchListener(getOnSwipeListener(getContext())) }
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

    private fun doOnButtonClick() {
        writeNoteButton.text = if (writeNoteButton.text != "Clicked") "Clicked" else "Write note"
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

    private fun shortenSpinnerPopup(spinner: Spinner) {
        try {
            val popup: Field = Spinner::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val popupWindow: ListPopupWindow = popup.get(spinner) as ListPopupWindow
            popupWindow.height = 720
        } catch (_: NoClassDefFoundError) {
        } catch (_: ClassCastException) {
        } catch (_: NoSuchFieldException) {
        } catch (_: IllegalAccessException) {
        }
    }

    private fun getSelectedMonthValue() = monthSpinner.selectedItemPosition + 1
    private fun getSelectedYear() = yearSpinner.selectedItemPosition + 1900

    private fun setSelectedMonthValue(monthValue: Int) = monthSpinner.setSelection(monthValue - 1)
    private fun setSelectedYear(year: Int) = yearSpinner.setSelection(year - 1900)

    private fun getOnSwipeListener(ctx: Context) = object: OnSwipeTouchListener(ctx) {
        override fun onSwipeLeft() = doOnSwipeLeft()
        override fun onSwipeRight() = doOnSwipeRight()
    }
    
    private fun getContext() = this@MonthActivity
}
