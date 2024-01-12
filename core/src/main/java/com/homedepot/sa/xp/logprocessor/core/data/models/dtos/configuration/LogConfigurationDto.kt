package com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration

 data class LogConfigurationDto(
    val serviceId: String,
    val stagingLocation: String,
    val localLogPath: String,
    val backupDirectory: String,
    val thirdApps: List<ThirdAppDto>,
    val maxPostSizeKB: Int,
    val processingInterval: Int,
    val logServiceURL: String,
    val logServiceBatchURL: String,
    val logServiceConnectionTimeout: Int,
    val logServiceGZip: Boolean,
    val transferRetry:Int,
    val transferRetrySleepInterval:Int,
    val ignoreLogsOlderThan:Int,
    val maxStagingDirectorySize:Int,
    val zipDaysToKeep:Int,
    val maxBackupDirectorySizeMB:Int,
    val mStoreId:String,
    val mDevServerResponse:String,
)