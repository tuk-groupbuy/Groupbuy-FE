package com.tuk.tugether.presentation.chat

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.tuk.tugether.R
import com.tuk.tugether.databinding.ActivityChatRoomBinding
import com.tuk.tugether.presentation.base.BaseActivity
import com.tuk.tugether.presentation.chat.adapter.ChatRVA
import com.tuk.tugether.util.extension.KeyboardUtil
import com.tuk.tugether.util.extension.repeatOnStarted
import com.tuk.tugether.util.network.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatRoomActivity: BaseActivity<ActivityChatRoomBinding>(R.layout.activity_chat_room) {
    private val viewModel: ChatViewModel by viewModels()
    private val chatRVA by lazy {
        ChatRVA()
    }
    private var isFirst = true

    override fun initObserver() {
        repeatOnStarted {
            viewModel.chatEvent.collect { state ->
                when (state) {
                    is UiState.Success -> handleChatEvent(state.data)
//                    is UiState.Failure -> {
//                        showCustomSnackBar(binding.root, state.msg)
//                    }
                    else -> Unit
                }
            }
        }
        repeatOnStarted {
            viewModel.chatRoomId.collect{ id ->
                if (id == -1L) { return@collect }
//                initWsClient()
                viewModel.fetchChatMessage()
            }
        }

    }

    override fun initView() {
        initChatRVAdapter()

        onBackPressedDispatcher.addCallback(this) {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.chat_fragment_container)
            if (currentFragment is ChatParticipantFragment) {
                binding.layoutReadCoordinator.visibility = View.VISIBLE
                binding.layoutChatSend.visibility = View.VISIBLE
                supportFragmentManager.popBackStack()
            } else {
                finish()
            }
        }

        binding.ivChatBackBtn.setOnClickListener {
            finish()
        }

        binding.ivChatMenuBtn.setOnClickListener {
            // 채팅 UI 숨김
            binding.layoutReadCoordinator.visibility = View.GONE
            binding.layoutChatSend.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .replace(R.id.chat_fragment_container, ChatParticipantFragment())
                .addToBackStack(null)
                .commit()
        }

        val chatRoomId = intent.getLongExtra("chatRoomId", -1L)
        Log.d("ChatRoomActivity", "받은 chatRoomId: $chatRoomId")
        if (chatRoomId != -1L) {
            viewModel.setChatRoomId(chatRoomId)
        }

        binding.rvChat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (totalItemCount - lastVisibleItemPosition <= 2) {
                        viewModel.fetchChatMessage()
                    }
                }
            }
        })

        chatRVA.registerAdapterDataObserver(object : AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if(positionStart == 0){
                    binding.rvChat.scrollToPosition(0)
                }
            }
        })
        KeyboardUtil.registerRecyclerViewKeyboardVisibilityListener(binding.root, binding.rvChat)
    }

    private fun initChatRVAdapter() {
        binding.rvChat.adapter = chatRVA
        binding.rvChat.itemAnimator = null
    }

    @SuppressLint("SetTextI18n")
    private fun handleChatEvent(event: ChatViewModel.ChatEvent){
        when(event){
            is ChatViewModel.ChatEvent.CreateChatRoom -> {
                with(event.result) {
                    binding.tvChatTitle.text = "채팅방"
                }
            }
            is ChatViewModel.ChatEvent.FetchChatLog -> {
                Log.d("chat_log", event.chatLog.messages.toString())
//                binding.tvChatWriter.text = event.chatLog.receiverName.combineNicknameAndTeam(event.chatLog.receiverTeam)
                val prevLastIndex = chatRVA.currentList.lastIndex
                chatRVA.submitList(event.chatLog.messages){
                    if(isFirst) {
                        binding.rvChat.scrollToPosition(0)
                        isFirst = false
                    }
                    chatRVA.notifyItemChanged(prevLastIndex)
                }
            }

            is ChatViewModel.ChatEvent.RefreshChatLog -> {
                val prevLastIndex = chatRVA.currentList.lastIndex
                chatRVA.submitList(event.chatLog.messages){
                    chatRVA.notifyItemChanged(prevLastIndex)
                }
            }
        }
    }

}
