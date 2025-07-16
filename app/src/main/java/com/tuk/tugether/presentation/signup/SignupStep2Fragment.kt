package com.tuk.tugether.presentation.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.tuk.tugether.R
import com.tuk.tugether.databinding.FragmentSignupStep2Binding
import com.tuk.tugether.presentation.base.BaseFragment

class SignupStep2Fragment : BaseFragment<FragmentSignupStep2Binding>(R.layout.fragment_signup_step2) {

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

        binding.ivTopbarBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }

        binding.ivEditProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }

        binding.btnNext.setOnClickListener {
            val nickname = binding.etNickname.text.toString()

            if (nickname.isBlank()) {
                showToast("닉네임을 입력해주세요.")
                return@setOnClickListener
            }
            // ViewModel에 닉네임과 이미지 URI 저장할 수 있음
            // viewModel.nickname.value = nickname
            // viewModel.profileImageUri.value = selectedImageUri

            findNavController().navigate(R.id.action_signupStep2Fragment_to_signupStep3Fragment)
        }
    }

    override fun initObserver() {
        // 필요 시 LiveData 관찰
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT).show()
    }
}
