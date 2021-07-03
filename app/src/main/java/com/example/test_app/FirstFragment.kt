package com.example.test_app

import android.icu.lang.UCharacter
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun
import androidx.core.graphics.red
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.test_app.adapter.CustomAdapter
import com.example.test_app.common.Common
import com.example.test_app.model.Country
import retrofit2.Call
import retrofit2.Response

class FirstFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var customAdapter: CustomAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

        getCountries()
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.rView)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        val decoration = DividerItemDecoration(context, VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    private fun getCountries() {
        val retrofitData = Common.retrofitService.getCountryDate()
        val badToast = Toast.makeText(context, resources.getText(R.string.connect_error_message),
            Toast.LENGTH_SHORT)
        val goodToast = Toast.makeText(context, resources.getString(R.string.done_message),
            Toast.LENGTH_SHORT)

        retrofitData.enqueue(object : retrofit2.Callback<MutableList<Country>?> {
            override fun onResponse(
                call: Call<MutableList<Country>?>,
                response: Response<MutableList<Country>?>
            ) {
                val responseBody = response.body()

                customAdapter = CustomAdapter(responseBody!!)
                customAdapter.notifyDataSetChanged()
                recyclerView.adapter = customAdapter
                goodToast.show()
            }

            override fun onFailure(call: Call<MutableList<Country>?>, t: Throwable) {
                badToast.show()
                getCountries()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        initRecyclerView(view)
        return view
    }
}