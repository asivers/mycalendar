package com.example.mycalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.activity.ComponentActivity
import java.time.LocalDate

@SuppressLint("NewApi", "ClickableViewAccessibility")
class MonthActivity : ComponentActivity() {

    private lateinit var monthSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var daysButtons: Array<Button>
    private lateinit var yearViewButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mv_activity)

        setBackgroundGradient()

        initAllElements()

        setupOnSwipeListeners()
        setupOnItemSelectedListeners()
        setupOnClickListeners()

        setupMonthSpinner()
        setupYearSpinner()

        setDayButtonsAttributes()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        setSelectedMonthValue(intent?.extras?.getInt("monthValue") ?: LocalDate.now().monthValue)
        setSelectedYear(intent?.extras?.getInt("year") ?: LocalDate.now().year)
        setDayButtonsAttributes()
    }

    private fun setBackgroundGradient() {
        val gradientDrawable = GradientDrawable().apply {
            orientation = GradientDrawable.Orientation.TOP_BOTTOM
            gradientType = GradientDrawable.LINEAR_GRADIENT
            shape = GradientDrawable.RECTANGLE
        }
        val topColor = resources.getColor(R.color.mv_gradient_top, null)
        val bottomColor = resources.getColor(R.color.mv_gradient_bottom, null)
        gradientDrawable.setColors(
            intArrayOf(topColor, topColor, bottomColor, bottomColor),
            floatArrayOf(0f, 0.1f, 0.25f, 1f)
        )
        findViewById<LinearLayout>(R.id.root_layout).background = gradientDrawable
    }

    private fun initAllElements() {
        monthSpinner = findViewById(R.id.month_spinner)
        yearSpinner = findViewById(R.id.year_spinner)

        val calendarLayout: GridLayout = findViewById(R.id.calendar_layout)
        daysButtons = Array(42) {
            layoutInflater.inflate(R.layout.mv_day_button, calendarLayout, false) as Button
        }
        daysButtons.forEach { calendarLayout.addView(it) }

        yearViewButton = findViewById(R.id.mv_year_view_button)
    }

    private fun setupOnSwipeListeners() {
        val allElements: MutableList<View> = mutableListOf(
            findViewById(R.id.root_layout),
            findViewById(R.id.top_layout),
            findViewById(R.id.calendar_layout),
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
            yearViewButton
        )
        allElements.addAll(daysButtons)
        val onSwipeListener = getOnSwipeListener({ doOnSwipeLeft() }, { doOnSwipeRight() }, {}, {})
        allElements.forEach { it.setOnTouchListener(onSwipeListener) }
    }

    private fun setupOnItemSelectedListeners() {
        val onItemSelectedListener = getOnItemSelectedListener { setDayButtonsAttributes() }
        monthSpinner.onItemSelectedListener = onItemSelectedListener
        yearSpinner.onItemSelectedListener = onItemSelectedListener
    }

    private fun setupOnClickListeners() {
        yearViewButton.setOnClickListener { switchToYearView() }
    }

    private fun setupMonthSpinner() {
        val months = resources.getStringArray(R.array.months)
        val adapter = ArrayAdapter(this, R.layout.mv_month_spinner_header, months)
        adapter.setDropDownViewResource(R.layout.mv_month_spinner_item)
        monthSpinner.adapter = adapter
        setSelectedMonthValue(LocalDate.now().monthValue)
    }

    private fun setupYearSpinner() {
        val years = Array(201) { 1900 + it }
        val adapter = getYearSpinnerAdapter(this@MonthActivity, yearSpinner, years)
        adapter.setDropDownViewResource(R.layout.myv_year_spinner_item)
        yearSpinner.adapter = adapter
        setSelectedYear(LocalDate.now().year)
        shortenSpinnerPopup(yearSpinner, 1600)
    }

    private fun setDayButtonsAttributes() {
        val selectedMonthValue = getSelectedMonthValue()
        val selectedYear = getSelectedYear()
        val weekdayColor = resources.getColor(R.color.white, null)
        val holidayColor = resources.getColor(R.color.myv_green_day_holiday, null)
        val todayCircle = getTodayCircle()
        setDayElementsForMonth(
            daysButtons,
            selectedMonthValue,
            selectedYear,
            weekdayColor,
            holidayColor,
            todayCircle
        )
    }

    private fun getTodayCircle(): LayerDrawable {
        val todayCircle = getDrawable(R.drawable.mv_today_circle) as LayerDrawable
        val width = daysButtons[0].width - 3
        val height = daysButtons[0].width - 3
        if (width < height) {
            todayCircle.setLayerWidth(0, width)
            todayCircle.setLayerHeight(0, width)
            todayCircle.setLayerInsetTop(0, (height - width) / 2 + 3)
        } else {
            todayCircle.setLayerWidth(0, height)
            todayCircle.setLayerHeight(0, height)
            todayCircle.setLayerInsetTop(0, 3)
            if (LocalDate.now().dayOfMonth < 10) {
                todayCircle.setLayerInsetLeft(0, (width - height) / 2 + 3)
            } else {
                todayCircle.setLayerInsetLeft(0, (width - height) / 2)
            }
        }
        return todayCircle
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
    private fun setSelectedMonthValue(monthValue: Int) = monthSpinner.setSelection(monthValue - 1)

    private fun getSelectedYear() = yearSpinner.selectedItemPosition + 1900
    private fun setSelectedYear(year: Int) = yearSpinner.setSelection(year - 1900)

    private fun switchToYearView() {
        val intent = Intent(this@MonthActivity, YearActivity::class.java)
        intent.putExtra("year", getSelectedYear())
        intent.putExtra("monthValue", getSelectedMonthValue())
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivityIfNeeded(intent, 0)
    }
}
