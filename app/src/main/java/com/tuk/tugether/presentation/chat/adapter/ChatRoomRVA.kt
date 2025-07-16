package com.tuk.tugether.presentation.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuk.tugether.databinding.ItemChatRoomListBinding
import com.tuk.tugether.domain.model.response.ChatListResponseModel

class ChatRoomRVA(
    private val onClick: (Long) -> Unit
): ListAdapter<ChatListResponseModel.ChatRoomModel, ChatRoomRVA.ChatRoomViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        val binding = ItemChatRoomListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatRoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatRoomRVA.ChatRoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChatRoomViewHolder(private val binding: ItemChatRoomListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatListResponseModel.ChatRoomModel) {
            binding.tvChatRoomName.text = item.title
            binding.tvChatRoomRecentMessage.text = item.lastChatMessage

            Glide.with(binding.ivChatRoomThumbnail.context)
                .load(item.imageUrl)
                .into(binding.ivChatRoomThumbnail)

            binding.root.setOnClickListener {
                onClick(item.chatRoomId)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ChatListResponseModel.ChatRoomModel>() {
        override fun areItemsTheSame(oldItem: ChatListResponseModel.ChatRoomModel, newItem: ChatListResponseModel.ChatRoomModel): Boolean {
            return oldItem.chatRoomId == newItem.chatRoomId
        }

        override fun areContentsTheSame(oldItem: ChatListResponseModel.ChatRoomModel, newItem: ChatListResponseModel.ChatRoomModel): Boolean {
            return oldItem == newItem
        }
    }
}