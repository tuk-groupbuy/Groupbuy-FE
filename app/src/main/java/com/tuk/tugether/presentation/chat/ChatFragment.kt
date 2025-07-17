package com.tuk.tugether.presentation.chat

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        bottomNavigationShow()
        viewModel.chatRoomList.observe(viewLifecycleOwner) { chatRoomList ->
            chatRoomRVA.submitList(chatRoomList)
        }
    }

    override fun initView() {
        initRecyclerView()

        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userIdStr = prefs.getString("user_id", null)

        userIdStr?.toLongOrNull()?.let { userId ->
            viewModel.fetchChatRoomList(userId)
        } ?: run {
            Log.e("ChatFragment", "user_id is null or invalid")
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
        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userIdStr = prefs.getString("user_id", null)
        val nickname = prefs.getString("user_nickname", null)

        Log.d("ChatFragment", "user_id: $userIdStr, nickname: $nickname")

        userIdStr?.toLongOrNull()?.let { userId ->
            viewModel.fetchChatRoomList(userId)
        }
    }

    // BottomNavigationView 보이기
    private fun bottomNavigationShow() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.VISIBLE
    }
}