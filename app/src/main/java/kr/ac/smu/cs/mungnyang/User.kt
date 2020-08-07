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
    var type:String?=null
    var num:String?= null
    var color:String?=null
    var dday:String?=null
    var image: ByteArray?=null
    var backPath:Int=0
    var maruPath:Int=0
}