package com.nikhil.library.android

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentExxtensions : Fragment() {

    // We can easily access the ViewModel in our Fragment
    val vm: ExampleViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // extensions like add, replace, commit can simplify our transactions with lambdas
        parentFragmentManager.commit {
            addToBackStack("")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            whenResumed {
                // do some stuff
            }
        }
    }

    suspend fun ex() {
        viewLifecycleOwner.lifecycle.whenResumed {
        }
    }
}