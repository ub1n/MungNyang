package kr.ac.smu.cs.mungnyang

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.back_item_view.view.*
import kotlinx.android.synthetic.main.cal_item_view.view.*
import kotlinx.android.synthetic.main.content_home.view.*
import kotlinx.android.synthetic.main.today_item_view.view.*


class BackAdapter(private var backList:Array<Int>,context : Context,num:Int,id:Int) : RecyclerView.Adapter<BackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.back_item_view, parent, false)

        return ViewHolder(view)
    }
    val a=num
    val b=id
    override fun getItemCount() = backList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        backList[position].let{ item ->
            with(holder) {

                back_image.setImageResource(item)

            }
        }

        //각 item에 클릭 이벤트 붙이기
        holder.itemView.setOnClickListener { view->
           if(a==0){
               Com.setbackimage(backList[position])


            }else{
               Com.setmaruimage(backList[position])
            }
            var intent= Intent(view.context, HomeActivity::class.java)
            intent.putExtra("user_id",b)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            //intent.putExtra("goal_dday",goalList[position].dayscount)
            //TokenTon.setpostId(goalList[position].id)
            view.context.startActivity(intent)


        }



    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val back_image=view.BackImageView
        val changeimage=view.backimage
    }

}