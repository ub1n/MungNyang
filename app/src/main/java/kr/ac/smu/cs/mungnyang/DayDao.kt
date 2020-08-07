package kr.ac.smu.cs.mungnyang

import androidx.room.*

@Dao
interface DayDao {
    @Query("DELETE FROM day WHERE id = (:id)")
    fun delete(id:Int)

    @Query("SELECT * FROM day WHERE userid=(:userid)") //email 값이 등록한 email과 같은 유저 전부 보이는 쿼리
    fun getDay(userid:Int): MutableList<Day>

    @Query("SELECT * FROM day WHERE id=(:id)")
    fun getDay2(id:Int):MutableList<Day>
    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(day:Day)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //정보 넣기
    fun insert(day: Day)
}