package paiva.thiago.wtest.extension

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun getRetrofit(baseUrl: String): Retrofit = Retrofit
    .Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()