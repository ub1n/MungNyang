package kr.ac.smu.cs.mungnyang

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_setting.*
import kr.ac.smu.cs.mungnyang.alarm.ReminderWorker

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val id:Int=intent.getIntExtra("user_id",0)

        stamp_text.setOnClickListener {
            intent= Intent(this,StampActivity::class.java)
            intent.putExtra("user_id",id)
            startActivityForResult(intent,6)
        }
        tutorial_text.setOnClickListener {
            intent=Intent(this,TutorialActivity::class.java)
            startActivity(intent)
        }

        switch2.setChecked(App.prefs.myCheckSound)
        /*switch2.setOnClickListener {
            App.prefs.myCheckSound=switch2.isChecked
        }*/
        switch2.setOnCheckedChangeListener{buttonView,isChecked ->
            if(isChecked){
                //on_off.setText("ON")
                Toast.makeText(this,"효과음이 설정됩니다.", Toast.LENGTH_LONG).show()
                App.prefs.myCheckSound=isChecked
            }
            else{
                //on_off.setText("OFF")
                Toast.makeText(this,"효과음 설정이 해제되었습니다.", Toast.LENGTH_LONG).show()
                App.prefs.myCheckSound=isChecked
            }
        }
        if(android.os.Build.VERSION.SDK_INT>=26){
            //알람 상태 유지
            val alarm=getSharedPreferences("alarm", Context.MODE_PRIVATE)
            val alarmEditor=alarm.edit()
            val alarm_set=alarm.getString("alarm","")

            //이전에 알람 설정한 것에 대한 처리
            if(alarm_set=="alarm_on") {
                switch1.setChecked(true)
                ReminderWorker.runAt()
            }else if(alarm_set=="alarm_off"){
                switch1.setChecked(false)
                ReminderWorker.cancel()
            }else{
                ReminderWorker.cancel()
            }





            //알람 설정 여부 스위치
            switch1.setOnCheckedChangeListener{buttonView,isChecked ->
                if(isChecked){
                    //on_off.setText("ON")
                    Toast.makeText(this,"알림이 설정됩니다.", Toast.LENGTH_LONG).show()
                    alarmEditor.putString("alarm","alarm_on")
                    alarmEditor.commit()
                    //알람 켜기
                    ReminderWorker.runAt()
                }
                else{
                    //on_off.setText("OFF")
                    Toast.makeText(this,"알림이 해제되었습니다.", Toast.LENGTH_LONG).show()
                    alarmEditor.putString("alarm","alarm_off")
                    alarmEditor.commit()
                    //알람 끄기
                    ReminderWorker.cancel()
                }
            }
        }else{
            switch1.isClickable=false
        }


    }

}

