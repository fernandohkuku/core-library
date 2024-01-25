package com.homedepot.sa.xp.logprocessor.core.infrastructure.providers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.homedepot.sa.xp.logprocessor.core.data.local.database.LogDataBase
import com.homedepot.sa.xp.logprocessor.core.data.models.db.LogDb

class LogContentProvider : ContentProvider() {
    private lateinit var dataBase: LogDataBase

    private val logContext: Context
        get() = context?.applicationContext!!

    override fun onCreate(): Boolean {
        dataBase = LogDataBase.create(context = logContext)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val logs = dataBase.logDao().getLogs()

        val cursor = MatrixCursor(arrayOf("_id", "message"))
        for (log in logs) {
            cursor.addRow(arrayOf(log.id, log.message))
        }

        return cursor
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.dir/vnd.example.logs"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val message = values?.getAsString("message") ?: ""
        val log = LogDb(message = message, tag = "", error = "")
        dataBase.logDao().insertLog(log)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}