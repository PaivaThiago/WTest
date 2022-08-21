package paiva.thiago.wtest.app.repository

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming

private const val POSTAL_CODES_PATH = "master/data/codigos_postais.csv"

interface PostalCodeService {

    @Streaming
    @GET(POSTAL_CODES_PATH)
    suspend fun getPostalCodes(): ResponseBody
}