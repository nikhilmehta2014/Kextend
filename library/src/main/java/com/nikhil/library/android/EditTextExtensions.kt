package com.nikhil.library.android

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import com.nikhil.library.R

class EditTextExtensions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edittext_extensions)

        val editText = findViewById<EditText>(R.id.et_dummy)

        editText.doBeforeTextChanged { text, start, count, after ->

        }

        editText.doOnTextChanged { text, start, before, count ->

        }

        editText.doAfterTextChanged {

        }
    }
}