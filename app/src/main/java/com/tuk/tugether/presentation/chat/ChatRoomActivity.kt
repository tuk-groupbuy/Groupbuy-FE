package com.tuk.tugether.presentation.chat

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.tuk.tugether.R
import com.tuk.tugether.databinding.ActivityChatRoomBinding
import com.tuk.tugether.presentation.base.BaseActivity
import com.tuk.tugether.presentation.chat.adapter.ChatRVA
import com.tuk.tugether.presentation.chat.adapter.WebSocketResource
import com.tuk.tugether.util.extension.KeyboardUtil
import com.tuk.tugether.util.extension.drawableOf
import com.tuk.tugether.util.extension.repeatOnStarted
import com.tuk.tugether.util.extension.setOnSingleClickListener
import com.tuk.tugether.util.network.UiState
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Request
import org.hildan.krossbow.stomp.StompClient
import javax.inject.Inject

@AndroidEntryPoint
class ChatRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding

    @Inject lateinit var stompClient: StompClient
    @Inject lateinit var spf: SharedPreferences

    lateinit var wsClient: WsClient

    private val viewModel: ChatViewModel by viewModels()
    private val chatRVA by lazy {
        ChatRVA()
    }
    private var isFirst = true

    private var isEntered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wsClient = WsClient(
            stompClient = stompClient,
            spf = spf,
            scope = lifecycleScope,
            url = getString(R.string.ws_url),
            onMessageReceived = {
                viewModel.refreshChatLog()
            }
        )

        binding = ActivityChatRoomBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }
        initObserver()
        initView()
    }

    private fun initObserver() {
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
                wsClient.setRoomId(id)
                wsClient.connectWebSocket()
                viewModel.fetchChatMessage()
            }
        }
        repeatOnStarted {
            viewModel.webSocketEvent.collect{ event ->
                handleWebsocketEvent(event)
            }
        }

    }

    private fun initView() {
        validateMessageSend()
        initChatRVAdapter()
        sendMessage()

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

    private fun validateMessageSend() {
        binding.etChatInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (text != null) {
                    if (text.isEmpty()) {
                        binding.ivChatSendBtn.setImageDrawable(drawableOf(R.drawable.ic_send))
                    } else {
                        binding.ivChatSendBtn.setImageDrawable(drawableOf(R.drawable.ic_send))
                    }

                    if (text.length >= 1000) {
                        Toast.makeText(
                            binding.root.context,
                            "채팅은 최대 1,000자까지 입력할 수 있어요",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun sendMessage() {
        with(binding) {
            ivChatSendBtn.setOnSingleClickListener {
                val text = etChatInput.text.toString().trim()
                if (text.isNotEmpty()) {
                    wsClient.sendMessage(text)
                    etChatInput.setText("")
                }
            }
        }
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

    private fun handleWebsocketEvent(event: WebSocketResource){
        when(event){
            WebSocketResource.Enter -> {
                isEntered = true
            }
            else -> Unit
        }
    }

    private fun initWsClient() {
        wsClient.connectWebSocket()
    }

    override fun onDestroy() {
        super.onDestroy()
        wsClient.closeSocket()
        KeyboardUtil.unregisterRecyclerViewKeyboardVisibilityListener(binding.root)
    }
}
