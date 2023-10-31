package com.example.retrofitstudy.api

import retrofit2.http.GET

interface APIService {
    @GET("/posts")
}