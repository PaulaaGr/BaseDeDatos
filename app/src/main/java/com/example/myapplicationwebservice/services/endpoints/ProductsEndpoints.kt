package com.example.myapplicationwebservice.services.endpoints

import com.example.myapplicationwebservice.services.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductsEndpoints {
    @GET("/products")
    suspend fun getAllProducts(): Response<List<Product>>
}