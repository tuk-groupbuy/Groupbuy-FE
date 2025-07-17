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
            binding.tvAlarmRequestTitle.text = "${request.senderNickname}"

            // 예시 이미지
            Glide.with(binding.root.context)
                .load("https://via.placeholder.com/100/888888/FFFFFF?text=User")
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
