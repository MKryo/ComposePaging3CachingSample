package com.example.composepaging3cachingsample.data

import androidx.paging.Pager
import androidx.paging.PagingConfig

class BeerRepository(
    private val api: BeerApi
) {
    fun getBeersPaging(): Pager<Int, BeerDto> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { BeerPagingSource(api) }
        )
    }
}
