package com.tuk.tugether.presentation.home

import android.view.View
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
    }

    // BottomNavigationView 보이기
    private fun bottomNavigationShow() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.VISIBLE
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
        // 검색창 입력 삭제
        binding.ivHomeDelete.setOnClickListener {
            binding.etHomeSearch.text.clear()
        }

        // 알림 이동
        binding.ivHomeTopbarAlarm.setOnSingleClickListener {
            findNavController().navigate(R.id.goToAlarm)
        }

        // 검색창 클릭 → 검색 모드 진입
        binding.etHomeSearch.setOnClickListener {
            binding.layoutSearchTopbar.visibility = View.VISIBLE
            binding.clSearch.visibility = View.VISIBLE
            binding.clHome.visibility = View.INVISIBLE
            binding.layoutHomeTopbar.visibility = View.INVISIBLE
        }

        // 뒤로가기 클릭 → 홈 모드 복귀
        binding.ivSearchTopbarBack.setOnClickListener {
            binding.layoutSearchTopbar.visibility = View.INVISIBLE
            binding.clSearch.visibility = View.INVISIBLE
            binding.clHome.visibility = View.VISIBLE
            binding.layoutHomeTopbar.visibility = View.VISIBLE
            binding.etHomeSearch.text.clear()
            hideKeyboard()
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

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(binding.etHomeSearch.windowToken, 0)
        binding.etHomeSearch.clearFocus()
    }

}
