package com.example.myapplicationwebservice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplicationwebservice.components.ImageWeb
import com.example.myapplicationwebservice.dataBases.entities.UsuarioEntity
import com.example.myapplicationwebservice.dataBases.viewsModels.UsuaiosViewModel
import com.example.myapplicationwebservice.services.driverAdapters.ProductsDiverAdapter
import com.example.myapplicationwebservice.services.models.Product
import com.example.myapplicationwebservice.ui.theme.MyApplicationWebServiceTheme

class MainActivity : ComponentActivity() {
    val productsDiverAdapter by lazy { ProductsDiverAdapter() }
    val usuariosViewModel by lazy { UsuaiosViewModel(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            usuariosViewModel.saveNewUser(UsuarioEntity(
                id = 0,
                name = "Pepito",
                email = "pepito@test.com"
            ))
            var products by remember { mutableStateOf<List<Product>>(emptyList()) }
            var loadProducts by remember { mutableStateOf<Boolean>(false) }
            if (!loadProducts) {
                this.productsDiverAdapter.allProducts(
                    loadData = {
                        products = it
                        loadProducts = true

                    },
                    errorData = {
                        println("Error en el servicio")
                        loadProducts = true
                    }
                )
            }
            ProductsScreen(products = products, onClickProduct = { goToDetail() })
        }
    }

    fun goToDetail() {
        val intent = Intent(this, ProductActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun ProductsScreen(
    products: List<Product>,
    onClickProduct: () -> Unit
) {
    MyApplicationWebServiceTheme {
        Scaffold(
            topBar = {
                Text(text = stringResource(id = R.string.title_products))
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                LazyColumn {
                    items(
                        items = products,
                        key = { it.id }
                    ) {
                        Column {
                            ImageWeb(url = it.image)
                            Row {
                                Text(text = stringResource(id = R.string.price))
                                Text(text = "${it.price}")
                            }
                            Row {
                                Text(text = stringResource(id = R.string.title))
                                Text(text = "${it.title}")
                            }
                            Row {
                                Text(text = stringResource(id = R.string.category))
                                Text(text = "${it.category}")
                            }
                            Button(onClick = { onClickProduct() }) {
                                Text(text = stringResource(id = R.string.go_to_product))
                            }
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, widthDp = 360)
@Composable
fun ProductsScreenPreview() {
    ProductsScreen(
        products = emptyList(),
        onClickProduct = {}
    )
}
