package com.example.test_app.fragments.sliders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.test_app.common.Common
import com.example.test_app.databinding.FragmentSlidersBinding
import com.example.test_app.dto.CountryDTO
import com.example.test_app.transformers.transformToMutableListDto
import com.google.android.material.slider.RangeSlider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SlidersFragment : Fragment() {

    var binding: FragmentSlidersBinding? = null
    var populationSliderValues = mutableListOf<Float>()
    var areaSliderValues = mutableListOf<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSlidersBinding.inflate(inflater, container, false)
        getCountriesList()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.buttonSliders?.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "Population",
                populationSliderValues
            )
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "Area",
                areaSliderValues
            )
            findNavController().popBackStack()
        }
    }

    fun populationSlider(minPopulation: Int, maxPopulation: Int) {

        binding?.rangeSliderPopulation?.valueFrom = minPopulation.toFloat()
        binding?.rangeSliderPopulation?.valueTo = maxPopulation.toFloat()
        binding?.rangeSliderPopulation?.values =
            mutableListOf(minPopulation.toFloat(), maxPopulation.toFloat())

        binding?.rangeSliderPopulation?.addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                populationSliderValues = slider.values
            }
        })
    }

    fun areaSlider(minArea: Double, maxArea: Double) {

        binding?.rangeSliderArea?.valueFrom = minArea.toFloat()
        binding?.rangeSliderArea?.valueTo = maxArea.toFloat()
        binding?.rangeSliderArea?.values = mutableListOf(minArea.toFloat(), maxArea.toFloat())

        binding?.rangeSliderArea?.addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                areaSliderValues = slider.values
            }
        })
    }

    fun getCountriesList() {
        Common.retrofitService?.getCountryDate()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.map { it.transformToMutableListDto() }
            ?.subscribe({ response ->
                response.sortBy { population ->
                    population.population
                }
                populationSlider(response[0].population, response[response.size - 1].population)

                response.sortBy { area ->
                    area.area
                }
                areaSlider(response[0].area, response[response.size - 1].area)

            }, {})
    }
}