package kr.ac.smu.cs.mungnyang

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Cal {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
    var year:Int=0
    var month:Int=0
    var day:Int=0
    var todo:String?=null
    var caltext:String?=null


}