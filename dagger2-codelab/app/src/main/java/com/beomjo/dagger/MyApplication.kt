package com.beomjo.dagger

import android.app.Application
import com.beomjo.dagger.di.AppComponent
import com.beomjo.dagger.di.DaggerAppComponent
import com.beomjo.dagger.storage.SharedPreferencesStorage
import com.beomjo.dagger.user.UserManager

open class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}
