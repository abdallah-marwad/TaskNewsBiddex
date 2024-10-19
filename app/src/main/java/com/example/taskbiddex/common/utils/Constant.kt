package com.example.taskbiddex.common.utils

import com.example.taskbiddex.MyApplication
import com.example.taskbiddex.R

object Constant {

    const val API_KEY = "da8ca0a0ecd44772a2694cad342a6135"
    const val BASE_URL = "https://newsapi.org/"
    const val  QUERY_PAGE_SIZE = 20
    val generalErrMsg =
        MyApplication.myAppContext.resources?.getString(R.string.some_err_happened)
    val noInterNetErrMsg =
        MyApplication.myAppContext.resources.getString(R.string.check_internet_connection)
}