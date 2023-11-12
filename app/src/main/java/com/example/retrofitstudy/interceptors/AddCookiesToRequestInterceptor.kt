package com.example.retrofitstudy.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

// 프리퍼런스에 저장된 쿠키 값을 읽어서 모두 요청 메시지에 넣어주는 인터셉터
class AddCookiesToRequestInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        // Preference에 저장된 쿠키 값 Set 가져오기
        val cookies = PreferenceHelper.getCookies(context)
        // 요청 메시지에 넣을 쿠키 값을 모두 전달하도록 개별 쿠키 헤더 추가
        for (cookie in cookies) builder.addHeader("Cookie", cookie)
        // 요청 메시지 보낼 수 있도록 계속 진행 요청
        return chain.proceed(builder.build())
    }
}