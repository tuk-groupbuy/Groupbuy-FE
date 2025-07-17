package com.tuk.tugether.presentation.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        bottomNavigationShow()
        initRecyclerView()
        initSearchInputListener()
        setClickListener()
    }

    override fun initObserver() {
        viewModel.postList.observe(viewLifecycleOwner) { posts ->
            postAdapter.updateData(posts)
        }

        viewModel.isSearchMode.observe(viewLifecycleOwner) { isSearch ->
            binding.tvHomeRecentPost.text = if (isSearch) "검색 결과" else "최근 게시글"
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isSearchMode.value == true) {
            viewModel.cachedSearchResults?.let {
                postAdapter.updateData(it)
            }
        } else {
            viewModel.getAllPosts()
        }
    }

    private fun bottomNavigationShow() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        postAdapter = PostAdapter(mutableListOf()) { post ->
            val bundle = Bundle().apply {
                putLong("postId", post.postId)
            }
            findNavController().navigate(R.id.goToPost, bundle)
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

        binding.etHomeSearch.setOnEditorActionListener { _, _, _ ->
            val keyword = binding.etHomeSearch.text.toString()
            if (keyword.isNotBlank()) {
                viewModel.searchPosts(keyword)
                hideKeyboard()
            }
            true
        }
    }

    private fun setClickListener() {
        binding.ivHomeDelete.setOnClickListener {
            binding.etHomeSearch.text.clear()
            viewModel.setSearchMode(false)
            viewModel.getAllPosts()
        }

        binding.ivHomeTopbarAlarm.setOnSingleClickListener {
            findNavController().navigate(R.id.goToAlarm)
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etHomeSearch.windowToken, 0)
        binding.etHomeSearch.clearFocus()
    }
}
