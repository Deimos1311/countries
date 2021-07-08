package com.example.test_app

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.test_app.adapter.CustomAdapter
import com.example.test_app.common.Common
import com.example.test_app.model.Country
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Response

class FirstFragment : Fragment()/*, View.OnClickListener*/ {
    var listOfCountries: MutableList<Country>? = null

    /*
    lateinit var fastButton: Button
*/
    lateinit var toolbar: Toolbar
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
        val retrofitData = Common.retrofitService?.getCountryDate()
        val goodSnack: Snackbar = Snackbar.make(
            requireView(), resources.getString(R.string.done_message), Snackbar.LENGTH_SHORT
        )
        val badSnack: Snackbar = Snackbar.make(
            requireView(), resources.getText(R.string.connect_error_message), Snackbar.LENGTH_LONG
        )
        badSnack.setAction(R.string.bad_snack_info) {
            Toast.makeText(context, getString(R.string.bad_snack_inside), Toast.LENGTH_SHORT)
                .show()
        }

        retrofitData?.enqueue(object : retrofit2.Callback<MutableList<Country>?> {
            override fun onResponse(
                call: Call<MutableList<Country>?>,
                response: Response<MutableList<Country>?>
            ) {
                listOfCountries = response.body()

                customAdapter = CustomAdapter(listOfCountries)
                customAdapter.notifyDataSetChanged()
                recyclerView.adapter = customAdapter
                goodSnack.show()
            }

            override fun onFailure(call: Call<MutableList<Country>?>, t: Throwable) {
                badSnack.show()
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
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Sort().sorting(customAdapter, listOfCountries, item)
        return super.onOptionsItemSelected(item)
    }

// Прошлое задание, по сортировке в 2 кнопки
// В след мердже удалю

/*        when(item.itemId) {
            R.id.descending_sort -> listOfCountries?.sortBy { it.population }
            R.id.ascending_sort -> listOfCountries?.sortByDescending { it.population }
        }
        customAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }*/

    //todo scroll
/*    override fun onClick(v: View) {
        fastButton = v.findViewById(R.id.fast_button)
        recyclerView.smoothScrollToPosition(recyclerView.size)
        recyclerView.smoothScrollToPosition(recyclerView.size)

    }*/
}

