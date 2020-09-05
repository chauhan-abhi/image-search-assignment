package com.abhi.imagesearch.data

import com.abhi.imagesearch.data.models.PhotosSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1")
    suspend fun fetchImages(
        @Query(value = "text") searchTerm: String,
        @Query(value = "api_key") apiKey: String): Response<PhotosSearchResponse>

}