package com.tanasi.oauth2.example.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanasi.oauth2.OAuth2ErrorBody
import com.tanasi.oauth2.OAuth2Response
import com.tanasi.oauth2.example.services.TestApiService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun login(username: String, password: String) = viewModelScope.launch {
        val response = TestApiService.build().login(username, password)
        when (response) {
            is OAuth2Response.Success -> {
                response.headers // okhttp3.Headers
                response.code // Int = 2xx

                response.body.accessToken
                response.body.tokenType
                response.body.expiresIn
                response.body.refreshToken
                response.body.scope

                response.body.raw // String
            }
            is OAuth2Response.Error.ServerError -> {
                when (response.body) {
                    is OAuth2ErrorBody.InvalidClient -> {
                        response.body.description
                        response.body.uri
                    }
                    is OAuth2ErrorBody.InvalidGrant -> TODO()
                    is OAuth2ErrorBody.InvalidRequest -> TODO()
                    is OAuth2ErrorBody.InvalidScope -> TODO()
                    is OAuth2ErrorBody.UnauthorizedClient -> TODO()
                    is OAuth2ErrorBody.UnsupportedGrantType -> TODO()
                }
            }
            is OAuth2Response.Error.NetworkError -> {
                response.error // IOException
                Log.e("TAG", "getArticle: ", response.error)
            }
            is OAuth2Response.Error.UnknownError -> {
                response.error // Throwable
                Log.e("TAG", "getArticle: ", response.error)
            }
        }
    }
}
