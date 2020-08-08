package kr.ac.smu.cs.mungnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_today.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TodayActivity : AppCompatActivity() {
    private var dayDatabase:DayDatabase?=null
    private var dayList=mutableListOf<Day>()
    private var day=Day()

    lateinit var mAdapter:DayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today)

        val userid=intent.getIntExtra("user_id",0)
        var addintent: Intent?=null
        val id=intent.getIntExtra("id",0)

        dayDatabase=DayDatabase.getInstance(this)




        //for(i in 0..(dayList.size-1))




        mAdapter= DayAdapter(dayList,applicationContext)
        val r = Runnable {   //recyclerview와 android room 결합
            try {
                dayList = dayDatabase?.dayDao?.getDay(userid)!!
                mAdapter = DayAdapter(dayList, applicationContext)
                mAdapter.notifyDataSetChanged()

                CalRecyclerView.adapter = mAdapter
                CalRecyclerView.layoutManager = LinearLayoutManager(this)

                mainRecyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }

        val thread = Thread(r)
        thread.start()

        val database: DayDatabase = DayDatabase.getInstance(applicationContext)
        val dayDao: DayDao=database.dayDao
        val memoList=ArrayList<Day>()
        Thread{memoList.addAll(dayDao.getDay(userid))}.start()
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

                Thread { dayDatabase?.dayDao?.delete(memoList[position].id) }.start()

                val adapter = CalRecyclerView.adapter as DayAdapter
                adapter.remove(position)
                //CalRecyclerView.adapter?.notifyItemRemoved(position)



                //memoList.removeAt(position)
                //var intent=Intent(applicationContext,MainActivity::class.java)
                // startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))


            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(CalRecyclerView)


        val now=System.currentTimeMillis()
        val mdate= Date(now)
        val simpledate= SimpleDateFormat("dd")
        val getTime=simpledate.format(mdate)


        cal_add_button.setOnClickListener{
             day.userid=userid
            day.todo=cal_editText.text.toString()
            day.date=getTime

            Thread { dayDatabase?.dayDao?.insert(day) }.start()
            mAdapter.notifyDataSetChanged()
            val intent=Intent(this,TodayActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra("user_id",userid)
            startActivity(intent)
            finish()
            

        }


    }
}
