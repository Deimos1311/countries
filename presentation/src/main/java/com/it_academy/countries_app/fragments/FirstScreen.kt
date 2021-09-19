package com.it_academy.countries_app.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.it_academy.countries_app.R
import com.it_academy.countries_app.databinding.FragmentFirstScreenBinding
import com.it_academy.countries_app.fragments.start_screen.bottom_sheet.BottomSheetDialogDontAskFragment
import com.it_academy.countries_app.fragments.start_screen.bottom_sheet.BottomSheetDialogRationaleFragment
import com.it_academy.domain.RATIONALE_KEY
import com.it_academy.domain.RESULT_KEY
import com.it_academy.domain.SETTINGS_KEY

class FirstScreen : Fragment() {

    var binding: FragmentFirstScreenBinding? = null

    private val permissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    findNavController().navigate(R.id.action_firstScreen_to_start_screen)
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showSettingsDialog()
                }
                else -> {
                    showSnackbarShort(R.string.permission_denied)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstScreenBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.button?.setOnClickListener {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showRationaleDialog()
            } else {
                permissions.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        setFragmentResultListener(RATIONALE_KEY)
        { _, bundle ->
            val isWantToAllowAfterRationale = bundle.getBoolean(RESULT_KEY)
            if (isWantToAllowAfterRationale) {
                permissions.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        setFragmentResultListener(SETTINGS_KEY)
        { _, bundle ->
            val isWantToOpenSettings = bundle.getBoolean(RESULT_KEY)
            if (isWantToOpenSettings) {
                openSettings()
            }
        }
    }

    private fun showRationaleDialog() {
        BottomSheetDialogRationaleFragment()
            .show(parentFragmentManager, BottomSheetDialogRationaleFragment.TAG)
    }

    private fun showSettingsDialog() {
        BottomSheetDialogDontAskFragment()
            .show(parentFragmentManager, BottomSheetDialogDontAskFragment.TAG)
    }

    private fun showSnackbarShort(snackId: Int) {
        Snackbar.make(requireView(), snackId, Snackbar.LENGTH_SHORT).show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}