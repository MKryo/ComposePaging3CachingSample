package com.example.composepaging3cachingsample.data.mappers

import com.example.composepaging3cachingsample.data.local.BeerEntity
import com.example.composepaging3cachingsample.data.remote.BeerDto
import com.example.composepaging3cachingsample.domain.Beer

fun BeerDto.toBeerEntity(): BeerEntity {
    return BeerEntity(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = first_brewed,
        imageUrl = image_url
    )
}

fun BeerEntity.toBeer(): Beer {
    return Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = firstBrewed,
        imageUrl = imageUrl
    )
}