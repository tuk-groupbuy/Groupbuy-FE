package com.tuk.tugether.presentation.chat

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentChatParticipantBinding
import com.tuk.tugether.domain.model.request.CommonChatRequestModel
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

        binding.tvChatParticipantExitBtn.setOnClickListener {
            val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val userId = prefs.getString("user_id", null)?.toLongOrNull()

            if (userId == null) {
                Log.e("ChatParticipantFragment", "userId가 null이거나 Long으로 변환할 수 없습니다.")
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.chatRoomId.collect { chatRoomId ->
                    if (chatRoomId != -1L) {
                        val request = CommonChatRequestModel(chatRoomId, userId)
                        viewModel.fetchExitChatRoom(request)
                        return@collect  // 한 번만 실행되도록 종료
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        chatParticipantRVA = ChatParticipantRVA()

        binding.rvChatList.apply {
            adapter = chatParticipantRVA
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}