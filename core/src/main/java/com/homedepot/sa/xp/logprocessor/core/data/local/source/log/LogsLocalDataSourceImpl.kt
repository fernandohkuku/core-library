package com.homedepot.sa.xp.logprocessor.core.data.local.source.log

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import com.homedepot.sa.xp.logprocessor.core.data.local.database.dao.LogDao
import com.homedepot.sa.xp.logprocessor.core.data.models.db.LogDb
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.log.LogDto
import com.homedepot.sa.xp.logprocessor.core.data.repositories.LogLocalDataSource
import com.homedepot.sa.xp.logprocessor.core.di.hilt.PresentationModule.Companion.URI_LOG_CONTENT
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.OutputStream
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

class LogsLocalDataSourceImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val logAdapter: JsonAdapter<LogDto>,
    private val logDao: LogDao,
    @Named(URI_LOG_CONTENT)
    private val logContent: Uri
) : LogLocalDataSource {
    override suspend fun saveLog(level: Int, message: String, tag: String, error: String?) {
        insert(level, message, tag, error)
        val result = getLogs()
        val isOk = result
    }


    private fun insert(level: Int, message: String, tag: String, error: String?) {
        val values = ContentValues().apply {
            put("level", level)
            put("message", message)
            put("tag", tag)
            put("error", error)
        }
        context.contentResolver.insert(logContent, values)
    }

    override suspend fun getLogs(): List<LogDto> {
        val cursor = context.contentResolver.query(logContent, null, null, null, null)
        val logs = mutableListOf<LogDto>()

        cursor?.use { c ->
            val columnIndex = c.getColumnIndexOrThrow("id")
            while (c.moveToNext()) {
                val messageId = c.getLong(columnIndex)
                val uri = ContentUris.withAppendedId(logContent, messageId)
                readLogDto(uri)?.let { logs.add(it) }
            }
        }

        return logs
    }

    private fun readLogDto(uri: Uri): LogDto? {
        return runCatching {
            context.contentResolver.openInputStream(uri)?.use { it.bufferedReader().readText() }
        }.getOrNull()?.let { logAdapter.fromJson(it) }
    }
}

