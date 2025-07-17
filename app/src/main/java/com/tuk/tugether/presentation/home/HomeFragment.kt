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
        observePostList()
        viewModel.getAllPosts()
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

    private fun observePostList() {
        viewModel.postList.observe(viewLifecycleOwner) { posts ->
            if (posts.isEmpty()) {
                Toast.makeText(requireContext(), "검색 실패", Toast.LENGTH_SHORT).show()
            }
            postAdapter.updateData(posts)
        }
    }

    private fun initSearchInputListener() {
        binding.etHomeSearch.addTextChangedListener {
            val inputText = it?.toString() ?: ""
            binding.ivHomeDelete.visibility = if (inputText.isEmpty()) View.INVISIBLE else View.VISIBLE
        }

        binding.etHomeSearch.setOnEditorActionListener { v, actionId, event ->
            val keyword = binding.etHomeSearch.text.toString()
            if (keyword.isNotBlank()) {
                viewModel.searchPost(keyword)
                hideKeyboard()
            }
            true
        }
    }

    private fun setClickListener() {
        binding.ivHomeDelete.setOnClickListener {
            binding.etHomeSearch.text.clear()
        }

        binding.ivHomeTopbarAlarm.setOnSingleClickListener {
            findNavController().navigate(R.id.goToAlarm)
        }

        binding.etHomeSearch.setOnClickListener {
            binding.layoutSearchTopbar.visibility = View.VISIBLE
            binding.clSearch.visibility = View.VISIBLE
            binding.clHome.visibility = View.INVISIBLE
            binding.layoutHomeTopbar.visibility = View.INVISIBLE
        }

        binding.ivSearchTopbarBack.setOnClickListener {
            binding.layoutSearchTopbar.visibility = View.INVISIBLE
            binding.clSearch.visibility = View.INVISIBLE
            binding.clHome.visibility = View.VISIBLE
            binding.layoutHomeTopbar.visibility = View.VISIBLE
            binding.etHomeSearch.text.clear()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etHomeSearch.windowToken, 0)
        binding.etHomeSearch.clearFocus()
    }

}
