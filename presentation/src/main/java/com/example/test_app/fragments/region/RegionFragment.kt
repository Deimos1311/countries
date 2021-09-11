package com.example.test_app.fragments.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.outcome.Outcome
import com.example.test_app.databinding.FragmentRegionBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class RegionFragment : ScopeFragment() {

    var binding: FragmentRegionBinding? = null
    lateinit var regionAdapter: RegionAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel: RegionViewModel by stateViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        viewModel.getAllRegionsFlow().asLiveData(lifecycleScope.coroutineContext)
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Outcome.Progress -> {
                    }
                    is Outcome.Next -> {
                    }
                    is Outcome.Success -> {
                        regionAdapter.refresh(it.data)
                    }
                    is Outcome.Failure -> {
                    }
                }
            })
    }
}