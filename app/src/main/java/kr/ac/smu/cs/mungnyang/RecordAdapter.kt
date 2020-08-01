package kr.ac.smu.cs.mungnyang

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.record_item_view.view.*
import kotlinx.android.synthetic.main.today_item_view.view.*

class RecordAdapter(private var recordList:MutableList<Record>,context : Context) : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = recordList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        recordList[position].let{ item ->
            with(holder) {
                if(item.name!=null) {  //main에서 사진 띄우기


                    read_name.setText(item.name)
                    read_text.setText(item.rec)
                    val byteArray=item.image
                    val picture= BitmapFactory.decodeByteArray(byteArray,0,byteArray!!.size)
                    read_image.setImageBitmap(picture)



                }else{
                    read_name.setText("aaaaa")
                    read_text.setText("bbbb")
                }
            }
        }

        //각 item에 클릭 이벤트 붙이기
        holder.itemView.setOnClickListener { view->

        }



    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val read_text: TextView =view.rec_read_text
        val read_name:TextView=view.rec_read_name
        val read_image: ImageView =view.rec_read_image
    }
    fun remove(position:Int){
        recordList.removeAt(position)
        notifyItemRemoved(position)
    }
}