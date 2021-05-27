package com.codedamon.myworkshops.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codedamon.myworkshops.R
import com.codedamon.myworkshops.model.Workshop
import com.google.android.material.chip.Chip

class WorkshopAdapter(
    val context: Context,
    private val isMyWorkshop: Boolean,
    private val mInterface: WorkshopDetailInterface
) : RecyclerView.Adapter<WorkshopAdapter.WorkshopViewHolder>() {

    private val allWorkshop = ArrayList<Workshop>()

    interface WorkshopDetailInterface {
        fun onApplyClicked(name: String);
    }

    inner class WorkshopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.workshop_image)
        val place: Chip = itemView.findViewById(R.id.workshop_place)
        val name: TextView = view.findViewById(R.id.workshop_name)
        val by: TextView = itemView.findViewById(R.id.workshop_by)
        val dateTime: TextView = itemView.findViewById(R.id.workshop_data_time)
        val applyBtn: Button = view.findViewById(R.id.apply_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkshopViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workshop_rv, parent, false)
        return WorkshopViewHolder(itemView)
    }

    override fun getItemCount(): Int = allWorkshop.size

    override fun onBindViewHolder(holder: WorkshopViewHolder, position: Int) {
        val currentWorkshop = allWorkshop[position]

        Glide.with(context)
            .load(currentWorkshop.img)
            .into(holder.image)

        holder.place.text = currentWorkshop.place
        holder.name.text = currentWorkshop.name
        holder.by.text = currentWorkshop.by
        holder.dateTime.text = currentWorkshop.date_time

        if (isMyWorkshop)
            holder.applyBtn.visibility = View.GONE

        holder.applyBtn.setOnClickListener {
            if (currentWorkshop.applied)
                Toast.makeText(context, "Workshop Already Applied", Toast.LENGTH_SHORT).show()
            else {
                mInterface.onApplyClicked(currentWorkshop.name)
            }
        }
    }

    fun updateList(newList: List<Workshop>) {
        allWorkshop.clear()

        if (isMyWorkshop) {
            val filteredList = newList.filter {
                it.applied
            }
            allWorkshop.addAll(filteredList)
        } else {
            allWorkshop.addAll(newList)
        }
        notifyDataSetChanged()
    }
}
