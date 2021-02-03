package com.nikhil.library

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged

class EditTextExtensions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edittext_extensions)

        val editText = findViewById<EditText>(R.id.et_dummy)

        editText.doAfterTextChanged {

        }
    }
}