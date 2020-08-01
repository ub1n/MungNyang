package kr.ac.smu.cs.mungnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    private var userDatabase:UserDatabase?=null
    private var userList=mutableListOf<User>()

    lateinit var mAdapter:StartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        var addintent:Intent?=null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        userDatabase=UserDatabase.getInstance(this)

        mAdapter= StartAdapter(userList,applicationContext)
        val r = Runnable {   //recyclerview와 android room 결합
            try {
                userList = userDatabase?.userDao?.getUser()!!
                mAdapter = StartAdapter(userList, applicationContext)
                mAdapter.notifyDataSetChanged()
                mainRecyclerView.adapter = mAdapter
                mainRecyclerView.layoutManager = LinearLayoutManager(this)

                mainRecyclerView.setHasFixedSize(true)

            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }

        val thread = Thread(r)
        thread.start()

        petAddbutton.setOnClickListener {
            addintent= Intent(this,SignActivity::class.java)
            startActivityForResult(addintent,2)
        }
    }
}
