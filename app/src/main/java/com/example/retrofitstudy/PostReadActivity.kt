package com.example.retrofitstudy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitstudy.Config.Companion.BASE_URL
import com.example.retrofitstudy.api.APIService
import com.example.retrofitstudy.api.Post
import com.example.retrofitstudy.api.PostResponse
import com.example.retrofitstudy.api.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostReadActivity : AppCompatActivity() {
    lateinit var apiService : APIService
    lateinit var post: Post
    var id : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_read)

        id = intent.getIntExtra("id", -1)!!
        Log.d("mytag", id.toString())

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(APIService::class.java)
        getPost(id)


        var delete_btn = findViewById<Button>(R.id.post_delete_btn)
        delete_btn.setOnClickListener {
            deletePost(id)
            finish()
            Toast.makeText(this@PostReadActivity,"글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()

        }

        findViewById<Button>(R.id.post_modify_btn).setOnClickListener {
            val intent = Intent(this, PostWriteActivity::class.java)
            intent.putExtra("post", post)
            startActivity(intent)
        }
    }

    fun getPost( id: Int) {
        val call = apiService.getPost(id)
        call.enqueue(object : Callback<PostResponse> {
            @SuppressLint("WrongViewCast")
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if ( response.isSuccessful) {
                    val data : PostResponse? = response.body()
                    data?.let {
                        post = it.result
                        findViewById<TextView>(R.id.post_title).text = "제목: "+it.result.title
                        findViewById<TextView>(R.id.post_author).text = "글쓴이: "+it.result.author
                        findViewById<TextView>(R.id.post_content).text = "내용: "+it.result.content
                    }
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.w("mytag", "failed to call API : "+t.message)
            }

        })
    }

    fun deletePost(id: Int) {
        val call = apiService.deletePost(id)
        call.enqueue(object : Callback<StringResponse> {// 익명클래스므로 추상메서드 구현 필요
        override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
            if ( response.isSuccessful) {
                val data : StringResponse? = response.body()
                data?.let {
                    Log.d("mydelete", it.result.toString())
                }
            }
        }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) {}
        })
    }
}