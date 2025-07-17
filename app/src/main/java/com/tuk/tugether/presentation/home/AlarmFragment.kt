package com.tuk.tugether.presentation.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentAlarmBinding
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.presentation.home.adapter.Alarm
import com.tuk.tugether.presentation.home.adapter.AlarmAdapter
import com.tuk.tugether.presentation.home.adapter.AlarmRequest
import com.tuk.tugether.presentation.home.adapter.AlarmRequestAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding>(R.layout.fragment_alarm) {

    private val viewModel: NotificationViewModel by viewModels()

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

            val alarmList = alarmMap.map { (content, group) ->
                val first = group.first()
                val title = Regex("게시글 '(.*?)'").find(content)?.groupValues?.get(1) ?: "알 수 없음"

                Alarm(
                    title = title,
                    current = first.currentQuantity,
                    max = first.goalQuantity,
                    requests = group.map {
                        AlarmRequest(
                            notificationId = it.notificationId,
                            createdAt = it.createdAt
                        )
                    }
                )
            }

            val adapter = AlarmAdapter(alarmList)
            binding.rvAlarm.adapter = adapter
            binding.rvAlarm.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getNotifications(5L)  // userId 임시 고정
    }

}
