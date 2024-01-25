package com.homedepot.sa.xp.logprocessor.core.di.manual

import android.content.Context
import androidx.room.Room
import com.homedepot.sa.xp.logprocessor.core.data.local.database.LogDataBase
import com.homedepot.sa.xp.logprocessor.core.data.local.database.dao.LogDao

class DataBaseModule(
    private val context: Context
) {
    val logDataBase: LogDataBase by lazy {
        LogDataBase.create(context)
    }

    val logDao: LogDao by lazy {
        logDataBase.logDao()
    }
}