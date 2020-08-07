package kr.ac.smu.cs.mungnyang

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.today_item_view.view.*

class DayAdapter(private var dayList:MutableList<Day>,context : Context) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.today_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dayList[position].let{ item ->
            with(holder) {
                if(item.todo!=null) {  //main에서 사진 띄우기

                    day_text.setText(item.todo)
                    day_check.isChecked=item.done



                }
            }
        }

        //각 item에 클릭 이벤트 붙이기
        holder.itemView.setOnClickListener { view->

        }

        holder.day_check.setOnClickListener {view->
            /*var intent= Intent(view.context, TodayActivity::class.java)
            //intent.putExtra("goal_name",goalList[position].title)
            intent.putExtra("user_id",dayList[position].userid)


            intent.putExtra("id",dayList[position].id)
            //intent.putExtra("goal_dday",goalList[position].dayscount)
            //TokenTon.setpostId(goalList[position].id)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            view.context.startActivity(intent)
            Toast.makeText(view.context,"aaa",Toast.LENGTH_LONG).show()*/
            val dayDatabase=DayDatabase.getInstance(view.context)

            dayList[position].done=!dayList[position].done
            Thread{dayDatabase.dayDao.update(dayList[position])}.start()
        }



    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val day_text: TextView =view.cal_text
        val day_check  =view.checkBox
    }
    fun remove(position:Int){
        dayList.removeAt(position)
        notifyItemRemoved(position)
    }
}