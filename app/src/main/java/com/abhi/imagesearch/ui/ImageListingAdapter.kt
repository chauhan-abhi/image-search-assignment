package com.abhi.imagesearch.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhi.imagesearch.R
import com.abhi.imagesearch.data.models.PhotoResponse
import com.abhi.imagesearch.databinding.ImageListingItemBinding
import com.abhi.imagesearch.utils.loadImageUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class ImageListingAdapter(
    private val context: Context?,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mImageList: List<PhotoResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflator = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ImageListingItemBinding>(layoutInflator, viewType, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageViewHolder).bindData(position)
    }

    override fun getItemCount(): Int {
        return this.mImageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.image_listing_item
    }

    inner class ImageViewHolder(
        private val binding: ImageListingItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(position: Int) {
            val model = mImageList[position]
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(16))
            val imageUrl = getFlickrImageLink(model.id, model.secret, model.server, model.farm, "n")
            binding.imageCard.loadImageUrl(imageUrl, requestOptions)
            binding.name.text = model.title
        }
    }

    fun updateList(imageList: List<PhotoResponse>, columns: Int = 2) {
        mImageList = ArrayList(imageList)
        notifyDataSetChanged()
    }

    fun getFlickrImageLink(id: String, secret: String, serverId: String, farmId: Int, size: String): String {
        return "https://farm$farmId.staticflickr.com/$serverId/${id}_${secret}_$size.jpg"
    }
}

