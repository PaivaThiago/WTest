package paiva.thiago.wtest.extension

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import paiva.thiago.wtest.app.database.DB_NAME
import paiva.thiago.wtest.app.database.PostalCodeDB
import paiva.thiago.wtest.app.repository.PostalCodeRepository
import paiva.thiago.wtest.app.viewModel.PostalCodeViewModel

val appModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            PostalCodeDB::class.java,
            DB_NAME
        ).build()
    }

    single { get<PostalCodeDB>().getPostalCodeDAO() }

    single { PostalCodeRepository(androidContext(), get()) }

    viewModel { PostalCodeViewModel(get()) }
}