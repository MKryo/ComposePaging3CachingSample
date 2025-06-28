package com.example.composepaging3cachingsample.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException

class BeerPagingSource(private val api: BeerApi) : PagingSource<Int, BeerDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerDto> {
        return try {
            val page = params.key ?: 1 // 何ページ目か
            val pageSize = 20 // 固定ページサイズ

            val totalCount = 50 // 実際のデータの総数
            val start = (page - 1) * pageSize
            val end = minOf(start + pageSize, totalCount)

            val beers = if (start >= totalCount) {
                emptyList()
            } else {
                (start until end).map { id ->
                    BeerDto(
                        id = id + 1,
                        name = "Beer ${id + 1}",
                        tagline = "Tagline ${id + 1}",
                        first_brewed = "01/2025",
                        description = "Description for beer ${id + 1}",
                        image_url = null
                    )
                }
            }

            LoadResult.Page(
                data = beers,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (beers.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BeerDto>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}