package com.tuk.tugether.presentation.login

import android.content.Intent
import android.graphics.Typeface
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.tuk.tugether.R
import com.tuk.tugether.databinding.ActivityLoginBinding
import com.tuk.tugether.presentation.MainActivity
import com.tuk.tugether.presentation.base.BaseActivity
import com.tuk.tugether.presentation.singup.SignupActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun initView() {
        setSignUpText()

        // EditText -> ViewModel
        binding.etLoginEmail.doAfterTextChanged { text ->
            loginViewModel.email.value = text?.toString()
        }

        binding.etLoginPassword.doAfterTextChanged { text ->
            loginViewModel.password.value = text?.toString()
        }

        binding.btnLoginLogin.setOnClickListener {
            loginViewModel.login()
        }
    }

    override fun initObserver() {
        loginViewModel.loginResult.observe(this) { result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()

            if (result == "로그인 성공") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
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
                ds.color = ContextCompat.getColor(this@LoginActivity, R.color.blue_400)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvLoginSignup.text = spannable
        binding.tvLoginSignup.movementMethod = LinkMovementMethod.getInstance()
        binding.tvLoginSignup.highlightColor = android.graphics.Color.TRANSPARENT
    }
}
