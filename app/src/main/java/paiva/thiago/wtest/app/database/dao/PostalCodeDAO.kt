package paiva.thiago.wtest.app.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import paiva.thiago.wtest.app.database.entity.PostalCode

@Dao
interface PostalCodeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: PostalCode)

    @Query(
        "SELECT * FROM postal_codes WHERE code_full_name LIKE :query OR " +
                "code_full_name_clean LIKE :cleanQuery ORDER BY code_full_name ASC"
    )
    fun search(query: String, cleanQuery: String): PagingSource<Int, PostalCode>

    @Query("SELECT COUNT(code) FROM postal_codes")
    suspend fun getCount(): Int
}