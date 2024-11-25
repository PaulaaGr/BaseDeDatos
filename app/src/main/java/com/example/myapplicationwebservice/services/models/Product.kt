package com.example.myapplicationwebservice.services.models

data class Product(
    var id: Int,
    var title: String,
    var price: Double,
    var category: String,
    var description: String,
    var image: String,
    var rating: Rating
)
