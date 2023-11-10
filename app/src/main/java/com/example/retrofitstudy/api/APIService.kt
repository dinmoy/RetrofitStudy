package com.example.retrofitstudy.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.Serializable
import java.util.Date

data class Post(val id: Int, var title: String, var author: String, val createdAt: Date, var content : String) : Serializable
data class  AllPostResponse (val result: List<Post>)
data class PostResponse(val result: Post)
data class PostCreateRequest(val title: String, var author: String, var content : String)
data class StringResponse(val result: String)


interface APIService {
    @GET("/posts")
    fun getPosts() : Call<AllPostResponse>

    @GET("/posts/{id}")
    fun getPost(@Path("id") id : Int) : Call<PostResponse>

    @POST("/posts")
    fun createPost(@Body request : PostCreateRequest) : Call<StringResponse>

    //DELETE 요청 보내는deletePost 메서드 정의

    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id : Int) : Call<StringResponse>

    @PATCH("/posts/{id}")
    @JvmSuppressWildcards
    //Any (=Oject)
    fun modifyPOST(@Path("id") id: Int, @Body request: MutableMap<String,Any>) : Call<StringResponse>

}