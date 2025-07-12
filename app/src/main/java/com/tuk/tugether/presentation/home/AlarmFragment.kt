package com.tuk.tugether.presentation.home

import android.view.View
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

class AlarmFragment : BaseFragment<FragmentAlarmBinding>(R.layout.fragment_alarm) {

    override fun initView() {
        bottomNavigationRemove()
        setClickListener()
        initAlarmList()
    }

    override fun initObserver() {}

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
        val dummyAlarms = listOf(
            Alarm(
                title = "콜라",
                imageUrl = "https://via.placeholder.com/150/FF0000/FFFFFF?text=텀블러",
                current = 3,
                max = 5,
                requests = listOf(
                    AlarmRequest("사용자1", "https://via.placeholder.com/100/FF00FF/FFFFFF?text=User1"),
                    AlarmRequest("사용자2", "https://via.placeholder.com/100/00FFFF/000000?text=User2")
                )
            ),
            Alarm(
                title = "휴지",
                imageUrl = "https://via.placeholder.com/150/00FF00/000000?text=슬리퍼",
                current = 2,
                max = 4,
                requests = listOf(
                    AlarmRequest("사용자3", "https://via.placeholder.com/100/888888/FFFFFF?text=User3")
                )
            ),
            Alarm(
                title = "콜라",
                imageUrl = "https://via.placeholder.com/150/FF0000/FFFFFF?text=텀블러",
                current = 3,
                max = 5,
                requests = listOf(
                    AlarmRequest("사용자1", "https://via.placeholder.com/100/FF00FF/FFFFFF?text=User1"),
                    AlarmRequest("사용자2", "https://via.placeholder.com/100/00FFFF/000000?text=User2"),
                    AlarmRequest("사용자3", "https://via.placeholder.com/100/00FFFF/000000?text=User2")
                )
            ),
            Alarm(
                title = "휴지",
                imageUrl = "https://via.placeholder.com/150/00FF00/000000?text=슬리퍼",
                current = 2,
                max = 4,
                requests = listOf(
                    AlarmRequest("사용자3", "https://via.placeholder.com/100/888888/FFFFFF?text=User3")
                )
            )
        )

        val adapter = AlarmAdapter(dummyAlarms)
        binding.rvAlarm.adapter = adapter
        binding.rvAlarm.layoutManager = LinearLayoutManager(requireContext())
    }

}
