package com.example.zodiac.extension

import java.io.File

fun File.createIfNotExist() {
    absolutePath.removeSuffix(name).takeIf { it.isNotEmpty() }?.let {
        File(it).takeIf { !it.exists() }?.mkdirs()
    }
    takeIf { !it.exists() }?.createNewFile()
}

fun copyInternalFile(urlFrom: String, folder: File): String {
    val inputFile = File(urlFrom)
    return if (inputFile.exists()) {
        try {
            val outputPath = File(folder, inputFile.name)
            val file = inputFile.copyTo(outputPath)
            if (file.exists()) file.absolutePath
            else ""
        } catch (ex: Exception) {
            ""
        }
    } else {
        ""
    }
}

fun deleteFile(url: String): Boolean {
    return File(url).delete()
}