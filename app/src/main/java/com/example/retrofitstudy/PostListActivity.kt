package com.example.retrofitstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitstudy.api.APIService
import com.example.retrofitstudy.api.AllPostReaponse
import com.example.retrofitstudy.api.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostListActivity : AppCompatActivity() {
    lateinit var apiService: APIService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        val retrofit= Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService=retrofit.create(APIService::class.java)

    }
    fun showPosts(){
        val call=apiService.getPosts()
        call.enqueue(object: Callback<AllPostReaponse> {
            override fun onResponse(
                call: Call<AllPostReaponse>,
                response: Response<AllPostReaponse>
            ) {
                val data: AllPostReaponse? = response.body()
                data?.let {
                    Log.d("mytag", it.result.toString())
                    val layoutManager = LinearLayoutManager(this@PostListActivity)
                    val adapter = PostListAdapter(data.result)
                    val recyclerView = findViewById<RecyclerView>(R.id.post_list)
                    recyclerView.setHasFixedSize(false)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                }
            }
            override fun onFailure(call: Call<AllPostReaponse>, t: Throwable) {
            }
        })
    }
}