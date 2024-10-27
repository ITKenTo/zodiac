package com.example.zodiac.extension

fun convertTemperature(tenthsOfDegreesCelsius: Int): Double {
    return tenthsOfDegreesCelsius / 10.0
}

fun convertVoltage(voltage: Int): Double {
    return voltage / 1000.0
}

fun convertPercentageToMah(percentage: Int, fullCapacity: Int): String {
    return "${(percentage / 100.0 * fullCapacity)} mAh"
}

fun convertFullCapacity(fullCapacity: Int): String {
    return "${(fullCapacity / 1000)} mAh"
}

//fun convertScreenTimeOut(screenTimeOut: Int, context: Context): String {
//    return if (screenTimeOut != Integer.MAX_VALUE) {
//        if (screenTimeOut < 60 * 1000) {
//            "${screenTimeOut / 1000}s"
//        } else {
//            "${screenTimeOut / (1000 * 60)}m"
//        }
//    } else {
//        context.getString(R.string.never)
//    }
//}

fun convertRingMode(ringMode: Int): String {
    return when (ringMode) {
        0 -> "Silent"
        1 -> "Vibrate"
        2 -> "Normal"
        else -> "None"
    }
}