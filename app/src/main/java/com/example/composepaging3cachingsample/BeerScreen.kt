package com.example.composepaging3cachingsample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.composepaging3cachingsample.data.BeerApi
import com.example.composepaging3cachingsample.data.BeerDto
import com.example.composepaging3cachingsample.data.BeerRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Composable
fun BeerScreen(
    modifier: Modifier = Modifier
) {

    val retrofit = Retrofit.Builder()
        .baseUrl(BeerApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val api = retrofit.create(BeerApi::class.java)
    val factory = BeerViewModelFactory(BeerRepository(api))
    val viewModel: BeerViewModel = viewModel(factory = factory)


    // collect して、ついでにページング用の UI フレンドリーなデータ型 LazyPagingItems に変換する
    val beers: LazyPagingItems<BeerDto> = viewModel.beers.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(beers.itemCount) { index ->
            beers[index]?.let { beer ->
                BeerItem(beer)
            }
        }
    }
}

@Composable
fun BeerItem(beer: BeerDto?) {
    if (beer == null) {
        Text("Loading...")
    } else {
        Text(text = beer.name)
        Text(text = beer.tagline)
        Text(text = beer.first_brewed)
        Text(text = beer.description)
    }
}