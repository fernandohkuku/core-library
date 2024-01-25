package com.homedepot.sa.xp.logprocessor.core.di.manual

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.core.net.toUri

class PresentationModule(
    private val context: Context
) {
    @get:JvmSynthetic
    val resources: Resources by lazy {
        context.resources
    }

    val packageName: String by lazy {
        context.packageName
    }

    val uriLogContent: Uri by lazy {
        "content://com.homedepot.sa.xp.logprocessor.logs.provider/logs".toUri()
    }
}