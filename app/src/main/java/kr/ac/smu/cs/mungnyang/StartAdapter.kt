package kr.ac.smu.cs.mungnyang

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item_view.view.*

class StartAdapter(private var userList:MutableList<User>,context : Context) : RecyclerView.Adapter<StartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item_view, parent, false)
        //이미지 위에 어둡게 씌우는 것
        view.user_imageView.setColorFilter(Color.parseColor("#882C2C2C"))
        return ViewHolder(view)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        userList[position].let{ item ->
            with(holder) {
                if(item.image!=null) {  //main에서 사진 띄우기
                    //imageView.setImageBitmap(item.image)

                    //val thumbnail= item.thumbnail
                    /*val url: URL =URL(thumbnail)
                    val conn:HttpURLConnection=url.openConnection() as HttpURLConnection
                    conn.setDoInput(true)
                    conn.connect()
                    val iss:InputStream=conn.inputStream
                    //val iss: InputStream =url.openConnection().inputStream

                    val picture= BitmapFactory.decodeStream(iss)*/
                    //goal_imageView.setImageBitmap(picture)
                    //Picasso.get().load(thumbnail).into(goal_imageView)
                    val byteArray=item.image
                    val picture= BitmapFactory.decodeByteArray(byteArray,0,byteArray!!.size)
                    user_imageView.setImageBitmap(picture)
                    //user_imageView.setImageBitmap(item.image)
                    //item 위에 글자쓰기
                    user_text.setText(item.name)
                    user_text2.setText(item.dday.toString())


                }
            }
        }

        //각 item에 클릭 이벤트 붙이기
        holder.itemView.setOnClickListener { view->
            var intent= Intent(view.context, HomeActivity::class.java)
            //intent.putExtra("goal_name",goalList[position].title)
            intent.putExtra("user_id",userList[position].id)
            //intent.putExtra("goal_dday",goalList[position].dayscount)
            //TokenTon.setpostId(goalList[position].id)
            view.context.startActivity(intent)
        }
        


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val user_imageView: ImageView =view.user_imageView
        val user_text: TextView =view.user_imageView_text
        val user_text2: TextView =view.user_imageView_text2
    }
}