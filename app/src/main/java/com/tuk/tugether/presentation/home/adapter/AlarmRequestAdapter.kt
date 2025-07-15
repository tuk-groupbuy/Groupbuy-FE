package com.tuk.tugether.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuk.tugether.databinding.ItemAlarmRequestBinding

class AlarmRequestAdapter(
    private val requests: List<AlarmRequest>
) : RecyclerView.Adapter<AlarmRequestAdapter.RequestViewHolder>() {

    inner class RequestViewHolder(private val binding: ItemAlarmRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(request: AlarmRequest) {
            binding.tvAlarmRequestTitle.text = request.name

            Glide.with(binding.root.context)
                .load(request.imageUrl)
                .into(binding.ivAlarmRequestImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = ItemAlarmRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bind(requests[position])
    }

    override fun getItemCount(): Int = requests.size
}
