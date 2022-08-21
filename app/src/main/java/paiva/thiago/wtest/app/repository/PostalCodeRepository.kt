package paiva.thiago.wtest.app.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import paiva.thiago.wtest.app.database.dao.PostalCodeDAO
import paiva.thiago.wtest.app.database.entity.PostalCode
import paiva.thiago.wtest.extension.cleanText
import paiva.thiago.wtest.extension.getRetrofit
import paiva.thiago.wtest.extension.validateAsNumber
import paiva.thiago.wtest.extension.withWildCard
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader

private const val BASE_URL = "https://raw.githubusercontent.com/centraldedados/codigos_postais/"
private const val FILE_NAME = "PostalCodes.csv"

private const val NAME_INDEX = 3
private const val CODE_INDEX = 14
private const val EXT_INDEX = 15

class PostalCodeRepository(private val context: Context, private val postalCodeDAO: PostalCodeDAO) {

    private suspend fun getPostalCodes(): ResponseBody {
        return getRetrofit(BASE_URL).create(PostalCodeService::class.java).getPostalCodes()
    }

    private suspend fun saveToFile(body: ResponseBody): File {
        return withContext(Dispatchers.IO) {
            File(context.cacheDir, FILE_NAME).also { file ->
                body.byteStream().use { input ->
                    FileOutputStream(file).use { output ->
                        output.write(input.readBytes())
                    }
                }
            }
        }
    }

    suspend fun initDB() = withContext(Dispatchers.IO) {
        if (postalCodeDAO.getCount() <= 0) {
            FileReader(saveToFile(getPostalCodes())).readLines().toMutableList().also { lines ->
                if (lines.isNotEmpty()) {
                    lines.removeFirst()
                }
                val postalCodes = mutableListOf<PostalCode>()
                lines.forEach { line ->
                    val info = line.split(",")
                    val code: String = info[CODE_INDEX]
                    val codeExt: String = info[EXT_INDEX]
                    val name: String = info[NAME_INDEX]
                    if (code.validateAsNumber(4)
                        && codeExt.validateAsNumber(3)
                        && name.isNotBlank()
                    ) {
                        val fullCodeName = "$code-$codeExt $name"
                        postalCodes.add(
                            PostalCode(
                                code,
                                codeExt,
                                name,
                                fullCodeName,
                                fullCodeName.cleanText()
                            )
                        )
                    }
                }
                postalCodeDAO.insertAll(*postalCodes.toTypedArray())
            }
        }
    }

    fun searchPostalCodes(query: String): Flow<PagingData<PostalCode>> {
        return Pager(config = PagingConfig(pageSize = 30, enablePlaceholders = false)) {
            postalCodeDAO.search(query.withWildCard(), query.cleanText().withWildCard())
        }.flow
    }
}