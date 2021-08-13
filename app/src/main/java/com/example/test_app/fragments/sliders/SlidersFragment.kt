package com.example.test_app.fragments.sliders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test_app.common.Common
import com.example.test_app.databinding.FragmentSlidersBinding
import com.example.test_app.dto.CountryDTO
import com.example.test_app.transformers.transformToMutableListDto
import com.google.android.material.slider.RangeSlider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SlidersFragment : Fragment() {

    var binding: FragmentSlidersBinding? = null
    var list: MutableList<CountryDTO> = mutableListOf()

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

    }

    fun configSlider(firstIndex: Int, lastIndex: Int) {

        binding?.rangeSlider?.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                slider.valueFrom = firstIndex.toFloat()
                slider.valueTo = lastIndex.toFloat()
            }
            override fun onStopTrackingTouch(slider: RangeSlider) {

            }
        })

        binding?.rangeSlider?.addOnChangeListener { rangeSlider, value, fromUser ->
            //rangeSlider.values = mutableListOf(30f, 1000f)
        }
    }

    fun getCountriesList() {
        Common.retrofitService?.getCountryDate()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.map { it.transformToMutableListDto() }
            ?.subscribe({response->
                        response.sortBy { item->
                            item.population
                        }
                configSlider(response[0].population, response[response.size - 1].population)
            },{})
    }
}