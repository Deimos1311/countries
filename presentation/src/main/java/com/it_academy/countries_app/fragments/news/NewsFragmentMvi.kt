package com.it_academy.countries_app.fragments.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.it_academy.countries_app.base.mvi.BaseFragment
import com.it_academy.countries_app.databinding.FragmentNewsBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class NewsFragmentMvi : BaseFragment<NewsIntent, NewsAction, NewsState, NewsViewModelMvi>() {

    private var binding: FragmentNewsBinding? = null

    lateinit var adapter: NewsAdapterDiff
    lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel: NewsViewModelMvi by stateViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding?.root
    }

    private fun initRecyclerView(view: View) {
        linearLayoutManager = LinearLayoutManager(activity)
        binding?.recyclerViewNews?.setHasFixedSize(true)
        binding?.recyclerViewNews?.layoutManager = linearLayoutManager

        val decoration = DividerItemDecoration(context, VERTICAL)
        binding?.recyclerViewNews?.addItemDecoration(decoration)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

        adapter = NewsAdapterDiff()
        binding?.recyclerViewNews?.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner, {
            render(it)
        })
    }

    override fun initData() {
        viewModel.dispatchIntent(NewsIntent.LoadAllNews)
    }

    override fun render(state: NewsState) {
        when (state) {
            is NewsState.Loading -> if (state.loading) showProgress() else hideProgress()
            is NewsState.ResultAllNews -> if (state.data.isNotEmpty()) {
                adapter.submitList(state.data)
            } else {
                Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
            }
            is NewsState.Exception -> state.ex
        }
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

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}