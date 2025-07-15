package com.tuk.tugether.presentation.chat

import android.view.View
import androidx.activity.addCallback
import com.tuk.tugether.R
import com.tuk.tugether.databinding.ActivityChatRoomBinding
import com.tuk.tugether.presentation.base.BaseActivity

class ChatRoomActivity: BaseActivity<ActivityChatRoomBinding>(R.layout.activity_chat_room) {

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
    }
}
