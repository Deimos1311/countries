package com.example.test_app.fragments.start_screen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.domain.RATIONALE_KEY
import com.example.domain.RESULT_KEY
import com.example.domain.SETTINGS_KEY
import com.example.test_app.R
import com.example.test_app.base.mvp.BaseMvpFragment
import com.example.test_app.databinding.FragmentStartScreenBinding
import com.example.test_app.fragments.start_screen.bottom_sheet.BottomSheetDialogDontAskFragment
import com.example.test_app.fragments.start_screen.bottom_sheet.BottomSheetDialogRationaleFragment
import com.google.android.material.snackbar.Snackbar

class StartScreenFragment : BaseMvpFragment<StartScreenView>(), StartScreenView {

    private var binding: FragmentStartScreenBinding? = null

    private val permissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    findNavController().navigate(R.id.action_start_screen_to_first_fragment)
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
        binding = FragmentStartScreenBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().attachView(this)

        binding?.buttonToListOfCountries?.setOnClickListener {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showRationaleDialog()
            } else {
                permissions.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        binding?.buttonToMap?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_mapFragment)
        }
        binding?.buttonToMyLocationMap?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_myLocation)
        }
        binding?.buttonToCapitals?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_listOfCapitalsFragment)
        }

        binding?.buttonToRegion?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_regionFragment)
        }

        setFragmentResultListener(RATIONALE_KEY) { _, bundle ->
            val isWantToAllowAfterRationale = bundle.getBoolean(RESULT_KEY)
            if (isWantToAllowAfterRationale) {
                permissions.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        setFragmentResultListener(SETTINGS_KEY) { _, bundle ->
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

    //todo read about this
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun createPresenter() {
        mPresenter = StartScreenPresenter()
    }

    override fun getPresenter(): StartScreenPresenter = mPresenter as StartScreenPresenter

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
