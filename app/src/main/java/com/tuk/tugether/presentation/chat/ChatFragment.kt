package com.tuk.tugether.presentation.chat

import android.content.Intent
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment: BaseFragment<FragmentChatBinding>(R.layout.fragment_chat) {

    override fun initObserver() {
        bottomNavigationShow()
    }

    override fun initView() {
        binding.tvGotochat.setOnClickListener {
            val intent = Intent(requireContext(), ChatRoomActivity::class.java)
            startActivity(intent)
        }
    }

    // BottomNavigationView 보이기
    private fun bottomNavigationShow() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.VISIBLE
    }
}