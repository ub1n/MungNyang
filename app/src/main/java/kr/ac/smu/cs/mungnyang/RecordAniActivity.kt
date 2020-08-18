package kr.ac.smu.cs.mungnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_record_ani.*

class RecordAniActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_ani)
        val DURATION:Long=1700
        Glide.with(this).asGif().load(R.drawable.rec_gif).into(imageView_ani)

        val userid=intent.getIntExtra("user_id",0)
        Handler().postDelayed({
            val intent= Intent(this,RecordActivity::class.java)
            intent.putExtra("user_id",userid)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        },DURATION)
    }
}
