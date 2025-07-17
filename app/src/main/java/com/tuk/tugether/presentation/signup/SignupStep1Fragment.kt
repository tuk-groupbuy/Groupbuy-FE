package com.tuk.tugether.presentation.signup

import android.content.Intent
import android.os.Bundle
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.ivTopbarBack.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        // 이메일 입력 시 ViewModel 업데이트
        binding.etEmail.doAfterTextChanged {
            viewModel.email.value = it?.toString()
        }

        // 인증번호 입력 시 ViewModel 업데이트
        binding.etVerificationCode.doAfterTextChanged {
            viewModel.verificationCode.value = it?.toString()
        }

        // 비밀번호 입력 시 ViewModel 업데이트
        binding.etPassword.doAfterTextChanged {
            viewModel.password.value = it?.toString()
        }

        // 비밀번호 확인 입력 시 ViewModel 업데이트
        binding.etPasswordConfirm.doAfterTextChanged {
            viewModel.passwordConfirm.value = it?.toString()
        }

        // 인증번호 전송 버튼
        binding.btnSendCode.setOnClickListener {
            if (viewModel.email.value.isNullOrBlank()) {
                Toast.makeText(requireContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.sendVerificationCode()
            }
        }

        // 다음 버튼 - 입력값 유효성 검사 후 step2로 이동
        binding.btnNext.setOnClickListener {
            if (viewModel.validateStep1()) {
                findNavController().navigate(R.id.action_signupStep1Fragment_to_signupStep2Fragment)
            } else {
                Toast.makeText(requireContext(), "입력값을 모두 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initObserver() {
        viewModel.step1Valid.observe(viewLifecycleOwner) { isValid ->
            if (isValid == true) {
                Toast.makeText(requireContext(), "인증번호가 전송되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "이메일을 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
