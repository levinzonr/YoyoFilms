package cz.levinzonr.stackquestions.screens.viewutils

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by nomers on 3/16/18.
 */
class InfiniteScrollListener(
        val callbacks: InfiniteScrollCallbacks,
        val linearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener(){
    var isLoading: Boolean = false
    var currentPage: Int = 0
    var previousItemCount = 0
    var staticElementsCnt = 1

    constructor(staticElementsCnt : Int,
                callbacks: InfiniteScrollCallbacks,
                linearLayoutManager: LinearLayoutManager) : this (callbacks, linearLayoutManager) {
        this.staticElementsCnt = staticElementsCnt
    }


    interface InfiniteScrollCallbacks {
        fun onLoadMore(pageToLoad: Int)
    }

    companion object {
        const val TAG = "InfiniteScrollListener"
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemsCount = linearLayoutManager.itemCount
        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition() + 1

        if (!isLoading && totalItemsCount <= lastVisibleItem + staticElementsCnt) {
            currentPage ++
            Log.d(TAG, "-->LoadTime? $currentPage")
            isLoading = true
            callbacks.onLoadMore(currentPage)
        }

        if (previousItemCount < totalItemsCount) {
            isLoading = false
            previousItemCount = totalItemsCount
        }
    }


}