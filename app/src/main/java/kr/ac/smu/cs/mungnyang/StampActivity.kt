package kr.ac.smu.cs.mungnyang

import android.app.ActionBar
import android.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.core.view.marginTop
import androidx.core.view.setPadding


class StampActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamp)
        /*val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )*/
        val layout=findViewById<LinearLayout>(R.id.dynamicLayout)
        val edit = TextView(this)
        edit.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )

        edit.setText("스탬프 현황 : "+Com.mlevel.toString()+"개")
        edit.setTextSize(23.0F)
        edit.setPadding(30,30,30,30)



        val layout2 = LinearLayout(this)
        layout2.orientation = LinearLayout.HORIZONTAL
        layout2.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        layout2.setHorizontalGravity(Gravity.CENTER)
        val layout3 = LinearLayout(this)
        layout3.orientation = LinearLayout.HORIZONTAL
        layout3.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        layout3.setHorizontalGravity(Gravity.CENTER)
        val layout5 = LinearLayout(this)
        layout5.orientation = LinearLayout.HORIZONTAL
        layout5.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        layout5.setHorizontalGravity(Gravity.CENTER)
        val layout4 = LinearLayout(this)
        layout4.orientation = LinearLayout.HORIZONTAL
        layout4.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        layout4.setHorizontalGravity(Gravity.CENTER)


        layout.addView(edit)
        /*for(i in 1..3){
            val image= ImageView(this)
            image.setLayoutParams(
                LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                )
            )
            image.setImageResource(R.drawable.stamp)
            image.setPadding(30,30,30,30)
            layout.addView(image)
        }*/
        layout.addView(layout2)
        layout.addView(layout3)
        layout.addView(layout4)
        layout.addView(layout5)
        for(i in 1..Com.mlevel){
            if(i<4){
            val image= ImageView(this)
            image.setLayoutParams(
                LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
            )
            image.setImageResource(R.drawable.caletc)
                image.setPadding(30)
            layout2.addView(image)}
            else if(i<7){
                val image2 = ImageView(this)
                image2.setLayoutParams(
                    LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                    )
                )
                image2.setImageResource(R.drawable.caletc)
                image2.setPadding(30)
                layout3.addView(image2)
            }else if(i<10){
                val image2 = ImageView(this)
                image2.setLayoutParams(
                    LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                    )
                )
                image2.setImageResource(R.drawable.stamp)
                image2.setPadding(30)
                layout4.addView(image2)
            }else{
                val image2 = ImageView(this)
                image2.setLayoutParams(
                    LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                    )
                )
                image2.setImageResource(R.drawable.stamp)
                image2.setPadding(30)
                layout5.addView(image2)
            }
        }

        //setContentView(layout)
    }
}
