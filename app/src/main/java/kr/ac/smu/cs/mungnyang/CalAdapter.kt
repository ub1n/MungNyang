package kr.ac.smu.cs.mungnyang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cal_item_view.view.*
import kotlinx.android.synthetic.main.today_item_view.view.*
import kotlinx.android.synthetic.main.today_item_view.view.cal_text

class CalAdapter(private var calList:MutableList<Cal>,context : Context) : RecyclerView.Adapter<CalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cal_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = calList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        calList[position].let{ item ->
            with(holder) {
                if(item.todo!=null) {  //main에서 사진 띄우기

                    cal_text.setText(item.caltext)
                    when(item.todo){
                        "산책"->cal_image.setImageResource(R.drawable.walk)
                        "목욕"->cal_image.setImageResource(R.drawable.shower)
                        "미용"->cal_image.setImageResource(R.drawable.beauty)
                        "약 복용"->cal_image.setImageResource(R.drawable.drug)
                        "기타"->cal_image.setImageResource(R.drawable.stamp)
                    }



                }
            }
        }

        //각 item에 클릭 이벤트 붙이기
        holder.itemView.setOnClickListener { view->

        }



    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cal_text: TextView =view.cal_text
        val cal_image=view.calimage
    }
    fun remove(position:Int){
        calList.removeAt(position)
        notifyItemRemoved(position)
    }
}