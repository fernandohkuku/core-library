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
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.OutputStream
import java.lang.Exception
import javax.inject.Inject

class LogsLocalDataSourceImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val logAdapter: JsonAdapter<LogDto>,
    private val logDao: LogDao,
) : LogLocalDataSource {

    private val childFolder = "logs"
    private val baseFolder = "com.homedepot.sa.xp.logprocessor/logs"
    private val suffixFile = "-log.json"

    override suspend fun saveLog(level: Int, message: String, tag: String) {
        val body = LogDto(level, message, tag)
        val json = logAdapter.toJson(body)
        // val ok = readDataFromMediaStore()
        val logDb = LogDb(id = 13, message = "Message Test", tag = "tag", error = "error")
        // logDao.insertLog(logDb)
        val test = getAllLogs()
        val lolol = logDao.getLogs()
        val okoko = lolol.size
        insert(message)
        val ssss = okoko()
        val oks = get()
        // saveLog(json)
        /*    val logsFolder = getOrCreateFolderByPackageNameWithChild(baseFolder, childFolder)
            val logFile = logsFolder.createFile(suffixFile)

            logFile.saveFile(json)*/
    }

    private fun insert(message: String) {
        val content = "content://com.homedepot.sa.xp.logprocessor.logs.provider/logs".toUri()
        val values = ContentValues().apply {
            put("message", message)
        }
        val insert = context.contentResolver.insert(content, values)
    }


    private fun saveLog(json: String) {
        val values = ContentValues().apply {
            put(MediaStore.Files.FileColumns.DISPLAY_NAME, "log_${System.currentTimeMillis()}.json")
            put(MediaStore.Files.FileColumns.MIME_TYPE, "application/json")
            put(MediaStore.Files.FileColumns.RELATIVE_PATH, "Documents/Logs/${context.packageName}")
        }
        val contentUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        context.contentResolver.insert(contentUri, values)?.also { uri ->
            context.contentResolver.openOutputStream(uri)?.use { outputStream: OutputStream ->
                outputStream.write(json.toByteArray())
            }
        }
    }


    private fun readDataFromMediaStore(): List<String> {
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.RELATIVE_PATH
        )

        val selection = MediaStore.Files.FileColumns.RELATIVE_PATH
        val selectionArgs = arrayOf("Documents/Logs")

        val sortOrder = "${MediaStore.Files.FileColumns.DISPLAY_NAME} ASC"

        val contentUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

        val cursor = context.contentResolver.query(contentUri, projection, null, null, null)

        val logs = mutableListOf<String>()

        context.contentResolver.query(contentUri, projection, null, selectionArgs, sortOrder)
            ?.use { cursor ->
                cursor.use { c ->
                    while (c.moveToNext()) {
                        val id =
                            c.getLong(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                        val displayName =
                            c.getString(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))
                        val mimeType =
                            c.getString(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE))
                        val relativePath =
                            c.getString(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.RELATIVE_PATH))

                        val fileUri = ContentUris.withAppendedId(contentUri, id)

                        val fileDetails = displayName
                        logs.add(fileDetails)
                    }
                }
            }

        return logs
    }


    private fun okoko(){
        val projection = arrayOf("message")
        val cursor: Cursor? = context.contentResolver.query(
            "content://com.homedepot.sa.xp.logprocessor.logs.provider/logs".toUri(),
            projection,
            null,
            null,
            null
        )
        val columnIndexMessage = cursor?.getColumnIndex("message")
    }
    fun get() {
        val contentResolver = context.contentResolver
        val uri = Uri.parse("content://com.homedepot.sa.xp.logprocessor.logs.provider/logs")
        val cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor != null) {
            try {
                val columnIndexMessage = cursor.getColumnIndex("message")

                if (columnIndexMessage != -1) {
                    while (cursor.moveToNext()) {
                        val logMessage = cursor.getString(columnIndexMessage)
                        val ok = logMessage
                    }
                } else {
                    // Manejar el caso donde las columnas no existen en el Cursor
                    Log.e("Error", "Las columnas no existen en el Cursor")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor.close()
            }
        }
    }

    override suspend fun getLogs(): List<LogDto> {
        val projection = arrayOf(
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.RELATIVE_PATH
        )

        val selectionArgs = arrayOf("application/json", "Documents/Logs")

        val sortOrder = MediaStore.Files.FileColumns.DISPLAY_NAME

        val contentUri = MediaStore.Files.getContentUri("external")

        val cursor = context.contentResolver.query(
            contentUri,
            projection,
            null,
            selectionArgs,
            sortOrder
        )

        val logs = mutableListOf<LogDto>()

        cursor?.use { c ->
            while (c.moveToNext()) {
                val uri = ContentUris.withAppendedId(
                    contentUri,
                    c.getLong(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))
                )
                val logDto = readLogDto(uri)
                logDto?.let { logs.add(it) }
            }
        }

        return logs
    }

    fun getAllLogs(): List<String> {
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.RELATIVE_PATH
        )

        val selection = MediaStore.Files.FileColumns.RELATIVE_PATH
        val selectionArgs = arrayOf("Documents/Logs/com")

        val sortOrder = "${MediaStore.Files.FileColumns.DISPLAY_NAME} ASC"

        val contentUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

        val cursor = context.contentResolver.query(contentUri, projection, null, null, null)

        val logs = mutableListOf<String>()

        cursor?.use { c ->
            while (c.moveToNext()) {
                val id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                val displayName =
                    c.getString(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))
                val mimeType =
                    c.getString(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE))
                val relativePath =
                    c.getString(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.RELATIVE_PATH))

                val fileUri = ContentUris.withAppendedId(contentUri, id)

                val fileDetails = displayName
                logs.add(fileDetails)
            }
        }

        return logs
    }


    private fun readLogContent(uri: Uri): String? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val content = inputStream?.bufferedReader().use { it?.readText() }
        inputStream?.close()
        return content
    }

    private fun readLogDto(uri: Uri): LogDto? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val json = inputStream?.bufferedReader().use { it?.readText() }
        inputStream?.close()

        return json?.let {
            runCatching {
                logAdapter.fromJson(json)
            }.getOrNull()
        }
    }
}