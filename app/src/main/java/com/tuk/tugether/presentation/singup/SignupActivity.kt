package com.tuk.tugether.presentation.singup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tuk.tugether.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 예: 회원가입 관련 UI/로직 추가 예정
    }
}
