# Retrofit OAuth2 converter

Retrofit OAuth2 Converter is a Android library for convert OAuth2 response to data class

# Introduction

### OAuth2

OAuth 2 is an authorization framework that enables applications to obtain limited access to user accounts on an HTTP service, such as Facebook, GitHub, and DigitalOcean. It works by delegating user authentication to the service that hosts the user account, and authorizing third-party applications to access the user account. OAuth 2 provides authorization flows for web and desktop applications, and mobile devices.

# Getting started

### Implement dependency

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.stantanasi:retrofit-oauth2-converter:LAST_VERSION'
}
```

### Setup
Add the following lines when creating the retrofit instance:
+ **addCallAdapterFactory(OAuth2CallAdapterFactory.create())**
+ **addConverterFactory(OAuth2ConverterFactory.create())**
```kotlin
val retrofit = Retrofit.Builder()
  .baseUrl(baseUrl)
  .client(client)
  .addCallAdapterFactory(OAuth2CallAdapterFactory.create())
  .addConverterFactory(OAuth2ConverterFactory.create())
  .build()
```

# Usage

## API Service

```kotlin
@FormUrlEncoded
@POST("oauth/token")
suspend fun login(
    @Field("username") username: String,
    @Field("password") password: String,
    @Field("grant_type") grantType: String = "password"
): OAuth2Response
```

## OAuth2 success response

```kotlin
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
    is OAuth2Response.Error.ServerError -> TODO()
    is OAuth2Response.Error.NetworkError -> TODO()
    is OAuth2Response.Error.UnknownError -> TODO()
}
```

## Error response

```kotlin
val response = TestApiService.build().login(username, password)
when (response) {
    is OAuth2Response.Success -> TODO()
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
```
