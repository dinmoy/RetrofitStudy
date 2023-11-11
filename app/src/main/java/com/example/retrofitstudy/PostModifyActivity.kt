package com.example.retrofitstudy

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.example.retrofitstudy.api.APIService
import com.example.retrofitstudy.api.Post
import com.example.retrofitstudy.api.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostModifyActivity : AppCompatActivity() {

    lateinit var apiService: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_modify)

        val post = intent.getSerializableExtra("post") as Post
        // Q) post 에 title,author,content가 있을테니 해당 내용을 모두 EditText네다가 넣어주기
        findViewById<EditText>(R.id.post_title).setText(post.title)
        findViewById<EditText>(R.id.post_author).setText(post.author)
        findViewById<EditText>(R.id.post_content).setText(post.content)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")// 안드에서 지원하는 내 pc(10.0.2.2)에 연결해준다. localhost 사용 안 됨
            .addConverterFactory(GsonConverterFactory.create())// 필수적으로 들어가는 코드. body-parser의 역할과 동일
            .build()
        apiService = retrofit.create(APIService::class.java)

        findViewById<Button>(R.id.modify_btn).setOnClickListener {
            modifyPost(
                post.id,
                mutableMapOf(
                    "title" to findViewById<EditText>(R.id.post_title).text.toString(),
                    "author" to findViewById<EditText>(R.id.post_author).text.toString(),
                    "content" to findViewById<EditText>(R.id.post_content).text.toString()
                )
            )
        }
    }

    fun modifyPost(id: Int, body: MutableMap<String, Any>) {
        val call = apiService.modifyPOST(id, body)
        call.enqueue(object :
            Callback<StringResponse> {
            override fun onResponse(
                call: Call<StringResponse>, response: Response<StringResponse>
            ) {
                finish()
            }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) {
                Log.w("mytag", "failed to call API: " + t.message)
            }

        })

    }
}