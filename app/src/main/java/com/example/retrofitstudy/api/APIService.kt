package com.example.retrofitstudy.api

import retrofit2.Call
import retrofit2.http.GET
import java.util.Date

data class Post(val id: Int, var title: String, var author: String, val createdAt: Date, var content : String)
data class  AllPostReaponse (val result: List<Post>)
interface APIService {
    @GET("/posts")
    fun getPosts() : Call<AllPostReaponse>
}