package kr.ac.smu.cs.mungnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.activity_calendar__selected.*
import kotlinx.android.synthetic.main.activity_calendar__selected.CalRecyclerView
import kotlinx.android.synthetic.main.activity_calendar__selected.cal_add_button
import kotlinx.android.synthetic.main.activity_calendar__selected.cal_editText
import kotlinx.android.synthetic.main.activity_calendar__selected.textView_cal
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_today.*

class Calendar_SelectedActivity : AppCompatActivity() {

    private var calDatabase:CalDatabase?=null
    private var calList=mutableListOf<Cal>()
    private var cal=Cal()
    lateinit var mAdapter:CalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar__selected)
        val year=intent.getIntExtra("year",0)
        val month=intent.getIntExtra("month",0)
        val day=intent.getIntExtra("day",0)

        textView_cal.setText("$year"+"년"+"$month"+"월"+"$day"+"일 할일")
        cal_todo_button.setOnClickListener {
            val listItems = arrayOf("산책", "미용", "목욕","약 복용","기타")
            //val listItems=arrayOf(R.drawable.back0,R.drawable.calander,R.drawable.beauty)
            val mBuilder = AlertDialog.Builder(this@Calendar_SelectedActivity)
            mBuilder.setTitle("할 일을 골라주세요")
            mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->

                cal.todo=listItems[i]
                dialogInterface.dismiss()
            }
            // Set the neutral/cancel button click listener
            mBuilder.setNeutralButton("Cancel") { dialog, which ->
                // Do something when click the neutral button
                dialog.cancel()
            }

            val mDialog = mBuilder.create()
            mDialog.show()
        }

        calDatabase=CalDatabase.getInstance(this)

        mAdapter= CalAdapter(calList,applicationContext)
        val r = Runnable {   //recyclerview와 android room 결합
            try {
                calList = calDatabase?.calDao?.getCal(year,month,day)!!
                mAdapter = CalAdapter(calList, applicationContext)
                mAdapter.notifyDataSetChanged()

                CalRecyclerView.adapter = mAdapter
                CalRecyclerView.layoutManager = LinearLayoutManager(this)

                CalRecyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }

        val thread = Thread(r)
        thread.start()

        val database: CalDatabase = CalDatabase.getInstance(applicationContext)
        val calDao: CalDao=database.calDao
        val memoList=ArrayList<Cal>()
        Thread{memoList.addAll(calDao.getCal(year,month,day))}.start()
        val simpleItemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //showToast("on Move")
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                // 삭제되는 아이템의 포지션을 가져온다
                val position = viewHolder.adapterPosition
                // 데이터의 해당 포지션을 삭제한다
                //showToast("on remove " + mList.remove(position))

                // 아답타에게 알린다
                Thread { calDatabase?.calDao?.delete(memoList[position].id) }.start()

                val adapter = CalRecyclerView.adapter as CalAdapter
                adapter.remove(position)
                //mRecyclerView.adapter?.notifyItemRemoved(position)



                //memoList.removeAt(position)
                //var intent=Intent(applicationContext,MainActivity::class.java)
                // startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))


            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(CalRecyclerView)

        cal_add_button.setOnClickListener{
            cal.year=year
            cal.month=month
            cal.day=day
            cal.caltext=cal_editText.text.toString()
            Thread { calDatabase?.calDao?.insert(cal) }.start()

            mAdapter.notifyDataSetChanged()
            val intent= Intent(this,Calendar_SelectedActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra("year",year)
            intent.putExtra("month",month)
            intent.putExtra("day",day)
            startActivity(intent)
            finish()


        }
    }
}
