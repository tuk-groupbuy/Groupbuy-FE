package com.tuk.tugether.presentation.login

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tuk.tugether.databinding.ActivityLoginBinding
import com.tuk.tugether.presentation.MainActivity
import com.tuk.tugether.presentation.singup.SignupActivity
import androidx.core.widget.doAfterTextChanged

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSignUpText()

        // EditText -> ViewModel
        // 이메일
        binding.etLoginEmail.doAfterTextChanged { text ->
            loginViewModel.email.value = text?.toString()
        }

// 비밀번호
        binding.etLoginPassword.doAfterTextChanged { text ->
            loginViewModel.password.value = text?.toString()
        }

        // 로그인 버튼
        binding.btnLoginLogin.setOnClickListener {
            loginViewModel.login()
        }

        // 결과 감지
        loginViewModel.loginResult.observe(this) { result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()

            if (result == "로그인 성공") {
                // ✅ MainActivity로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // LoginActivity 종료 (뒤로가기 시 다시 안 보이게)
            }
        }
    }

    private fun setSignUpText() {
        val fullText = "계정이 없으신가요? 가입하기"
        val keyword = "가입하기"
        val spannable = SpannableString(fullText)
        val start = fullText.indexOf(keyword)
        val end = start + keyword.length

        spannable.setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = ContextCompat.getColor(this@LoginActivity, com.tuk.tugether.R.color.blue_400)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvLoginSignup.text = spannable
        binding.tvLoginSignup.movementMethod = LinkMovementMethod.getInstance()
        binding.tvLoginSignup.highlightColor = android.graphics.Color.TRANSPARENT
    }
}
