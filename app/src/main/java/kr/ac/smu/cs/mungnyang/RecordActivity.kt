package kr.ac.smu.cs.mungnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        val userid=intent.getIntExtra("user_id",0)
        Thread.sleep(300)
        rec_new.setOnClickListener {
            val intent= Intent(this,AddRecordActivity::class.java)
            intent.putExtra("user_id",userid)
            startActivity(intent)
        }
        rec_read.setOnClickListener {
            val intent=Intent(this,ReadRecordActivity::class.java)
            intent.putExtra("user_id",userid)
            startActivity(intent)
        }
    }
}
