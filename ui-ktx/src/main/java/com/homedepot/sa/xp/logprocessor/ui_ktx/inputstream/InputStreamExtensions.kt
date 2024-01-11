package com.homedepot.sa.xp.logprocessor.ui_ktx.inputstream

import java.io.FileOutputStream
import java.io.InputStream


fun InputStream.saveFile(path: String) {
    use { input ->
        FileOutputStream(path).use { outPut ->
            input.copyTo(outPut)
        }
    }
}