package com.tuk.tugether.presentation.home

import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentHomeBinding
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.presentation.home.adapter.PostAdapter
import com.tuk.tugether.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter

    override fun initView() {
        initRecyclerView()
        initSearchInputListener()
        setClickListener()
    }

    override fun initObserver() {
        observePostList()
    }

    private fun initRecyclerView() {
        postAdapter = PostAdapter(emptyList()) { post ->
            Toast.makeText(requireContext(), "Clicked: ${post.title}", Toast.LENGTH_SHORT).show()
        }

        binding.rvHomePicture.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initSearchInputListener() {
        binding.etHomeSearch.addTextChangedListener {
            val inputText = it?.toString() ?: ""
            binding.ivHomeDelete.visibility = if (inputText.isEmpty()) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun setClickListener() {
        binding.ivHomeDelete.setOnClickListener {
            binding.etHomeSearch.text.clear()
        }

        binding.ivTopbarAlarm.setOnSingleClickListener {
            findNavController().navigate(R.id.goToAlarm)
        }
    }

    private fun observePostList() {
        viewModel.postList.observe(viewLifecycleOwner) { posts ->
            postAdapter = PostAdapter(posts) { post ->
                Toast.makeText(requireContext(), "Clicked: ${post.title}", Toast.LENGTH_SHORT).show()
            }
            binding.rvHomePicture.adapter = postAdapter
        }
    }
}
