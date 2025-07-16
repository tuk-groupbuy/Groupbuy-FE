package com.tuk.tugether.presentation.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuk.tugether.R
import com.tuk.tugether.databinding.ItemChatParticipantBinding
import com.tuk.tugether.domain.model.response.ParticipantListResponseModel

class ChatParticipantRVA(

): ListAdapter<ParticipantListResponseModel.ChatRoomUserModel, ChatParticipantRVA.ChatParticipantViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatParticipantViewHolder {
        val binding = ItemChatParticipantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatParticipantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatParticipantRVA.ChatParticipantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChatParticipantViewHolder(private val binding: ItemChatParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParticipantListResponseModel.ChatRoomUserModel) {
            binding.tvReceiverNickname.text = item.userId.toString()

            binding.ivReceiverProfile.setImageResource(R.drawable.ic_person)

//            Glide.with(binding.ivReceiverProfile.context)
//                .load(item.)
//                .into(binding.ivReceiverProfile)

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ParticipantListResponseModel.ChatRoomUserModel>() {
        override fun areItemsTheSame(oldItem: ParticipantListResponseModel.ChatRoomUserModel, newItem: ParticipantListResponseModel.ChatRoomUserModel): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: ParticipantListResponseModel.ChatRoomUserModel, newItem: ParticipantListResponseModel.ChatRoomUserModel): Boolean {
            return oldItem == newItem
        }
    }
}