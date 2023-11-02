package com.example.retrofitstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofitstudy.api.APIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var apiService: APIService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit= Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService=retrofit.create(APIService::class.java)
    }
}