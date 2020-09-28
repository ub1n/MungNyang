package kr.ac.smu.cs.mungnyang

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    val PREFS_FILENAME="prefs"
    val PREF_KEY_MY_EDITTEXT="loginId"
    val prefs: SharedPreferences =context.getSharedPreferences(PREFS_FILENAME,0)

    //sahredPreferences에 저장하려는 변수, get set은 추가 지정

    var myCheckSound: Boolean
        get()=prefs.getBoolean("myCheckSound",false)
        set(value)=prefs.edit().putBoolean("myCheckSound",value).apply()

}