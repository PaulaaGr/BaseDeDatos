package com.example.myapplicationwebservice.services.driverAdapters

import com.example.myapplicationwebservice.services.controllers.ProductsServices
import com.example.myapplicationwebservice.services.models.Product

class ProductsDiverAdapter {
    private val service: ProductsServices = ProductsServices()

    fun allProducts(
        loadData: (list: List<Product>) -> Unit,
        errorData: () -> Unit
    ) {
        this.service.getAllProducts(
            success = { loadData(it) },
            error = { errorData() }
        )
    }
}