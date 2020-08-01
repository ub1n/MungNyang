package kr.ac.smu.cs.mungnyang

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Day::class], version = 1)
abstract class DayDatabase : RoomDatabase() {

    abstract val dayDao: DayDao

    companion object {
        private var instance: DayDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DayDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, DayDatabase::class.java, "day.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as DayDatabase
        }
    }
}