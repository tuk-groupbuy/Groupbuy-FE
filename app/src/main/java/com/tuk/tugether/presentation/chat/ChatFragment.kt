package com.tuk.tugether.presentation.chat

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentChatBinding
import com.tuk.tugether.domain.model.request.CreateChatRequestModel
import com.tuk.tugether.presentation.chat.adapter.ChatRoomRVA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment: BaseFragment<FragmentChatBinding>(R.layout.fragment_chat) {
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatRoomRVA: ChatRoomRVA

    override fun initObserver() {
        viewModel.chatRoomList.observe(viewLifecycleOwner) { chatRoomList ->
            chatRoomRVA.submitList(chatRoomList)
        }
    }

    override fun initView() {
        initRecyclerView()

//        val userId = 6L // 로그인 전 하드코딩된 ID
//        val request = CreateChatRequestModel(userId)
//        viewModel.fetchChatRoomList(request)

        val userId = 6L // 로그인 전 하드코딩된 ID
        viewModel.fetchChatRoomList(userId)

        binding.tvGotochat.setOnClickListener {
            val intent = Intent(requireContext(), ChatRoomActivity::class.java)
            val chatRoomId = intent.putExtra("chatRoomId", 5L) // 임시로 5L 전달
            Log.d("chatFragment", "ChatFragment -> ChatRoom $chatRoomId")
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        chatRoomRVA = ChatRoomRVA { chatRoomId ->
            val intent = Intent(requireContext(), ChatRoomActivity::class.java).apply {
                putExtra("chatRoomId", chatRoomId)
                Log.d("chatFragment", "ChatFragment -> ChatRoom $chatRoomId")
            }
            startActivity(intent)
        }

        binding.rvChatList.apply {
            adapter = chatRoomRVA
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onResume() {
        super.onResume()
        // 화면 다시 보일 때 리스트 최신화
        viewModel.fetchChatRoomList(6L)
    }
}