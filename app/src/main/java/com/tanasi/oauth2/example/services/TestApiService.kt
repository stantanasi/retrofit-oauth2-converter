package com.tanasi.oauth2.example.services

import com.tanasi.oauth2.OAuth2Response
import com.tanasi.oauth2.adapter.OAuth2CallAdapterFactory
import com.tanasi.oauth2.converter.OAuth2ConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TestApiService {

    companion object {
        fun build(): TestApiService {
            val baseUrl = ""
            val client = OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(OAuth2CallAdapterFactory.create())
                .addConverterFactory(OAuth2ConverterFactory.create())
                .build()

            return retrofit.create(TestApiService::class.java)
        }
    }

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = "password"
    ): OAuth2Response

}