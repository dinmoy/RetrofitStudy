package com.example.retrofitstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofitstudy.api.APIService
import com.example.retrofitstudy.api.AllPostReaponse
import com.example.retrofitstudy.api.Post
import com.example.retrofitstudy.api.PostCreateRequest
import com.example.retrofitstudy.api.PostResponse
import com.example.retrofitstudy.api.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        //getPosts()
        getPost(1)
        deletePost(1)
    }
//    fun getPost(){
//        val call=apiService.getPosts()
//        call.enqueue(object: Callback<AllPostReaponse>{
//            override fun onResponse( call: Call<AllPostReaponse>, response: Response<AllPostReaponse>) {
//                val data: AllPostReaponse?=response.body()
//                data?.let{
//                    Log.d("mytag",it.result.toString())
//                }
//            }
//            override fun onFailure(call: Call<AllPostReaponse>, t: Throwable) {
//            }
//
//        })
//    }
    fun getPost(id :Int){
        val call=apiService.getPost(id)
        call.enqueue(object: Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if(response.isSuccessful){
                    val data: PostResponse?=response.body()
                    data?.let{
                        Log.d("mytag",it.result.toString())
                    }
                }else{

                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
            }

        })
    }

    fun deletePost(id: Int){
        //호출준비
        val call=apiService.deletePost(id)
        //호출(익명 메서드)
        call.enqueue(object: Callback<StringResponse>{
            override fun onResponse(call: Call<StringResponse>,esponse: Response<StringResponse>
            ) { }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) { }

        })

    }
    fun createPost(){
        val call=apiService.createPost(PostCreateRequest("android title","android author","androi content"))
        call.enqueue(object:Callback<StringResponse>{
            override fun onResponse( call: Call<StringResponse>, response: Response<StringResponse> ) { }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) { }

        })
    }

}