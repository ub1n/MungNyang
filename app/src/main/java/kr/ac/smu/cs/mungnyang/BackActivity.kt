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
            R.drawable.back8,R.drawable.back9,R.drawable.back10,R.drawable.back11,R.drawable.back12,R.drawable.back13,R.drawable.back14,R.drawable.back15)
        var maru=arrayOf(R.drawable.maru0,R.drawable.maru1,R.drawable.maru2,R.drawable.maru3,R.drawable.maru4,R.drawable.maru5,R.drawable.maru6,R.drawable.maru7,
            R.drawable.maru8,R.drawable.maru9,R.drawable.maru10,R.drawable.maru11)
        val id:Int=intent.getIntExtra("user_id",0)
        val num:Int=intent.getIntExtra("num",0)

        if(num==0){
            mAdapter=BackAdapter(back,applicationContext,0,id)
            mAdapter.notifyDataSetChanged()
            BackRecyclerView.adapter=mAdapter
            BackRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

            BackRecyclerView.setHasFixedSize(true)
        }else{
            mAdapter=BackAdapter(maru,applicationContext,1,id)
            mAdapter.notifyDataSetChanged()
            BackRecyclerView.adapter=mAdapter
            BackRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

            BackRecyclerView.setHasFixedSize(true)
        }


    }
}
