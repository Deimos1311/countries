package com.example.test_app.fragments.list_of_capitals
//todo design recyclerview
//todo do some new recyclerview to consolidate knowledge
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.outcome.Outcome
import com.example.test_app.databinding.FragmentListOfCapitalsBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class ListOfCapitalsFragment : ScopeFragment() {

    //Binding
    private var binding: FragmentListOfCapitalsBinding? = null

    //Adapter
    lateinit var listOfCapitalsAdapter: ListOfCapitalsAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel: ListOfCapitalsViewModel by stateViewModel()

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

        listOfCapitalsAdapter = ListOfCapitalsAdapter()
        binding?.recyclerViewCapitals?.adapter = listOfCapitalsAdapter

        viewModel.getCapitals()
        viewModel.dataCapitalsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {
                    if (it.loading) showProgress() else hideProgress()
                }
                is Outcome.Next -> {
                    listOfCapitalsAdapter.refresh(it.data)
                    hideProgress()
                }
                is Outcome.Success -> {
                }
                is Outcome.Failure -> {
                }
            }
        })

        binding?.swipeRefreshCapitals?.setOnRefreshListener {
            viewModel.getCapitals()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showProgress() {
        //binding?.swipeRefresh?.isRefreshing = false
        binding?.progressBar?.isVisible = true
        binding?.frameWithProgress?.isVisible = true
    }

    private fun hideProgress() {
        //binding?.swipeRefresh?.isRefreshing = true
        binding?.progressBar?.isVisible = false
        binding?.frameWithProgress?.isVisible = false
    }
}