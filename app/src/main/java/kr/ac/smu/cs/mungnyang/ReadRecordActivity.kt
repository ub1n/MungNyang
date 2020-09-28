package kr.ac.smu.cs.mungnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        recordDatabase=RecordDatabase.getInstance(this)

        var size=-1
        mAdapter= RecordAdapter(recordList,applicationContext)
        val r = Runnable {   //recyclerview와 android room 결합
            try {
                recordList = recordDatabase?.recordDao?.getRecord(userid)!!
                if(recordList.size==0){
                    Handler(Looper.getMainLooper()).post{
                    Toast.makeText(this,"현재 등록된 일기가 없어요!",Toast.LENGTH_LONG).show()
                        var intent= Intent(this, RecordActivity::class.java)
                        //intent.putExtra("goal_name",goalList[position].title)
                        intent.putExtra("user_id",userid)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    this.startActivity(intent)}
                }
                Com.setSize(recordList.size)
                mAdapter = RecordAdapter(recordList, applicationContext)
                mAdapter.notifyDataSetChanged()
                readRecyclerView.adapter = mAdapter

                readRecyclerView.layoutManager = LinearLayoutManager(this)
                size=mAdapter.itemCount

                readRecyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
                //Toast.makeText(this,"현재 등록된 일기가 없어요!",Toast.LENGTH_LONG).show()

            }
        }

        val thread = Thread(r)
        thread.start()

        val database: RecordDatabase = RecordDatabase.getInstance(applicationContext)
        val recordDao: RecordDao=database.recordDao
        val memoList=ArrayList<Record>()
        Thread{memoList.addAll(recordDao.getRecord(userid))}.start()



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

                Thread { recordDatabase?.recordDao?.delete(memoList[position].id) }.start()

                val adapter = readRecyclerView.adapter as RecordAdapter
                adapter.remove(position)
                //CalRecyclerView.adapter?.notifyItemRemoved(position)
                var intent= Intent(viewHolder.itemView.context, ReadRecordActivity::class.java)
                //intent.putExtra("goal_name",goalList[position].title)
                intent.putExtra("user_id",userid)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                viewHolder.itemView.context.startActivity(intent)


                //memoList.removeAt(position)
                //var intent=Intent(applicationContext,MainActivity::class.java)
                // startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))


            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(readRecyclerView)
    }
}
