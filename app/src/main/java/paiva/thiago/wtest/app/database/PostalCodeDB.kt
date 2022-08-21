package paiva.thiago.wtest.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import paiva.thiago.wtest.app.database.dao.PostalCodeDAO
import paiva.thiago.wtest.app.database.entity.PostalCode

const val DB_NAME = "PostalCodes"

@Database(entities = [PostalCode::class], version = 1, exportSchema = false)
abstract class PostalCodeDB : RoomDatabase() {
    abstract fun getPostalCodeDAO(): PostalCodeDAO
}