package com.e.kakaocustommessage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.Social
import com.kakao.sdk.user.UserApiClient
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val animationView = findViewById<View>(R.id.loginLottie) as LottieAnimationView
        animationView.setAnimation("loading.json")
        animationView.loop(true)
        animationView.playAnimation()

        val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Handler().postDelayed(Runnable {
            animationView.pauseAnimation()
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.e("kylog", "사용자 정보 요청 실패", error)

                    animationView.visibility = View.INVISIBLE
                    loginBtn.visibility = View.VISIBLE
                    loginTxt.visibility = View.VISIBLE
                }
                else if (user != null) {
                    Log.i("kylog", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                    Toasty.success(this, "카카오톡 로그인 성공", Toast.LENGTH_SHORT).show()
                    if(pref.getBoolean("isFirstTime",false))
                        startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    else{
                        startActivity(Intent(this, OnBoardActivity::class.java))
                        pref.edit().putBoolean("isFirstTime",true).commit();
                    }
                    finish()
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                }
            }

        },1800)

        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("kylog", "로그인 실패", error)
            }
            else if (token != null) {
                Log.i("kylog", "로그인 성공 ${token.accessToken}")

                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
                        Log.e("kylog", "토큰 정보 보기 실패", error)
                    }
                    else if (tokenInfo != null) {
                        Log.i("kylog", "토큰 정보 보기 성공" +
                                "\n회원번호: ${tokenInfo.id}" +
                                "\n만료시간: ${tokenInfo.expiresIn} 초")
                    }
                }
                Toasty.success(this, "카카오톡 로그인 성공", Toast.LENGTH_SHORT).show()

                if(pref.getBoolean("isFirstTime",false))
                    startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                else{
                    startActivity(Intent(this, OnBoardActivity::class.java))
                    pref.edit().putBoolean("isFirstTime",true).commit();
                }
                finish()
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }
        }

        loginBtn.setOnClickListener{
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}