package com.example.mycalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity

// just a template yet
@SuppressLint("NewApi")
class YearActivity : ComponentActivity() {

    private lateinit var yearCalendarLayout: GridLayout
    private lateinit var monthsWithNames: Array<LinearLayout>
    private lateinit var dayCells: Array<Array<TextView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_year)

        yearCalendarLayout = findViewById(R.id.year_calendar_layout)
        monthsWithNames = Array(12) { layoutInflater.inflate(R.layout.month_year_cell, yearCalendarLayout, false) as LinearLayout }
        monthsWithNames.forEach {
            yearCalendarLayout.addView(it)
            it.setOnClickListener { goToMonthPage() }
        }
        dayCells = Array(12) {
            val monthCell = monthsWithNames[it].getChildAt(1) as GridLayout
            Array(42) { layoutInflater.inflate(R.layout.day_year_cell, monthCell, false) as TextView }
        }
        dayCells.forEachIndexed { index, dayCellsForMonth ->
            val monthCell = monthsWithNames[index].getChildAt(1) as GridLayout
            dayCellsForMonth.forEach {
                monthCell.addView(it)
                it.setOnClickListener { goToMonthPage() }
            }
        }
    }

    private fun goToMonthPage() {
        val intent = Intent(this@YearActivity, MonthActivity::class.java)
        startActivity(intent)

        // todo consider this
//        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0, Color.TRANSPARENT)
        // and save month activity state (bundle)
    }
}