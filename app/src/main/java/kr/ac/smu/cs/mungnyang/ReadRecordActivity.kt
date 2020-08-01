package kr.ac.smu.cs.mungnyang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_read_record.*
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_today.*

class ReadRecordActivity : AppCompatActivity() {
    lateinit var mAdapter:RecordAdapter
    private var recordDatabase:RecordDatabase?=null
    private var recordList=mutableListOf<Record>()
    private var record=Record()
    var i=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_record)

        val userid=intent.getIntExtra("user_id",0)
        Toast.makeText(this,"$userid",Toast.LENGTH_LONG).show()
        recordDatabase=RecordDatabase.getInstance(this)

        mAdapter= RecordAdapter(recordList,applicationContext)
        val r = Runnable {   //recyclerview와 android room 결합
            try {
                recordList = recordDatabase?.recordDao?.getRecord(userid)!!

                mAdapter = RecordAdapter(recordList, applicationContext)
                mAdapter.notifyDataSetChanged()

                readRecyclerView.adapter = mAdapter
                readRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


                readRecyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }

        val thread = Thread(r)
        thread.start()
    }
}
