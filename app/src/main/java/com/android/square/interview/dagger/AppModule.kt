package com.android.square.interview.dagger

import android.app.Application
import com.android.square.interview.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val application: MainApplication) {
    @Provides
    @Singleton
    fun getApplication(): Application = application
}