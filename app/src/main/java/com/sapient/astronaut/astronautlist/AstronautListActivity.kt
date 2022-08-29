package com.sapient.astronaut.astronautlist

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapient.astronaut.AstronautApplication
import com.sapient.astronaut.R
import kotlinx.android.synthetic.main.astronaut_list.*
import javax.inject.Inject

class AstronautListActivity : AppCompatActivity(), AstronautListView {

    @Inject
    lateinit var astronautAdapter: AstronautAdapter

    @Inject
    lateinit var presenter: AstronautListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AstronautApplication).appComponent.astronautListComponent().create().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.astronaut_list)
        swiperefresh.setOnRefreshListener { getAstronauts()
        }
        setRecyclerView()
        presenter.setView(this)
        getAstronauts()
    }

    private fun getAstronauts() {
        displaySwipeRefresh()
        presenter.getAstronauts()
    }

    private fun setRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = astronautAdapter
        }

    }

    override fun updateAdapter() {
        astronautAdapter.notifyDataSetChanged()
    }

    override fun displayError() {
        var alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle(getString(R.string.error_title))
        alertBuilder.setMessage(getString(R.string.error_message))
        alertBuilder.setPositiveButton(getString(R.string.ok_button), null)
        alertBuilder.show()
    }

    private fun displaySwipeRefresh(){
        swiperefresh.isRefreshing = true
    }

    override fun dismissSwipeRefresh(){
        swiperefresh.isRefreshing = false
    }


}