package kr.ac.smu.cs.mungnyang

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Day {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
    var userid:Int?=null
    var todo:String?=null
    var done:Boolean=false
    var date:String?=null
    var oneday:Boolean=false
}