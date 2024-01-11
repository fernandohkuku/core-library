package com.homedepot.sa.xp.logprocessor.core.domain.exceptions.configuration

internal class PlatformConfigurationNotFoundException(
    override val message: String = "Platform configuration not found"
) : ConfigurationException(message)