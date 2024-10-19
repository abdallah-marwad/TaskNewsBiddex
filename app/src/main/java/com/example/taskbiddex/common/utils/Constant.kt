package com.example.taskbiddex.common.utils

import com.example.taskbiddex.MyApplication
import com.example.taskbiddex.R

object Constant {

    const val API_KEY = "cdb0eb7d712847e0944ce82e39245299"
    const val BASE_URL = "https://newsapi.org/"
    const val  QUERY_PAGE_SIZE = 20
    const val  ARTICLE = "ARTICLE"
    val generalErrMsg =
        MyApplication.myAppContext.resources?.getString(R.string.some_err_happened)
    val noInterNetErrMsg =
        MyApplication.myAppContext.resources.getString(R.string.check_internet_connection)
}