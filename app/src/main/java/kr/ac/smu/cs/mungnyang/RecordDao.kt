package kr.ac.smu.cs.mungnyang

import androidx.room.*

@Dao
interface RecordDao {
    @Query("DELETE FROM record WHERE id = (:id)")
    fun delete(id:Int)

    @Query("SELECT * FROM record WHERE userid=(:userid)") //email 값이 등록한 email과 같은 유저 전부 보이는 쿼리
    fun getRecord(userid:Int): MutableList<Record>

    @Query("SELECT * FROM record WHERE userid=(:userid) AND id=(:id)") //email 값이 등록한 email과 같은 유저 전부 보이는 쿼리
    fun getRecord(userid:Int,id:Int): MutableList<Record>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(record:Record)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //정보 넣기
    fun insert(record: Record)
}