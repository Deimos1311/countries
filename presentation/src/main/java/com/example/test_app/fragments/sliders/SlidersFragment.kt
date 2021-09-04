package com.example.test_app.fragments.sliders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.test_app.R
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.databinding.FragmentSlidersBinding
import com.google.android.material.slider.RangeSlider
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class SlidersFragment : ScopeFragment() {

    var binding: FragmentSlidersBinding? = null
    var populationStart: Float = 0.0F
    var populationEnd: Float = 0.0F
    var areaStart: Float = 0.0F
    var areaEnd: Float = 0.0F
    var distance: Float = 0.0F

    private val viewModel: SlidersViewModel by stateViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_slidersFragment_to_list_of_countries)
                    Toast.makeText(requireContext(), "TEST", Toast.LENGTH_SHORT).show()
                }

            })

        //val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
        //}


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSlidersBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.buttonSliders?.setOnClickListener {

            distance = binding?.editText?.text.toString().toFloat()
            val slidersValues = mutableListOf(
                populationStart,
                populationEnd,
                areaStart,
                areaEnd,
                distance
            )
            setFragmentResult(
                "key",
                bundleOf("pair" to slidersValues)
            )

            /*findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "Sliders",
                slidersValues
            )*/
            findNavController().popBackStack()
        }

        viewModel.getListOfCountriesLivaData.observe(viewLifecycleOwner) {
            when (it) {
                is Outcome.Progress -> {
                    if (it.loading) showProgress() else hideProgress()
                }
                is Outcome.Next -> {

                }
                is Outcome.Success -> {
                    populationSlider(
                        it.data[0].population, it.data[it.data.size - 1].population
                    )
                }
                is Outcome.Failure -> {
                }
            }
        }

        viewModel.getListOfCountriesSortedByAreaLivaData.observe(viewLifecycleOwner) {
            when (it) {
                is Outcome.Progress -> {
                    if (it.loading) showProgress() else hideProgress()
                }
                is Outcome.Next -> {

                }
                is Outcome.Success -> {
                    areaSlider(it.data[0].area, it.data[it.data.size - 1].area)
                }
                is Outcome.Failure -> {
                }
            }
        }
        viewModel.getCountriesListSortedByPopulation()
        viewModel.getCountriesListSortedByArea()
    }

    private fun populationSlider(minPopulation: Int, maxPopulation: Int) {

        binding?.rangeSliderPopulation?.valueFrom = minPopulation.toFloat()
        binding?.rangeSliderPopulation?.valueTo = maxPopulation.toFloat()
        binding?.rangeSliderPopulation?.values =
            mutableListOf(minPopulation.toFloat(), maxPopulation.toFloat())
        populationStart = minPopulation.toFloat()
        populationEnd = maxPopulation.toFloat()

        binding?.populationText?.text = getString(
            R.string.slider_text_population, populationStart.toInt(), populationEnd.toInt()
        )

        binding?.rangeSliderPopulation?.addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                populationStart = slider.values[0]
                populationEnd = slider.values[1]
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                populationStart = slider.values[0]
                populationEnd = slider.values[1]
            }
        })
    }

    private fun areaSlider(minArea: Double, maxArea: Double) {

        binding?.rangeSliderArea?.valueFrom = minArea.toFloat()
        binding?.rangeSliderArea?.valueTo = maxArea.toFloat()
        binding?.rangeSliderArea?.values = mutableListOf(minArea.toFloat(), maxArea.toFloat())
        areaStart = minArea.toFloat()
        areaEnd = maxArea.toFloat()

        binding?.areaText?.text = getString(
            R.string.slider_text_area, areaStart.toInt(), areaEnd.toInt()
        )

        binding?.rangeSliderArea?.addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                areaStart = slider.values[0]
                areaEnd = slider.values[1]
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                areaStart = slider.values[0]
                areaEnd = slider.values[1]
            }
        })
    }

    private fun showProgress() {
        binding?.progressBar?.isVisible = true
        binding?.frameWithProgress?.isVisible = true
    }

    private fun hideProgress() {
        binding?.progressBar?.isVisible = false
        binding?.frameWithProgress?.isVisible = false
    }
}