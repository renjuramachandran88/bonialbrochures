package com.example.bonialbrochures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.bonialbrochures.data.local.model.BrochureEntity
import com.example.bonialbrochures.databinding.ItemBrochureBinding

class BrochureAdapter : ListAdapter<BrochureEntity, BrochureViewHolder>(BrochureComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrochureViewHolder {
        val binding =
            ItemBrochureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrochureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrochureViewHolder, position: Int) {
        val currentBrochure = getItem(position)
        if (currentBrochure != null) {
            holder.bind(currentBrochure)
        }
    }

    class BrochureComparator : DiffUtil.ItemCallback<BrochureEntity>() {
        override fun areItemsTheSame(oldItem: BrochureEntity, newItem: BrochureEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BrochureEntity, newItem: BrochureEntity): Boolean {
            return oldItem.brochureImage == newItem.brochureImage
        }
    }
}