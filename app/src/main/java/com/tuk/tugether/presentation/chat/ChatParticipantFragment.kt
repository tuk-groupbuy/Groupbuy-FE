package com.tuk.tugether.presentation.chat

import android.view.View
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentChatParticipantBinding
import com.tuk.tugether.presentation.base.BaseFragment

class ChatParticipantFragment: BaseFragment<FragmentChatParticipantBinding>(R.layout.fragment_chat_participant) {

    override fun initObserver() {

    }

    override fun initView() {
        binding.ivChatParticipantBackBtn.setOnClickListener {
            (activity as? ChatRoomActivity)?.findViewById<View>(R.id.layout_read_coordinator)?.visibility = View.VISIBLE
            (activity as? ChatRoomActivity)?.findViewById<View>(R.id.layout_chat_send)?.visibility = View.VISIBLE

            parentFragmentManager.popBackStack()
        }
    }
}