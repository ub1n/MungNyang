package kr.ac.smu.cs.mungnyang

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cal::class], version = 1)
abstract class CalDatabase : RoomDatabase() {

    abstract val calDao: CalDao

    companion object {
        private var instance: CalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CalDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, CalDatabase::class.java, "cal.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as CalDatabase
        }
    }
}