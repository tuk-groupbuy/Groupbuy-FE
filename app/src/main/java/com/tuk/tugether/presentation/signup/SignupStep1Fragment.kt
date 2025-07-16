package com.tuk.tugether.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentSignupStep1Binding
import com.tuk.tugether.presentation.base.BaseFragment
import com.tuk.tugether.presentation.login.LoginActivity

class SignupStep1Fragment :
    BaseFragment<FragmentSignupStep1Binding>(R.layout.fragment_signup_step1) {

    private val viewModel: SignupViewModel by activityViewModels()

    override fun initView() {

        binding.ivTopbarBack.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        // ✅ doAfterTextChanged로 ViewModel 연결
        binding.etEmail.doAfterTextChanged {
            viewModel.email.value = it?.toString()
        }

        binding.etVerificationCode.doAfterTextChanged {
            viewModel.verificationCode.value = it?.toString()
        }

        binding.etPassword.doAfterTextChanged {
            viewModel.password.value = it?.toString()
        }

        binding.etPasswordConfirm.doAfterTextChanged {
            viewModel.passwordConfirm.value = it?.toString()
        }

        // 인증번호 전송
        binding.btnSendCode.setOnClickListener {
            viewModel.sendVerificationCode()
            Toast.makeText(requireContext(), "인증번호를 전송했습니다.", Toast.LENGTH_SHORT).show()
        }

        // 다음 버튼
        binding.btnNext.setOnClickListener {
            if (viewModel.validateStep1()) {
                findNavController().navigate(R.id.action_signupStep1Fragment_to_signupStep2Fragment)
            } else {
                Toast.makeText(requireContext(), "모든 입력을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initObserver() {
        // 필요 시 LiveData 옵저빙
    }
}
