package com.it_academy.countries_app.fragments.start_screen.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.it_academy.countries_app.databinding.FragmentBottomSheetDialogRationaleBinding
import com.it_academy.domain.RATIONALE_KEY
import com.it_academy.domain.RATIONALE_TAG
import com.it_academy.domain.RESULT_KEY

class BottomSheetDialogRationaleFragment: BottomSheetDialogFragment() {

    private var binding: FragmentBottomSheetDialogRationaleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetDialogRationaleBinding.inflate(inflater, container,false)

        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.button?.setOnClickListener {
            setFragmentResult(
                RATIONALE_KEY,
                bundleOf(RESULT_KEY to true),
            )
            dismiss()
        }
    }

    companion object {
        const val TAG = RATIONALE_TAG
    }
}