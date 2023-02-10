package com.android.square.interview

import android.app.Application
import com.android.square.interview.dagger.AppComponent
import com.android.square.interview.dagger.AppModule
import com.android.square.interview.dagger.DaggerAppComponent

class MainApplication : Application() {
    companion object {
        private lateinit var appComponent: AppComponent

        fun getAppComponent(): AppComponent {
            return appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger(): AppComponent {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
        return appComponent
    }
}