package com.tuk.tugether.presentation.chat

import android.content.Intent
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment: BaseFragment<FragmentChatBinding>(R.layout.fragment_chat) {

    override fun initObserver() {

    }

    override fun initView() {
        binding.tvGotochat.setOnClickListener {
            val intent = Intent(requireContext(), ChatRoomActivity::class.java)
            startActivity(intent)
        }
    }
}