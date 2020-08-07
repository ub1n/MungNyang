package kr.ac.smu.cs.mungnyang

import androidx.room.*

@Dao
interface UserDao {
    @Query("DELETE FROM user WHERE id = (:id)")
    fun delete(id:Int)

    @Query("SELECT * FROM user ") //email 값이 등록한 email과 같은 유저 전부 보이는 쿼리
    fun getUser(): MutableList<User>

    @Query("SELECT * FROM user WHERE id=(:id)")
    fun getUser(id:Int):MutableList<User>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(user:User)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //정보 넣기
    fun insert(user: User)
}