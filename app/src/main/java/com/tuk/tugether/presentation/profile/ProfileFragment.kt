package com.tuk.tugether.presentation.profile

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentProfileBinding
import com.tuk.tugether.presentation.login.LoginActivity
import com.tuk.tugether.presentation.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun initView() {
        bottomNavigationShow()

        val userId = getUserIdFromPrefs()
        if (userId.isNotEmpty()) {
            viewModel.loadUserProfile(userId)
        }

        // 설정 아이콘 클릭 시 PopupMenu 표시
        binding.ivTopbarIcon.setOnClickListener { view ->
            val popup = android.widget.PopupMenu(requireContext(), view)
            popup.menuInflater.inflate(R.menu.profile_topbar_settings, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_edit_profile -> {
                        // 예: 프로필 수정 화면으로 이동
                        startActivity(Intent(requireContext(), EditProfileActivity::class.java))
                        true
                    }
                    R.id.menu_logout -> {
                        // SharedPreferences 초기화
                        val prefs = requireActivity().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
                        prefs.edit().clear().apply()

                        // 로그인 화면으로 이동
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        true
                    }
                    else -> false
                }
            }

            popup.show()
        }
    }

    override fun initObserver() {
        // 필요 시 LiveData 관찰자 등록
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.user = user
        }
    }

    private fun bottomNavigationShow() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.VISIBLE
    }

    private fun getUserIdFromPrefs(): String {
        val prefs = requireActivity().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        return prefs.getString("user_id", "") ?: ""
    }
}