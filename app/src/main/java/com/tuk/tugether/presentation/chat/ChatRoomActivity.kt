package com.tuk.tugether.presentation.chat

import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.tuk.tugether.R
import com.tuk.tugether.databinding.ActivityChatRoomBinding
import com.tuk.tugether.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatRoomActivity: BaseActivity<ActivityChatRoomBinding>(R.layout.activity_chat_room) {
    private val viewModel: ChatViewModel by viewModels()

    override fun initObserver() {

    }

    override fun initView() {
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

        val chatRoomId = intent.getLongExtra("chatRoomId", 5L)
        Log.d("ChatRoomActivity", "받은 chatRoomId: $chatRoomId")
        if (chatRoomId != 5L) {
            viewModel.setChatRoomId(chatRoomId)
        }
    }
}
