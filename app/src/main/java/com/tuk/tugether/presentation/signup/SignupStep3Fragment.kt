package com.tuk.tugether.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentSignupStep3Binding
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.presentation.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignupStep3Fragment : BaseFragment<FragmentSignupStep3Binding>(R.layout.fragment_signup_step3) {

    override fun initView() {
        // 2초 후에 자동으로 로그인 화면으로 이동
        lifecycleScope.launch {
            delay(2000)  // 2000밀리초 = 2초
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun initObserver() {
        // 필요 시 LiveData 관찰
    }
}
