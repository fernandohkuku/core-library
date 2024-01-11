package com.homedepot.sa.xp.logprocessor.ui_ktx.firstphone

import android.net.wifi.WifiManager
import android.text.format.Formatter
import java.net.InetAddress

fun WifiManager.findStoreNumberFromWifi(): String? {
    return try {
        dhcpInfo.dns1.toInetAddress().canonicalHostName.extractStoreNumberFromFqdn()
    } catch (e: Exception) {
        null
    }
}

private fun Int.toInetAddress(): InetAddress =
    InetAddress.getByName(Formatter.formatIpAddress(this))

private fun String.extractStoreNumberFromFqdn(): String? =
    this.split("\\.".toRegex())
        .firstOrNull { it.length == 6 && (it.startsWith("st") || it.startsWith("dc")) }
        ?.substring(2)