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
import java.text.SimpleDateFormat
import java.util.*

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

                    if(item.cdday == "만난 날"){
                        var a=countdday(item.met_year,item.met_month,item.met_day)
                        user_text2.setText("D+"+"$a")
                    }else if(item.cdday=="생일"){
                        var a=countdday(item.birth_year,item.birth_month,item.birth_day)
                        user_text2.setText("D+"+"$a")
                    }else{
                        user_text2.setText("날짜를 선택해주세요")
                    }
                    user_text.setText(item.name)



                }else{
                    if(item.cdday == "만난 날"){
                        var a=countdday(item.met_year,item.met_month,item.met_day)
                        user_text2.setText("D+"+"$a")
                    }else if(item.cdday=="생일"){
                        var a=countdday(item.birth_year,item.birth_month,item.birth_day)
                        user_text2.setText("D+"+"$a")
                    }else{
                        user_text2.setText("날짜를 선택해주세요")
                    }
                    user_text.setText(item.name)
                }
            }
        }

        //각 item에 클릭 이벤트 붙이기
        holder.itemView.setOnClickListener { view->
            var intent= Intent(view.context, HomeActivity::class.java)
            //intent.putExtra("goal_name",goalList[position].title)
            intent.putExtra("user_id",userList[position].id)


            intent.putExtra("name",userList[position].name)
            intent.putExtra("gender",userList[position].gender)
            intent.putExtra("birth",userList[position].birth)
            intent.putExtra("type",userList[position].type)
            intent.putExtra("num",userList[position].num)
            intent.putExtra("color",userList[position].color)
            intent.putExtra("dday",userList[position].dday)
            intent.putExtra("image",userList[position].image)
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
    fun countdday(myear: Int, mmonth: Int, mday: Int): Int {
        var mmonth = mmonth
        try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

            val todaCal = Calendar.getInstance() //오늘날자 가져오기
            val ddayCal = Calendar.getInstance() //오늘날자를 가져와 변경시킴

            mmonth -= 1 // 받아온날자에서 -1을 해줘야함.
            ddayCal.set(myear, mmonth, mday)// D-day의 날짜를 입력

            val today =
                todaCal.getTimeInMillis() / 86400000 //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            val dday = ddayCal.getTimeInMillis() / 86400000
            val count = today-dday // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return count.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }
}