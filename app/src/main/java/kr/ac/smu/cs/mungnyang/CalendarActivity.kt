package kr.ac.smu.cs.mungnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calendar.*

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)


        calendarView.setOnDateChangeListener{ calendarView: CalendarView, year: Int, month: Int, day: Int ->
            Toast.makeText(this,"$year"+"year"+"$month"+"month"+"$day"+"day",Toast.LENGTH_LONG).show()
            val intent= Intent(this,Calendar_SelectedActivity::class.java)
            intent.putExtra("year",year)
            intent.putExtra("month",month+1)
            intent.putExtra("day",day)
            startActivity(intent)
        }
    }
}
