package com.homedepot.sa.xp.logprocessor.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homedepot.sa.xp.logprocessor.core.data.models.db.LogDb

@Dao
interface LogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLog(logDb: LogDb)

    @Query("SELECT * FROM LogDb")
    fun getLogs(): List<LogDb>
}