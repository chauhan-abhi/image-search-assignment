package com.abhi.imagesearch.domain

import com.abhi.imagesearch.BuildConfig
import com.abhi.imagesearch.data.FlickrApiService
import com.abhi.imagesearch.data.models.PhotoResponse
import com.abhi.imagesearch.ui.ViewState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageSearchRepository @Inject constructor(
    private val apiService: FlickrApiService
) : ImageSearchUseCaseInteractor {
    override suspend fun searchImageByQuery(text: String) = withContext(Dispatchers.IO) {
        var viewState = ViewState.loading<List<PhotoResponse>>()
        val photosResponse = apiService.fetchImages(text, BuildConfig.FLICKR_API_KEY)
        val list = ArrayList<PhotoResponse>()
        if(photosResponse.isSuccessful) {
            list.addAll(photosResponse.body()?.photos!!.photo)
            viewState = ViewState.success(list)
        } else {
            viewState = ViewState.error("Network failure!")
        }
        viewState
    }
}

@Module
@InstallIn(ApplicationComponent::class)
interface ImageSearchRepositoryModule {
    @Binds
    fun bindRepository(imageSearchRepository: ImageSearchRepository): ImageSearchUseCaseInteractor
}