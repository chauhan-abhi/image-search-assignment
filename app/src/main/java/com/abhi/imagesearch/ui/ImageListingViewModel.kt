package com.abhi.imagesearch.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi.imagesearch.data.models.PhotoResponse
import com.abhi.imagesearch.domain.ImageSearchUseCaseInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ImageListingViewModel @ViewModelInject constructor(
    private val useCaseInteractor: ImageSearchUseCaseInteractor
) : ViewModel() {

    var images: MutableLiveData<List<PhotoResponse>> = MutableLiveData()


    fun getPhotosLiveData() = images


    fun searchImages(query: String) {
        if(query.length < 3) {
            return
        }
        viewModelScope.launch(Dispatchers.Main) {
            val result = useCaseInteractor.searchImageByQuery(query)
            images.postValue(result)
        }
    }

    fun resetSearch() {
        images.postValue(mutableListOf())
    }
}