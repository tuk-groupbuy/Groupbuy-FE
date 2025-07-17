package com.tuk.tugether.presentation.login

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle // Bundle import 유지
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // SplashScreen import 유지
import androidx.core.widget.doAfterTextChanged
import com.tuk.tugether.R
import com.tuk.tugether.databinding.ActivityLoginBinding
import com.tuk.tugether.presentation.MainActivity
import com.tuk.tugether.presentation.base.BaseActivity
import com.tuk.tugether.presentation.signup.SignupActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            false // 초기 로딩이 완료된 것으로 간주하고 스플래시 화면을 바로 닫습니다.
        }

        // 나머지 초기화는 기존과 동일하게 initView()와 initObserver()에서 진행
        initView()
        initObserver()
    }

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

        loginViewModel.userInfo.observe(this) { userInfo ->
            if (userInfo != null) {
                // id, nickname 데이터 안전하게 받음
                val userId = userInfo.userId
                val nickname = userInfo.nickname

                // SharedPreferences에 저장 예시
                val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                prefs.edit()
                    .putString("user_id", userId)
                    .putString("user_nickname", nickname)
                    .apply()

                // 이후 화면 전환 등 처리
                Toast.makeText(this, "로그인 완료: $nickname", Toast.LENGTH_SHORT).show()
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