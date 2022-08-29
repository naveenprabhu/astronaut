package com.sapient.astronaut.astronautlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapient.astronaut.AstronautApplication
import com.sapient.astronaut.R
import com.sapient.astronaut.astronautdetail.AstronautDetailActivity
import com.sapient.astronaut.utils.Constants.ASTRONAUT_ID
import kotlinx.android.synthetic.main.astronaut_list.*
import javax.inject.Inject


class AstronautListActivity : AppCompatActivity(), AstronautListView {

    @Inject
    lateinit var astronautAdapter: AstronautAdapter

    @Inject
    lateinit var presenter: AstronautListPresenter

    var isSortOrderAscending: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AstronautApplication).appComponent.astronautListComponent().create().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.astronaut_list)
        swiperefresh.setOnRefreshListener { getAstronauts()
        }
        setSupportActionBar(findViewById(R.id.toolbar))
        setRecyclerView()
        presenter.setView(this)
        getAstronauts()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
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

        astronautAdapter.onItemClick = {astronautyee ->
            var intent = Intent(this@AstronautListActivity, AstronautDetailActivity::class.java).apply {
                putExtra(ASTRONAUT_ID, astronautyee.id)
            }
            this.startActivity(intent)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_sort) {
            sortByName()
        }
        return super.onOptionsItemSelected(item)
    }

    fun sortByName() {
        isSortOrderAscending = !isSortOrderAscending
        presenter.sortByName(isSortOrderAscending)
    }

}