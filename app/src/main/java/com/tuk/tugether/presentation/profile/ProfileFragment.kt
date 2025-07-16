package com.tuk.tugether.presentation.profile

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    override fun initView() {
        bottomNavigationShow()
    }

    override fun initObserver() {

    }

    // BottomNavigationView 보이기
    private fun bottomNavigationShow() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.VISIBLE
    }
}