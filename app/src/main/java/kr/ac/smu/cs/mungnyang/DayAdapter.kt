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
import java.text.SimpleDateFormat
import java.util.*

class DayAdapter(private var dayList:MutableList<Day>,context : Context) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    val now=System.currentTimeMillis()
    val mdate= Date(now)
    val simpledate= SimpleDateFormat("dd")
    val getTime=simpledate.format(mdate)
    var cnt=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.today_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dayList[position].let{ item ->
            with(holder) {
                if(item.todo!=null) {  //main에서 사진 띄우기
                    val dayDatabase=DayDatabase.getInstance(holder.itemView.context)


                    day_text.setText(item.todo)
                    if(getTime!=item.date){
                        day_check.isChecked=false
                        item.done=false
                        item.date=getTime
                        item.oneday=false
                        Thread{dayDatabase.dayDao.update(item)}.start()
                    }else{
                    day_check.isChecked=item.done}
                    if(item.done==true)
                        cnt=cnt+1



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
            if(dayList[position].done==true){
                cnt=cnt+1
                if(cnt==dayList.size){
                    if(dayList[position].oneday==false){
                        if(Com.mlevel>19){
                            Toast.makeText(view.context,"이미 최대 스탬프 입니다!",Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(view.context,"스탬프 획득!",Toast.LENGTH_LONG).show()
                            Com.levelUp()
                        //if(level>12) -> Toast.makeText(view.context,"already full level",Toast.LENGTH_LONG).show() else-> level++
                             dayList[position].oneday=true
                            val intent=Intent(view.context,HomeActivity::class.java)
                            intent.putExtra("user_id",dayList[position].userid)
                            intent.putExtra("level_check",1)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            view.context.startActivity(intent)
                        }}
                    else {
                        Toast.makeText(view.context, "이미 오늘 스탬프를 받았어요!", Toast.LENGTH_LONG).show()
                    }
                }
            }
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