package kr.ac.smu.cs.mungnyang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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