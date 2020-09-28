package kr.ac.smu.cs.mungnyang

class Com {
    companion object{

        var backPath:Int=0
        fun setbackimage(id:Int){
            backPath=id
        }
        var maruPath:Int=0
        fun setmaruimage(id:Int){
            maruPath=id
        }
        var anitype:Int=0
        fun setType(id:Int){
            anitype=id
        }
        var mlevel:Int=0
        fun setLevel(id:Int){
            mlevel=id
        }
        fun levelUp(){
            mlevel++
        }
        var msize:Int=-1
        fun setSize(id:Int){
            msize=id
        }

    }
}