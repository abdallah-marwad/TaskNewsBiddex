package com.example.taskbiddex.common.utils

import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
/*
    created at 01/01/2024
    by Abdallah Marwad
    abdallahshehata311as@gmail.com
 */
class RecyclerPaging(private val shouldPaginateCallBack: ShouldPaginateCallBack) :
    RecyclerView.OnScrollListener() {
    @Volatile
    var isLoading = false

    @Volatile
    var isLastPage = false

    @Volatile
    var isPaginate = false
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val exceptionHandler = CoroutineExceptionHandler() { _, throwable ->
            Log.d("test", "CoroutineExceptionHandler = " + throwable.message)
        }
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val lastVisibleView =
                layoutManager.findViewByPosition(layoutManager.findLastVisibleItemPosition())

            val isNotLoadingAndNotLastPage = !isLoading and !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            var isWholeLastItemVisible = false
            if (isAtLastItem) {
                lastVisibleView?.let {
                    val itemBottom = lastVisibleView.bottom
                    val recyclerViewHeight = recyclerView.height
                    isWholeLastItemVisible = itemBottom <= recyclerViewHeight
                }
            }
            val shouldPaginate =
                (isNotLoadingAndNotLastPage and isAtLastItem && isWholeLastItemVisible)
            if (shouldPaginate) {
                isLoading = true
                isPaginate = true
                withContext(Dispatchers.Main) {
                    shouldPaginateCallBack.shouldPaginateCallBack()
                }
            }
        }

    }

    interface ShouldPaginateCallBack {
        fun shouldPaginateCallBack()
    }
}
