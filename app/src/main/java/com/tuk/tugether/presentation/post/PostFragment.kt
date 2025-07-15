package com.tuk.tugether.presentation.post

import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentPostBinding
import com.tuk.tugether.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment: BaseFragment<FragmentPostBinding>(R.layout.fragment_post) {

    override fun initView() {
        bottomNavigationRemove()
        setClickListener()
    }

    override fun initObserver() {

    }

    // BottomNavigationView 숨기기
    private fun bottomNavigationRemove() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.GONE
    }

    private fun setClickListener() {
        binding.ivTopbarBack.setOnClickListener {
            findNavController().popBackStack()
        }

        var isJoined = false

        binding.tvPostJoinBtn.setOnClickListener {
            isJoined = !isJoined

            if (isJoined) {
                binding.tvPostJoinBtn.text = "취소하기"
                binding.tvPostJoinBtn.setBackgroundResource(R.drawable.shape_rect_999_blue400_fill)
                binding.tvPostJoinBtn.setTextColor(requireContext().getColor(R.color.white))
            } else {
                binding.tvPostJoinBtn.text = "참여하기"
                binding.tvPostJoinBtn.setBackgroundResource(R.drawable.shape_rect_999_blue300_fill)
                binding.tvPostJoinBtn.setTextColor(requireContext().getColor(R.color.black_main))
            }
        }
    }

}