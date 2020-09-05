package com.abhi.imagesearch

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhi.imagesearch.ui.ImageListingAdapter
import com.abhi.imagesearch.ui.ImageListingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val imageListingViewModel: ImageListingViewModel by viewModels()
    private var mColumnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            mColumnCount = it.getInt("column", 2)
        }
        setContentView(R.layout.activity_main)
        val imageListAdapter = ImageListingAdapter(this)
        val mLayoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, mColumnCount, RecyclerView.VERTICAL, false)
        rvImageListing.layoutManager = mLayoutManager
        rvImageListing.adapter = imageListAdapter
        imageListingViewModel.images.observe(this, { imageList ->
            if (!imageList.isNullOrEmpty()) {
                imageListAdapter.updateList(imageList)
            }
        })
        imageListingViewModel.searchImages("dog")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("column", mColumnCount)
        super.onSaveInstanceState(outState)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbarmenu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.twoColumnMenu -> setColumns(2)
            R.id.threeColumnMenu -> setColumns(3)
            R.id.fourColumnMenu -> setColumns(4)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setColumns(numberOfColumn: Int) {
        mColumnCount = numberOfColumn
        val mLayoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, numberOfColumn, RecyclerView.VERTICAL, false)
        rvImageListing.layoutManager = mLayoutManager
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        imageListingViewModel.searchImages(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


}