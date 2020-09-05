package com.abhi.imagesearch.domain

import com.abhi.imagesearch.data.models.PhotoResponse
import com.abhi.imagesearch.ui.ViewState

interface ImageSearchUseCaseInteractor {

    suspend fun searchImageByQuery(text: String): ViewState<List<PhotoResponse>>
}