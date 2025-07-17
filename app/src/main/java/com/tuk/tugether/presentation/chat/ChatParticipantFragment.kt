package com.tuk.tugether.presentation.chat

import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentChatParticipantBinding
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.presentation.chat.adapter.ChatParticipantRVA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatParticipantFragment: BaseFragment<FragmentChatParticipantBinding>(R.layout.fragment_chat_participant) {
    private val viewModel: ChatViewModel by activityViewModels()
    private lateinit var chatParticipantRVA: ChatParticipantRVA

    override fun initObserver() {
        viewModel.participantList.observe(viewLifecycleOwner) { chatRoomList ->
            chatParticipantRVA.submitList(chatRoomList)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.chatRoomId.collect { chatRoomId ->
                    if (chatRoomId != -1L) {
                        viewModel.fetchParticipantList(chatRoomId)
                    }
                }
            }
        }
    }

    override fun initView() {
        initRecyclerView()

        binding.ivChatParticipantBackBtn.setOnClickListener {
            (activity as? ChatRoomActivity)?.findViewById<View>(R.id.layout_read_coordinator)?.visibility = View.VISIBLE
            (activity as? ChatRoomActivity)?.findViewById<View>(R.id.layout_chat_send)?.visibility = View.VISIBLE

            parentFragmentManager.popBackStack()
        }
        viewModel.setChatRoomId(5L)
    }

    private fun initRecyclerView() {
        chatParticipantRVA = ChatParticipantRVA()

        binding.rvChatList.apply {
            adapter = chatParticipantRVA
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}