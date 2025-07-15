package com.tuk.tugether.presentation.post

import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentCreatePostBinding
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostFragment: BaseFragment<FragmentCreatePostBinding>(R.layout.fragment_create_post) {

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
        binding.tvCreatePostCompleteBtn.setOnSingleClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.createPostFragment, true)
                .build()

            findNavController().navigate(R.id.goToPost, null, navOptions)
        }

        binding.ivTopbarBack.setOnClickListener { findNavController().popBackStack() }
    }
}