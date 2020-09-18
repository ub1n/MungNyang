package kr.ac.smu.cs.mungnyang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_back.*

class BackActivity : AppCompatActivity() {
    lateinit var mAdapter:BackAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_back)
        var back=arrayOf(R.drawable.back0,R.drawable.back1,R.drawable.back2,R.drawable.back3,R.drawable.back4,R.drawable.back5,R.drawable.back6,R.drawable.back7,
            R.drawable.back8,R.drawable.back9,R.drawable.back10,R.drawable.back11,R.drawable.back12,R.drawable.back13,R.drawable.back14,R.drawable.back15,
            R.drawable.back16,R.drawable.back17,R.drawable.back18,R.drawable.back19)
        var maru=arrayOf(R.drawable.maru0,R.drawable.maru1,R.drawable.maru2,R.drawable.maru3,R.drawable.maru4,R.drawable.maru5,R.drawable.maru6,R.drawable.maru7,
            R.drawable.maru8,R.drawable.maru9,R.drawable.maru10,R.drawable.maru11,R.drawable.maru12,R.drawable.maru13,R.drawable.maru14,R.drawable.maru15,R.drawable.maru16,
            R.drawable.maru17,R.drawable.maru18,R.drawable.maru19,R.drawable.maru20)
        val id:Int=intent.getIntExtra("user_id",0)
        val num:Int=intent.getIntExtra("num",0)
        var levelback=arrayOf(R.drawable.back0)
        var levelmaru=arrayOf(R.drawable.maru0,R.drawable.maru20)


        for(i in 1..(Com.mlevel-1)){
            levelback=levelback.plusElement(back[i])
        }
        for(i in 1..(Com.mlevel-1)){
            //levelmaru.plus(maru[i])
            levelmaru=levelmaru.plusElement(maru[i])
        }
        if(num==0){
            mAdapter=BackAdapter(levelback,applicationContext,0,id)
            mAdapter.notifyDataSetChanged()
            BackRecyclerView.adapter=mAdapter
            BackRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

            BackRecyclerView.setHasFixedSize(true)
        }else{
            mAdapter=BackAdapter(levelmaru,applicationContext,1,id)
            mAdapter.notifyDataSetChanged()
            BackRecyclerView.adapter=mAdapter
            BackRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

            BackRecyclerView.setHasFixedSize(true)
        }


    }
}
