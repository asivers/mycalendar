package com.example.mycalendar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

// just a template yet
class YearActivity : ComponentActivity() {

    private lateinit var backToMonthButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_year)

        backToMonthButton = findViewById(R.id.back_to_month_btn)

        backToMonthButton.setOnClickListener {
            val intent = Intent(this@YearActivity, MonthActivity::class.java)
            startActivity(intent)
        }
    }
}