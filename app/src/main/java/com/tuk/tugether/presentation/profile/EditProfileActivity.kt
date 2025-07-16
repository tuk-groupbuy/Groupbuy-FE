package com.tuk.tugether.presentation.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.tuk.tugether.R
import com.tuk.tugether.databinding.ActivityEditProfileBinding
import com.tuk.tugether.presentation.base.BaseActivity

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>(R.layout.activity_edit_profile) {

    private val viewModel: EditProfileViewModel by viewModels()

    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            selectedImageUri?.let { uri ->
                Glide.with(this)
                    .load(uri)
                    .circleCrop()
                    .into(binding.ivProfile)
            }
        }
    }

    override fun initObserver() {
        // 유저 데이터 옵저빙
        viewModel.user.observe(this, Observer { user ->
            if (user != null) {
                binding.user = user
                // 프로필 이미지 Glide로 로드
                Glide.with(this)
                    .load(user.profileImageUrl)
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .circleCrop()
                    .into(binding.ivProfile)
            }
        })
    }

    override fun initView() {
        // ViewModel 바인딩
        binding.viewModel = viewModel

        // 유저 프로필 불러오기
        viewModel.loadUserProfile()

        // 백버튼 클릭 처리
        binding.ivTopbarBack.setOnClickListener {
            finish()
        }

        // 프로필 편집 아이콘 클릭
        binding.ivEditProfile.setOnClickListener {
            // TODO: 프로필 사진 변경 로직 구현
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }

        // 완료 버튼 클릭
        binding.btnComplete.setOnClickListener {
            // TODO: 프로필 수정 완료 처리
            finish()
        }
    }
}
