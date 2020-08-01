package kr.ac.smu.cs.mungnyang

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Record::class], version = 1)
abstract class RecordDatabase : RoomDatabase() {

    abstract val recordDao: RecordDao

    companion object {
        private var instance: RecordDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RecordDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, RecordDatabase::class.java, "record.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as RecordDatabase
        }
    }
}