package com.example.retrofitstudy.interceptors

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.retrofitstudy.LoginActivity
import okhttp3.Interceptor
import okhttp3.Response

class SessionExpirationDetectInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code() == 440) {       //세션 만료 코드
            PreferenceHelper.removeCookies(context)     //세션 지우기
            Handler(Looper.getMainLooper()).post(Runnable {
                Toast.makeText(context, "세션이 만료되었습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show()
                // https://stackoverflow.com/questions/12947916/android-remove-all-the-previous-activities-from-the-back-stack
                val intent = Intent(context, LoginActivity::class.java)     //로그인 창으로 넘기기
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //back 버튼을 눌러도 없게, stack구조로 쌓게 만든다
                context.startActivity(intent)
                // (context as Activity).finish()
            })
        }
        return response
    }
}