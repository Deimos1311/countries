package com.it_academy.countries_app.fragments.list_of_capitals

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.it_academy.countries_app.R
import com.it_academy.countries_app.databinding.FragmentListOfCapitalsBinding
import com.it_academy.domain.outcome.Outcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class ListOfCapitalsFragment : ScopeFragment() {

    private var binding: FragmentListOfCapitalsBinding? = null

    lateinit var listOfCapitalsAdapterDiff: ListOfCapitalsAdapterDiff
    lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel: ListOfCapitalsViewModel by stateViewModel()
    private lateinit var sharedFlowJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListOfCapitalsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        return binding?.root
    }

    private fun initRecyclerView(view: View) {

        linearLayoutManager = LinearLayoutManager(activity)
        binding?.recyclerViewCapitals?.setHasFixedSize(true)
        binding?.recyclerViewCapitals?.layoutManager = linearLayoutManager

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding?.recyclerViewCapitals?.addItemDecoration(decoration)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        sharedFlowJob = Job()

        listOfCapitalsAdapterDiff = ListOfCapitalsAdapterDiff()
        binding?.recyclerViewCapitals?.adapter = listOfCapitalsAdapterDiff

        listOfCapitalsAdapterDiff.setItemClick { viewModel.clickOnItem() }

        getCapitalsFromAPI()

        CoroutineScope(lifecycleScope.coroutineContext + sharedFlowJob).launch {
            viewModel.sharedFlow.collect {
                findNavController().navigate(R.id.action_listOfCapitalsFragment_to_mapFragment)
            }
        }

        binding?.swipeRefresh?.setOnRefreshListener {
            getCapitalsFromAPI()
        }
    }

    private fun getCapitalsFromAPI() {
        viewModel.getCapitals().asLiveData(lifecycleScope.coroutineContext)
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Outcome.Progress -> {
                        if (it.loading) showProgress() else hideProgress()
                    }
                    is Outcome.Next -> {
                    }
                    is Outcome.Success -> {
                        listOfCapitalsAdapterDiff.submitList(it.data)
                    }
                    is Outcome.Failure -> {
                    }
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_capitals_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchButton = menu.findItem(R.id.search).actionView as SearchView

        searchButton.queryHint = getString(R.string.search_by_capital)

        searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getCapitalByName(newText).asLiveData(lifecycleScope.coroutineContext)
                    .observe(
                        viewLifecycleOwner, {
                            if (it is Outcome.Success) {
                                listOfCapitalsAdapterDiff.submitList(it.data)
                            }
                        })
                return false
            }
        })

        searchButton.setOnCloseListener {
            viewModel.getCapitals().asLiveData(lifecycleScope.coroutineContext)
                .observe(viewLifecycleOwner, {
                    if (it is Outcome.Success) {
                        listOfCapitalsAdapterDiff.submitList(it.data)
                    }
                })
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        sharedFlowJob.cancel()
    }

    private fun showProgress() {
        if (binding?.swipeRefresh?.isRefreshing == false) {
            binding?.progressBar?.isVisible = true
            binding?.frameWithProgress?.isVisible = true
        }
    }

    private fun hideProgress() {
        binding?.swipeRefresh?.isRefreshing = false
        binding?.progressBar?.isVisible = false
        binding?.frameWithProgress?.isVisible = false
    }
}