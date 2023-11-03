package com.example.retrofitstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.retrofitstudy.api.APIService
import com.example.retrofitstudy.api.PostResponse
import com.example.retrofitstudy.api.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostReadActivity : AppCompatActivity() {
    lateinit var apiService: APIService
    lateinit var post_delete_btn: Button
    var id:Int=-1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_read)
        id = intent.getIntExtra("id",-1)!!
        Log.d("mytag",id.toString())

        val retrofit= Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService=retrofit.create(APIService::class.java)

        getPost(id)
    }

    fun getPost(id :Int){
        val call=apiService.getPost(id)
        call.enqueue(object: Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                    val data: PostResponse?=response.body()
                    data?.let{
                        findViewById<TextView>(R.id.post_title).text="제목: "+it.result.title
                        findViewById<TextView>(R.id.post_author).text="저자: "+it.result.author
                        findViewById<TextView>(R.id.post_content).text="글내용: "+it.result.content
                    }
                }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
              Log.w("mytag","faild to call API: "+t.message)
            }
        })

        post_delete_btn.setOnClickListener{
            fun deletePost(id: Int){
                //호출준비
                val call=apiService.deletePost(id)
                //호출(익명 메서드)
                call.enqueue(object: Callback<StringResponse>{
                    override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>
                    ) {
                        val data: StringResponse? = response.body()
                        data?.let {
                            Toast.makeText(
                                this@PostReadActivity,
                                "글 삭제가 완료되었습니다",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<StringResponse>, t: Throwable) {
                    }
                })


            }
        }

    }
}