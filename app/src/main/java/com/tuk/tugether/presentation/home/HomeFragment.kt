package com.tuk.tugether.presentation.home

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentHomeBinding
import com.tuk.tugether.presentation.home.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter

    override fun initView() {
        postAdapter = PostAdapter(emptyList()) { post ->
            Toast.makeText(requireContext(), "Clicked: ${post.title}", Toast.LENGTH_SHORT).show()
        }

        binding.rvHomePicture.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun initObserver() {
        viewModel.postList.observe(viewLifecycleOwner) { posts ->
            postAdapter = PostAdapter(posts) { post ->
                Toast.makeText(requireContext(), "Clicked: ${post.title}", Toast.LENGTH_SHORT).show()
            }
            binding.rvHomePicture.adapter = postAdapter
        }
    }
}
