package com.sapient.astronaut.astronautdetail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sapient.astronaut.AstronautApplication
import com.sapient.astronaut.R
import com.sapient.astronaut.model.Astronaut
import com.sapient.astronaut.utils.Constants.ASTRONAUT_ID
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.astronaut_detail.*
import javax.inject.Inject


class AstronautDetailActivity: AppCompatActivity(), AstronautDetailView {

    @Inject
    lateinit var presenter: AstronautDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AstronautApplication).appComponent.astronautDetailComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.astronaut_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.setView(this)

        val astronautId : Int =intent.getIntExtra(ASTRONAUT_ID, 0)
        presenter.getAstronautDetail(astronautId)
    }

    override fun updateAstronautDetails(astronaut: Astronaut) {
        nameDetailTextView.text = astronaut.name
        nationalityDetailTextView.text = astronaut.nationality
        dobDetailTextView.text = astronaut.dateOfBirth
        bioDetailTextView.text = astronaut.bio
        Picasso.get()
            .load(astronaut.profileImage)
            .placeholder(R.drawable.noimage)
            .into(astronautDetailImageView)
    }

    override fun updateAstronautDetailViewVisibility() {
        astronautDetailGroup.visibility = View.VISIBLE
        progressLoader.visibility = View.GONE
    }

    override fun displayError() {
        var alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle(getString(R.string.error_title))
        alertBuilder.setMessage(getString(R.string.error_message))
        alertBuilder.setPositiveButton(getString(R.string.ok_button)) { _, _ ->
            finish()
        }
        alertBuilder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

