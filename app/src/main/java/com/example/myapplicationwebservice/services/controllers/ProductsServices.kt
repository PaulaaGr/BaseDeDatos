package com.example.myapplicationwebservice.services.controllers

import androidx.lifecycle.viewModelScope
import com.example.myapplicationwebservice.services.endpoints.ProductsEndpoints
import com.example.myapplicationwebservice.services.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsServices : BaseService() {

    fun getAllProducts(
        success: (products: List<Product>) -> Unit,
        error: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getRetrofit()
                    .create(ProductsEndpoints::class.java)
                    .getAllProducts()
                val data = response.body()
                when (data) {
                    null -> success(emptyList())
                    else -> success(data)
                }
            } catch (e: Exception) {
                println(e)
                error()
            }
        }
    }
}