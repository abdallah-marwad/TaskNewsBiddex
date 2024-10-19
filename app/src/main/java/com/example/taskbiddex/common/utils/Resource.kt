package com.example.taskbiddex.common.utils

sealed class Resource<T>(
     val data : T? = null ,
     val message : ErrorType ? = null
){
    class Loading<T>(): Resource<T>()
    class Success<T>( data: T?): Resource<T>(data)
    class Failure<T>( message: ErrorType?): Resource<T>(message = message)
}
sealed class ErrorType(
     val message : String?
){
    class NoInternet( message: String): ErrorType(message = message)
    class GeneralErr(message: String?): ErrorType(message = message)
}
