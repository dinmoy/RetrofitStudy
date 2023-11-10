package com.example.retrofitstudy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitstudy.api.APIService
import com.example.retrofitstudy.api.AllPostResponse
import com.example.retrofitstudy.api.Post
import com.example.retrofitstudy.api.PostCreateRequest
import com.example.retrofitstudy.api.PostResponse
import com.example.retrofitstudy.api.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostWriteActivity : AppCompatActivity() {
    lateinit var apiService : APIService
    lateinit var post: Post
    var id : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_write)

        // id 받아서 content 출력하기
        id = intent.getIntExtra("id", -1)!!
        Log.d("mytag", id.toString())

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")// 안드에서 지원하는 내 pc(10.0.2.2)에 연결해준다. localhost 사용 안 됨
            .addConverterFactory(GsonConverterFactory.create())// 필수적으로 들어가는 코드. body-parser의 역할과 동일
            .build()

        apiService = retrofit.create(APIService::class.java)// 아까 정의한 APIService 인터페이스를 만듦
//        getPost(id)

//
//        var delete_btn = findViewById<Button>(R.id.post_delete_btn)
//        delete_btn.setOnClickListener {
//            deletePost(id)
//            finish()
//
//            // 왜 this만 사용하면 안 되는가?
//            // A. this가 액티비티가 아닌 익명클래스를 가리키기 때문이다. this가 콜백이므로 바깥의 this임을 알려주기 위해서
//            Toast.makeText(this@PostWriteActivity,"글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
//
//        }

        findViewById<Button>(R.id.post_write_btn).setOnClickListener {
            createPost(
                findViewById<EditText>(R.id.post_title).text.toString(),
                findViewById<EditText>(R.id.post_author).text.toString(),
                findViewById<EditText>(R.id.post_content).text.toString()
            )
        }
    }

    fun createPost(title: String, author: String, content : String){
        val call = apiService.createPost(PostCreateRequest(title, author, content))
        call.enqueue(object:Callback<StringResponse>{
            override fun onResponse( call: Call<StringResponse>, response: Response<StringResponse> ) {
                Toast.makeText(this@PostWriteActivity,"글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) { }

        })
    }
}