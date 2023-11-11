package com.example.retrofitstudy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitstudy.api.APIService
import com.example.retrofitstudy.api.AllPostResponse
import com.example.retrofitstudy.api.StringResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        showPosts()

        findViewById<FloatingActionButton>(R.id.post_write_btn).setOnClickListener{
            val intent = Intent(this,PostWriteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume(){
        super.onResume()
        showPosts()
    }
    fun showPosts(){
        val call=apiService.getPosts()
        call.enqueue(object: Callback<AllPostResponse> {
            @SuppressLint("WrongViewCast")
            override fun onResponse(
                call: Call<AllPostResponse>,
                response: Response<AllPostResponse>
            ) {
                if ( response.isSuccessful) {
                val data: AllPostResponse? = response.body()
                data?.let {
                    Log.d("mytag", it.result.toString())
                    val layoutManager = LinearLayoutManager(this@PostListActivity)
                    val adapter = PostListAdapter(data.result)
                    val recyclerView = findViewById<RecyclerView>(R.id.post_list)
                    recyclerView.setHasFixedSize(false)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                }

                } else {
                    // 없습니다
                }
            }
            override fun onFailure(call: Call<AllPostResponse>, t: Throwable) {
                Log.d("mytag", t.message.toString())
            }
        })
    }
}