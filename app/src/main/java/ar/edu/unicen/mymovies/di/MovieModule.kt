package ar.edu.unicen.mymovies.di

import ar.edu.unicen.mymovies.ddl.data.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", getRightToken(originalRequest))
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    private fun getRightToken(request: Request): String {
        return when {
            request.url.toString().contains("movie/popular") -> {
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMzJhNzgyMDk1OTllZjdiOWVmMTkyODY0ZDM0Mzc3NyIsIm5iZiI6MTcyOTM5MjgzMi44ODU2NDcsInN1YiI6IjY2NzFlY2U3NGEwOThlN2Y1YzU1NzliYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ojT7cwwgdCVpd1sDHYW9m-I25pAsMsQ-Q_0xgJegt_c"
            } else -> {
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMzJhNzgyMDk1OTllZjdiOWVmMTkyODY0ZDM0Mzc3NyIsIm5iZiI6MTcyOTU0MzQxNC45NDkwMTQsInN1YiI6IjY2NzFlY2U3NGEwOThlN2Y1YzU1NzliYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MauiVCTK8jtcoBzMhHKslp0z4q9d1HkfzCpnW70XWxk"
            }
        }
    }

    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMovieApi(
        retrofit: Retrofit
    ): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}