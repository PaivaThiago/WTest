package paiva.thiago.wtest.app.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "postal_codes", primaryKeys = ["code", "code_ext"])
data class PostalCode(
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "code_ext") val codeExt: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "code_full_name") val codeFullName: String,
    @ColumnInfo(name = "code_full_name_clean") val codeFullNameClean: String,
) {
    override fun toString(): String {
        return codeFullName
    }
}
