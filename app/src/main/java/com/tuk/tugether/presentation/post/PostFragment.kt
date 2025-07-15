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

    private fun setClickListener(){
        binding.ivTopbarBack.setOnClickListener { findNavController().popBackStack() }
    }

}