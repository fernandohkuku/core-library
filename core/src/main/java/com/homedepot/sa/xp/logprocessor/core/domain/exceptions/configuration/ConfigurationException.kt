package com.homedepot.sa.xp.logprocessor.core.domain.exceptions.configuration

internal open class ConfigurationException(
    override val message: String = "It is not possible to process the configuration"
) : RuntimeException(message)