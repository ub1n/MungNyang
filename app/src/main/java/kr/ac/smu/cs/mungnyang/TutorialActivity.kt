package kr.ac.smu.cs.mungnyang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_back.*
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {
    lateinit var mAdapter:TutoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        var tuto=arrayOf(R.drawable.tuto1,R.drawable.tuto2)
        mAdapter=TutoAdapter(tuto,applicationContext)
        mAdapter.notifyDataSetChanged()
        TutoRecyclerView.adapter=mAdapter
        TutoRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        TutoRecyclerView.setHasFixedSize(true)

        tuto_back.setOnClickListener {
            finish()
        }
    }
}
