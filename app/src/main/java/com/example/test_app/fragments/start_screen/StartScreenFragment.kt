package com.example.test_app.fragments.start_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.test_app.MainActivity
import com.example.test_app.R
import com.example.test_app.base.mvp.BaseMvpFragment
import com.example.test_app.databinding.FragmentStartScreenBinding

class StartScreenFragment : BaseMvpFragment<StartScreenView>(), StartScreenView {

    private var binding: FragmentStartScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            findNavController().navigate(R.id.action_start_screen_to_first_fragment)
        }
        binding?.buttonToMap?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_mapFragment)
        }
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
}
