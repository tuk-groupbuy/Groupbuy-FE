package com.tuk.tugether.presentation.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.tuk.tugether.R
import com.tuk.tugether.databinding.ActivityEditProfileBinding
import com.tuk.tugether.presentation.MainActivity
import com.tuk.tugether.presentation.base.BaseActivity

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>(R.layout.activity_edit_profile) {

    private val viewModel: EditProfileViewModel by viewModels()

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
        val userId = getUserIdFromPrefs()
        if (userId.isNotEmpty()) {
            viewModel.loadUserProfile(userId)
        }

        binding.ivTopbarBack.setOnClickListener {
            finish()
        }

        binding.ivEditProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }

        binding.btnComplete.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }
    }

    override fun initObserver() {
        viewModel.user.observe(this) { user ->
            if (user != null) {
                binding.user = user
            }
        }
    }

    private fun getUserIdFromPrefs(): String {
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return prefs.getString("user_id", "") ?: ""
    }
}
