package com.beomjo.dagger

import android.app.Application
import com.beomjo.dagger.storage.SharedPreferencesStorage
import com.beomjo.dagger.user.UserManager

open class MyApplication : Application() {

    open val userManager by lazy {
        UserManager(SharedPreferencesStorage(this))
    }
}
