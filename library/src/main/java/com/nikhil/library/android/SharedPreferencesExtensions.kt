package com.nikhil.library.android

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class SharedPreferencesExtensions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Old way
        val spOld = getPreferences(Context.MODE_PRIVATE)
            .edit()
            .putBoolean("key", true)
            .apply()

        // New way
        val spNew = getPreferences(Context.MODE_PRIVATE)
            .edit {
                putBoolean("key", true)
            }


    }
}