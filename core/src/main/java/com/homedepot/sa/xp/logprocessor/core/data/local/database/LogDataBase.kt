package com.homedepot.sa.xp.logprocessor.core.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homedepot.sa.xp.logprocessor.core.data.local.database.dao.LogDao
import com.homedepot.sa.xp.logprocessor.core.data.models.db.LogDb

@Database(entities = [LogDb::class], version = 1, exportSchema = false)
abstract class LogDataBase : RoomDatabase() {
    abstract fun logDao(): LogDao

    companion object {
        @Volatile
        private var INSTANCE: LogDataBase? = null

        fun create(context: Context): LogDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LogDataBase::class.java,
                    "log_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}