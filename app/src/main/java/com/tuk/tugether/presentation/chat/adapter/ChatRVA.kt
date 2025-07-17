package com.tuk.tugether.presentation.chat.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuk.tugether.R
import com.tuk.tugether.databinding.ItemChatTimeStampBinding
import com.tuk.tugether.databinding.ItemReceiverChatBinding
import com.tuk.tugether.databinding.ItemSenderChatBinding
import com.tuk.tugether.domain.model.response.ChatMessageResponseModel
import com.tuk.tugether.util.extension.toListViewingPartyDateFormat
import com.tuk.tugether.util.extension.toTimeFormat


class ChatRVA :
    ListAdapter<ChatMessageResponseModel.ChatMessageModel, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            SENDER -> SenderChatViewHolder(ItemSenderChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            RECEIVER -> ReceiverChatViewHolder(ItemReceiverChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            TIME_STAMP -> TimeStampViewHolder(ItemChatTimeStampBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalStateException("Invalid position")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SenderChatViewHolder -> {
                holder.bind(currentList[position])
            }
            is ReceiverChatViewHolder -> {
                holder.bind(currentList[position])
            }
            is TimeStampViewHolder -> {
                holder.bind(currentList[position])
            }
        }
    }

    inner class SenderChatViewHolder(private val binding: ItemSenderChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatMessageResponseModel.ChatMessageModel) {
            with(binding){
                tvSendererChat.text = chatItem.content
                tvTimeStamp.text = chatItem.sendAt.toTimeFormat()

                if(chatItem.isLastIndex){
                    tvDate.visibility = View.VISIBLE
                    tvDate.text = chatItem.sendAt.toListViewingPartyDateFormat()
                }

                runCatching {
                    currentList[bindingAdapterPosition + 1]
                }.onSuccess {
                    if (chatItem.sendAt.toListViewingPartyDateFormat() != currentList[bindingAdapterPosition + 1].sendAt.toListViewingPartyDateFormat()) {
                        tvDate.visibility = View.VISIBLE
                        tvDate.text = chatItem.sendAt.toListViewingPartyDateFormat()
                        Log.d("seceer" ,"$bindingAdapterPosition ${chatItem.sendAt}")
                    }
                }.onFailure {
                    Log.d("seceer", "$it")
                }
            }
        }
    }

    inner class ReceiverChatViewHolder(private val binding: ItemReceiverChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatMessageResponseModel.ChatMessageModel) {
            with(binding){
                tvReceiverChat.text = chatItem.content
                ivReceiverProfile.setImageResource(R.drawable.ic_ex_person)
//                Glide.with(ivReceiverProfile.context)
//                    .load(chatItem.receiverProfileImage)
//                    .into(ivReceiverProfile)

                tvReceiverNickname.text = chatItem.sender
                tvTimeStamp.text = chatItem.sendAt.toTimeFormat()

                if(chatItem.isLastIndex){
                    tvDate.visibility = View.VISIBLE
                    tvDate.text = chatItem.sendAt.toListViewingPartyDateFormat()
                }

                runCatching {
                    currentList[bindingAdapterPosition + 1]
                }.onSuccess {
                    if (chatItem.sendAt.toListViewingPartyDateFormat() != currentList[bindingAdapterPosition + 1].sendAt.toListViewingPartyDateFormat()) {
                        tvDate.visibility = View.VISIBLE
                        tvDate.text = chatItem.sendAt.toListViewingPartyDateFormat()
                        Log.d("receer", "$bindingAdapterPosition ${chatItem.sendAt}")
                    }
                }.onFailure {
                    Log.d("receer", "$it")
                }
            }
        }
    }

    inner class TimeStampViewHolder(private val binding: ItemChatTimeStampBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatMessageResponseModel.ChatMessageModel) {
            with(binding){
                tv.text = chatItem.content
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }

    class DiffCallback : DiffUtil.ItemCallback<ChatMessageResponseModel.ChatMessageModel>() {
        override fun areItemsTheSame(oldItem: ChatMessageResponseModel.ChatMessageModel, newItem: ChatMessageResponseModel.ChatMessageModel) =
            oldItem.sendAt == newItem.sendAt
        override fun areContentsTheSame(oldItem: ChatMessageResponseModel.ChatMessageModel, newItem: ChatMessageResponseModel.ChatMessageModel) =
            oldItem == newItem
    }

    companion object {
        const val SENDER = 0
        const val RECEIVER = 1
        const val TIME_STAMP = 2
    }
}