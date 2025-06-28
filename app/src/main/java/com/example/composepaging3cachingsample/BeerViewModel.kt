package com.example.composepaging3cachingsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData
import com.example.composepaging3cachingsample.data.BeerDto
import com.example.composepaging3cachingsample.data.BeerRepository

class BeerViewModel (
    repository: BeerRepository, // BeerRepositoryを注入
) : ViewModel() {

    // repositoryからPager<Key = Int, Value = BeerDto> を取得し、それをFlowに変換
    // cachedInでViewModelのライフサイクルに合わせる
    val beers: Flow<PagingData<BeerDto>> = repository.getBeersPaging()
        .flow
        .cachedIn(viewModelScope)
}
