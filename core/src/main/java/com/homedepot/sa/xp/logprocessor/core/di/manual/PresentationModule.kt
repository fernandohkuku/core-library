package com.homedepot.sa.xp.logprocessor.core.di.manual

import android.content.Context
import android.content.res.Resources

class PresentationModule(
    private val context: Context
) {
    @get:JvmSynthetic
    val resources: Resources by lazy {
        context.resources
    }

}