package com.hiteshchopra.marketo.data.injection.modules

import com.hiteshchopra.marketo.data.Constants
import com.hiteshchopra.marketo.data.remote.retrofit.RetrofitHelper
import com.hiteshchopra.marketo.data.remote.service.EventsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return RetrofitHelper.createOkHttpClient()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return RetrofitHelper.createRetrofitClient(okHttpClient, Constants.BASE_URL)
    }

    @Provides
    fun providesEventsApiService(retrofit: Retrofit): EventsService {
        return EventsService.createRetrofitService(retrofit)
    }

}