package com.it_academy.countries_app.fragments.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.it_academy.countries_app.databinding.FragmentRegionBinding
import com.it_academy.domain.outcome.Outcome
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class RegionFragment : ScopeFragment() {

    var binding: FragmentRegionBinding? = null
    lateinit var regionAdapter: RegionAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel: RegionViewModel by stateViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegionBinding.inflate(inflater, container, false)

        return binding?.root
    }

    private fun initRecyclerView(view: View) {
        linearLayoutManager = LinearLayoutManager(activity)
        binding?.recyclerViewRegion?.setHasFixedSize(true)
        binding?.recyclerViewRegion?.layoutManager = linearLayoutManager

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding?.recyclerViewRegion?.addItemDecoration(decoration)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

        regionAdapter = RegionAdapter()
        binding?.recyclerViewRegion?.adapter = regionAdapter

        viewModel.regionsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {
                }
                is Outcome.Next -> {
                    regionAdapter.addList(it.data)
                }
                is Outcome.Success -> {
                }
                is Outcome.Failure -> {
                }
            }
        })

        viewModel.getRegions()

        //viewModel.getAllRegionsFlow().asLiveData(lifecycleScope.coroutineContext)
        //    .observe(viewLifecycleOwner, {
        //        when (it) {
        //            is Outcome.Progress -> {
        //            }
        //            is Outcome.Next -> {
        //            }
        //            is Outcome.Success -> {
        //                regionAdapter.refresh(it.data)
        //            }
        //            is Outcome.Failure -> {
        //            }
        //        }
        //    })
    }
}