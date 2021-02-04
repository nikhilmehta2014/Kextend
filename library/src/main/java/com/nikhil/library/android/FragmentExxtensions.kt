package com.nikhil.library.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit

class FragmentExxtensions : Fragment() {

    // We can easily access the ViewModel in our Fragment
    val vm: ExampleViewmodel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // extensions like add, replace, commit can simplify our transactions with lambdas
        parentFragmentManager.commit {
            addToBackStack("")
        }
    }
}