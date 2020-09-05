package com.abhi.imagesearch.domain

import com.abhi.imagesearch.data.models.PhotoResponse

interface ImageSearchUseCaseInteractor {

    suspend fun searchImageByQuery(text: String): List<PhotoResponse>
}