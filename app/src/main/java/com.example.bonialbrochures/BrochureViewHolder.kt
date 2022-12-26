package com.example.bonialbrochures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.bonialbrochures.data.local.model.BrochureEntity
import com.example.bonialbrochures.databinding.ItemBrochureBinding

class BrochureViewHolder(private val binding: ItemBrochureBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun newInstance(parent: ViewGroup): BrochureViewHolder {
            return BrochureViewHolder(
                ItemBrochureBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(brochure: BrochureEntity) {
        if (brochure.brochureImage!!.isEmpty()) {
            Glide.with(itemView)
                .load(R.drawable.ic_place_holder)
                .transform(CenterCrop(), RoundedCorners(26))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivBrochure)
        } else {
            Glide.with(itemView)
                .load(brochure.brochureImage)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_place_holder)
                .transform(CenterCrop(), RoundedCorners(26))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivBrochure)
        }
        binding.tvRetailerName.text = brochure.retailerName
    }
}