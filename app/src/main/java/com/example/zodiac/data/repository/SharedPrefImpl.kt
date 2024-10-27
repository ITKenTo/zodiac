package com.example.zodiac.data.repository

import android.content.Context
import android.content.SharedPreferences

class SharedPrefImpl(context: Context) : SharedPref {

    companion object {

        const val PREFERENCE_FILE_KEY = "shared_preferences_app"

    }

    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

    val editor: SharedPreferences.Editor
        get() = sharedPref.edit()

}