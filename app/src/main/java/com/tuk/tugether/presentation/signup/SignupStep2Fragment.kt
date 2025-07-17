package com.tuk.tugether.presentation.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentSignupStep2Binding
import com.tuk.tugether.presentation.base.BaseFragment

class SignupStep2Fragment :
    BaseFragment<FragmentSignupStep2Binding>(R.layout.fragment_signup_step2) {

    private val viewModel: SignupViewModel by activityViewModels()

    private var selectedImageUri: Uri? = null
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            binding.ivProfile.setImageURI(selectedImageUri)
        }
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // 뒤로가기 버튼
        binding.ivTopbarBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }

        // 닉네임 입력 감지
        binding.etNickname.doAfterTextChanged {
            viewModel.nickname.value = it?.toString()
        }

        // 다음 버튼 클릭 → 닉네임 검증 후 회원가입 요청
        binding.btnNext.setOnClickListener {
            val nickname = binding.etNickname.text.toString()

            if (nickname.isBlank()) {
                Toast.makeText(requireContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.nickname.value = nickname
            viewModel.submitSignupWithNickname()
        }
    }

    override fun initObserver() {
        viewModel.signupSuccess.observe(viewLifecycleOwner) { success ->
            if (success == true) {
                Toast.makeText(requireContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signupStep2Fragment_to_signupStep3Fragment)
            } else {
                Toast.makeText(requireContext(), "회원가입 실패. 정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
