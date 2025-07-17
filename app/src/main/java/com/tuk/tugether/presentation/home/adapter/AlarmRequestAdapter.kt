package com.tuk.tugether.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuk.tugether.databinding.ItemAlarmRequestBinding

class AlarmRequestAdapter(
    private val requests: MutableList<AlarmRequest>,
    private val onApprove: (postId: Long, userId: Long) -> Unit,
    private val onReject: (postId: Long, userId: Long) -> Unit
) : RecyclerView.Adapter<AlarmRequestAdapter.RequestViewHolder>() {

    inner class RequestViewHolder(private val binding: ItemAlarmRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(request: AlarmRequest) {
            binding.tvAlarmRequestTitle.text = request.senderNickname

            Glide.with(binding.root.context)
                .load("https://via.placeholder.com/100/888888/FFFFFF?text=User")
                .into(binding.ivAlarmRequestImage)

            binding.tvAlarmRequestApproval.setOnClickListener {
                onApprove(request.postId, request.userId)
                removeItem(adapterPosition) // ✅ 아이템 제거
            }

            binding.tvAlarmRequestDenial.setOnClickListener {
                onReject(request.postId, request.userId)
                removeItem(adapterPosition) // ✅ 아이템 제거
            }
        }
    }

    private fun removeItem(position: Int) {
        if (position != RecyclerView.NO_POSITION && position < requests.size) {
            requests.removeAt(position)
            notifyItemRemoved(position) // ✅ 애니메이션 포함한 자연스러운 제거
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
