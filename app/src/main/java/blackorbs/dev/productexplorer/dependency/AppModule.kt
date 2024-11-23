package blackorbs.dev.productexplorer.dependency

import android.content.Context
import androidx.room.Room
import blackorbs.dev.productexplorer.data.local.Database
import blackorbs.dev.productexplorer.data.local.ProductDao
import blackorbs.dev.productexplorer.data.remote.ApiService
import blackorbs.dev.productexplorer.repository.BaseRepository
import blackorbs.dev.productexplorer.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Singleton
    @Binds
    abstract fun provideRepository(repository: ProductRepository): BaseRepository
}

@Module
@InstallIn(SingletonComponent::class)
object MainModule{

    @Singleton
    @Provides
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(context, Database::class.java, "products_db")
            .build()

    @Singleton
    @Provides
    fun provideProductDao(database: Database): ProductDao = database.productDao()

    @Singleton
    @Provides
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
}