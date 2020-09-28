package kr.ac.smu.cs.mungnyang

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.record_item_view.view.*
import kotlinx.android.synthetic.main.tuto_item_view.view.*

class TutoAdapter(private var tutoList:Array<Int>,context : Context) : RecyclerView.Adapter<TutoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tuto_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = tutoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tutoList[position].let{ item ->
            with(holder) {

                tuto_image.setImageResource(item)

            }
        }

        //각 item에 클릭 이벤트 붙이기
        holder.itemView.setOnClickListener { view->

        }






    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tuto_image: ImageView =view.tutoImage
        //val read_check=view.rec_check_button
    }

}