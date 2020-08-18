package kr.ac.smu.cs.mungnyang

import android.graphics.Bitmap
import androidx.room.*

@Entity
class User() {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
    var name:String?=null
    var gender:String?=null
    var birth:String?=null
    var birth_year:Int=0
    var birth_month:Int=0
    var birth_day:Int=0
    var type:String?=null
    var num:String?= null
    var color:String?=null
    var met_day:Int=0
    var met_month:Int=0
    var met_year:Int=0
    var dday:String?=null
    var image: ByteArray?=null
    var backPath:Int=0
    var maruPath:Int=0
    var myLevel:Int=1
    var cdday:String?=null
}