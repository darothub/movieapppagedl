package com.darotapp.movieapptoo.data.repository

enum class  Status{
    RUNNING,
    SUCCESS,
    FAILED

}
class NetworkState(val status:Status, val message:String) {

    companion object{
        val LOADED:NetworkState
        val LOADING:NetworkState
        val ERROR:NetworkState
        val END_OF_LIST: NetworkState

        init{
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Loading")
            ERROR = NetworkState(Status.FAILED, "Failed")
            END_OF_LIST = NetworkState(Status.FAILED, "You have reached the end")

        }
    }
}
