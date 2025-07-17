package com.tuk.tugether.presentation.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentAlarmBinding
import com.tuk.tugether.domain.model.request.CommonChatRequestModel
import com.tuk.tugether.domain.model.request.notification.NotificationApproveRequestModel
import com.tuk.tugether.domain.model.request.notification.NotificationDecisionRequestModel
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.presentation.chat.ChatViewModel
import com.tuk.tugether.presentation.home.adapter.Alarm
import com.tuk.tugether.presentation.home.adapter.AlarmAdapter
import com.tuk.tugether.presentation.home.adapter.AlarmRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding>(R.layout.fragment_alarm) {

    private val viewModel: NotificationViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()

    override fun initView() {
        bottomNavigationRemove()
        setClickListener()
        initAlarmList()
    }

    override fun initObserver() {
    }

    // BottomNavigationView 숨기기
    private fun bottomNavigationRemove() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.GONE
    }

    private fun setClickListener(){
        binding.ivTopbarBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initAlarmList() {
        viewModel.notificationList.observe(viewLifecycleOwner) { notifications ->
            val alarmMap = notifications.groupBy { it.content }

            val userId = getUserIdFromPrefs()
            if (userId.isEmpty()) return@observe
            val writerId = userId.toLong()

            val alarmList = alarmMap.map { (content, group) ->
                val first = group.first()
                val title = Regex("게시글 '(.*?)'").find(content)?.groupValues?.get(1) ?: "알 수 없음"

                Alarm(
                    title = title,
                    current = first.currentQuantity,
                    max = first.goalQuantity,
                    requests = group.map { item ->
                        AlarmRequest(
                            senderNickname = item.senderNickname,
                            createdAt = item.createdAt,
                            postId = item.postId,
                            userId = item.userId!!,
                            chatRoomId = item.chatRoomId
                        )
                    }
                )
            }

            val adapter = AlarmAdapter(alarmList,
                onApprove = { postId, userId, chatRoomId ->
                    viewModel.approveNotification(
                        NotificationApproveRequestModel(
                            postId = postId,
                            userId = userId,
                            writerId = writerId
                        )
                    )
                    chatViewModel.fetchJoinChat(
                        CommonChatRequestModel(
                            chatRoomId = chatRoomId,
                            userId = userId
                        )
                    )
                },
                onReject = { postId, userId ->
                    viewModel.rejectNotification(
                        NotificationDecisionRequestModel(
                            postId = postId,
                            userId = userId,
                            writerId = writerId
                        )
                    )
                }
            )
            binding.rvAlarm.adapter = adapter
            binding.rvAlarm.layoutManager = LinearLayoutManager(requireContext())
        }

        val userId = getUserIdFromPrefs()
        if (userId.isNotEmpty()) {
            viewModel.getNotifications(userId.toLong())
        }
    }

    private fun getUserIdFromPrefs(): String {
        val prefs = requireActivity().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        return prefs.getString("user_id", "") ?: ""
    }

}
