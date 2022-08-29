package com.sapient.astronaut.astronautlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sapient.astronaut.R
import com.sapient.astronaut.model.Astronaut
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.astronaut.view.*
import javax.inject.Inject


class AstronautAdapter @Inject constructor (private val presenter: AstronautListPresenter) : RecyclerView.Adapter<AstronautAdapter.AstronautViewHolder>() {

    var onItemClick: ((Astronaut) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AstronautViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.astronaut, parent, false)
        return AstronautViewHolder(view)
    }

    override fun getItemCount(): Int {
        return presenter.astronautList?.count() ?: 0
    }

    override fun onBindViewHolder(holder: AstronautViewHolder, position: Int) {
        holder.bindFilter(presenter.getAstronautAtPosition(position))
    }

    inner class AstronautViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindFilter(astronaut: Astronaut) {
            itemView.nameTextView.text = astronaut.name
            itemView.nationalityTextView.text = astronaut.nationality
            Picasso.get()
                .load(astronaut.profileThumbnailImage)
                .placeholder(R.drawable.noimage)
                .into(itemView.imageView)

            itemView.setOnClickListener{
                onItemClick?.invoke(astronaut)
            }
        }



    }
}