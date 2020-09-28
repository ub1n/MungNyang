package kr.ac.smu.cs.mungnyang

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
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
                if(getItemCount()!=0) {  //main에서 사진 띄우기

                  //  read_check.performClick()
                    //Toast.makeText(holder.itemView.context,"${getItemCount()}",Toast.LENGTH_SHORT).show()
                    read_name.setText(item.name)
                    read_text.setText(item.rec)
                    if(item.image==null){
                        read_image.setImageResource(R.drawable.rec_pic)
                    }else {
                        val byteArray = item.image
                        val picture = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
                        read_image.setImageBitmap(picture)

                    }


                }else{
                    //read_check.performClick()
                }

            }
        }

        //각 item에 클릭 이벤트 붙이기
        holder.itemView.setOnClickListener { view->

        }
        holder.read_change.setOnClickListener { view->
            var intent= Intent(view.context, RecordChangeActivity::class.java)
            //intent.putExtra("goal_name",goalList[position].title)
            intent.putExtra("user_id",recordList[position].userid)
            intent.putExtra("name",recordList[position].name)
            intent.putExtra("rec",recordList[position].rec)
            intent.putExtra("id",recordList[position].id)
            intent.putExtra("image",recordList[position].image)


            //intent.putExtra("goal_dday",goalList[position].dayscount)
            //TokenTon.setpostId(goalList[position].id)
            view.context.startActivity(intent)
        }

        holder.read_delete.setOnClickListener { view->
            try {
                if(recordList.size==-1){
                    Toast.makeText(view.context,"하나 남았을 떈 삭제할 수 없어요!",Toast.LENGTH_LONG).show()
                }else{
                val readDatabase = RecordDatabase.getInstance(view.context)

                  //  Toast.makeText(view.context,"${recordList[pos].name}",Toast.LENGTH_SHORT).show()
                //Thread { readDatabase.recordDao.delete(recordList[pos].id) }.start()    //왜안될까..
                  //  readDatabase.recordDao.delete(recordList[pos].id)
                /*remove(position)
                    var intent= Intent(view.context, ReadRecordActivity::class.java)
                    //intent.putExtra("goal_name",goalList[position].title)
                    intent.putExtra("user_id",recordList[pos].userid)
                    intent.putExtra("id",recordList[pos].id)
                    intent.putExtra("pos",position)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                   // Toast.makeText(view.context,"${pos}",Toast.LENGTH_LONG).show()
                    view.context.startActivity(intent)*/

                    Toast.makeText(view.context,"위아래로 스크롤 하면 저장한 일기를 볼 수 있고, 좌우로 스와이프하면 해당 일기가 지워져요!",Toast.LENGTH_LONG).show()
                }
            }catch(e:Exception){
                Toast.makeText(view.context,"오류!",Toast.LENGTH_LONG)
            }
        }



    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val read_text: TextView =view.rec_read_text
        val read_name:TextView=view.rec_read_name
        val read_image: ImageView =view.rec_read_image
        val read_delete=view.rec_delete_button
        val read_change=view.rec_change_button
        //val read_check=view.rec_check_button
    }
    fun remove(position:Int){
        recordList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(0,20)
    }
}