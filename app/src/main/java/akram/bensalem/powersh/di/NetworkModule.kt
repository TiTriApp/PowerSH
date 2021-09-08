package akram.bensalem.powersh.di

import com.github.theapache64.retrosheet.RetrosheetInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import akram.bensalem.powersh.data.api.PowerSHApi
import akram.bensalem.powersh.data.database.PowerSHDao
import akram.bensalem.powersh.repository.PowerSHRepository
import akram.bensalem.powersh.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrosheetInterceptor = RetrosheetInterceptor.Builder()
        .setLogging(false)
        .addSheet(
            sheetName = "all_shoes",
            columns = arrayOf(
                "id","title", "image_url", "release_date", "market_price_start", "market_price_end",
            )

        )
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(retrosheetInterceptor)
        .build()

    @Singleton
    @Provides
    fun providesPowerSHRepository(
        powerSHApi: PowerSHApi,
        powerSHDao: PowerSHDao
    ) = PowerSHRepository(
        powerSHApi,
        powerSHDao
    )

    @Singleton
    @Provides
    fun providesPowerSHApi(): PowerSHApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PowerSHApi::class.java)
}