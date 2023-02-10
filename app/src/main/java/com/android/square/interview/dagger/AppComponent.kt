package com.android.square.interview.dagger

import com.android.square.interview.MainApplication
import com.android.square.interview.ui.EmployeeListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DispatcherModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun appModule(module: AppModule): Builder
        fun build(): AppComponent
    }

    fun inject(application: MainApplication)
    fun inject(fragment: EmployeeListFragment)
}