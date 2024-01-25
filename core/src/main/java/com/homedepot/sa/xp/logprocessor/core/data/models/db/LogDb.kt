package com.homedepot.sa.xp.logprocessor.core.data.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LogDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val message: String,
    val tag: String,
    val error: String
)