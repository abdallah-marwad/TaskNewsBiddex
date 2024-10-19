package com.example.taskbiddex.features.news.data.repoImpl


import com.example.taskbiddex.common.utils.Constant
import com.example.taskbiddex.common.utils.ErrorType
import com.example.taskbiddex.common.utils.Resource
import com.example.taskbiddex.features.news.data.model.article.Article
import com.example.taskbiddex.features.news.data.model.article.NewsResponse
import com.example.taskbiddex.features.news.data.remote.ApiServices
import com.example.taskbiddex.features.news.domain.repo.NewsRepoAbstract
import com.tailors.doctoria.utils.InternetConnection
import java.io.IOException
import javax.inject.Inject

class NewsRepoImpl@Inject constructor(
    private val apiServices: ApiServices
)  :  NewsRepoAbstract {

    override suspend fun getAllNews(pageNum : Int): Resource<MutableList<Article>?> {
        if (!InternetConnection().hasInternetConnection())
            return Resource.Failure(ErrorType.NoInternet(Constant.noInterNetErrMsg))
        val response = try {
            apiServices.getAllNews(pageNumber  =pageNum)
        } catch (e: Exception) {
            return if (e is IOException) {
                Resource.Failure(ErrorType.NoInternet(Constant.noInterNetErrMsg))
            } else {
                Resource.Failure(ErrorType.GeneralErr(Constant.generalErrMsg))
            }
        }
        return when {
            response.body() == null || response.isSuccessful.not() ->
                Resource.Failure(ErrorType.GeneralErr(Constant.generalErrMsg))
            else -> Resource.Success(response.body()!!.articles)
        }
    }

}