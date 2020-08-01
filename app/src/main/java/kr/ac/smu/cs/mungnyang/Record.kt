package kr.ac.smu.cs.mungnyang

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Record {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
    var userid:Int?=null
    var name:String?=null
    var rec:String?=null
    var image:ByteArray?=null
}