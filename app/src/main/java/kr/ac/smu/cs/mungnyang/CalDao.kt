package kr.ac.smu.cs.mungnyang

import androidx.room.*

@Dao
interface CalDao {
    @Query("DELETE FROM cal WHERE id = (:id)")
    fun delete(id:Int)

    @Query("SELECT * FROM cal WHERE year=(:year) AND month=(:month) AND day=(:day)") //email 값이 등록한 email과 같은 유저 전부 보이는 쿼리
    fun getCal(year:Int,month:Int,day:Int): MutableList<Cal>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(cal:Cal)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //정보 넣기
    fun insert(cal: Cal)
}