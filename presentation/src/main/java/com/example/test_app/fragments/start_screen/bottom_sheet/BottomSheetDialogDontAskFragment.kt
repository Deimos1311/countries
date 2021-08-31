package com.example.test_app.fragments.start_screen.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.test_app.RESULT_KEY
import com.example.test_app.SETTINGS_KEY
import com.example.test_app.SETTINGS_TAG
import com.example.test_app.databinding.FragmentBottomSheetDialogDontAskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialogDontAskFragment: BottomSheetDialogFragment() {

    var binding : FragmentBottomSheetDialogDontAskBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetDialogDontAskBinding
                .inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.button?.setOnClickListener {
            setFragmentResult(
                SETTINGS_KEY,
                bundleOf(RESULT_KEY to true)
            )
            dismiss()
        }
    }

    companion object {
        const val TAG = SETTINGS_TAG
    }
}