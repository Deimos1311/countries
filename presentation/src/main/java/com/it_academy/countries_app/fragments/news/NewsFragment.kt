package com.it_academy.countries_app.fragments.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.it_academy.countries_app.databinding.FragmentNewsBinding
import com.it_academy.domain.outcome.Outcome
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class NewsFragment : ScopeFragment() {

    var binding: FragmentNewsBinding? = null

    lateinit var adapter: NewsAdapterDiff
    lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel: NewsViewModel by stateViewModel()

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

        viewModel.getAllNews().asLiveData(lifecycleScope.coroutineContext)
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Outcome.Progress -> {
                    }
                    is Outcome.Next -> {
                    }
                    is Outcome.Success -> {
                        adapter.submitList(it.data)
                    }
                    is Outcome.Failure -> {
                    }
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}