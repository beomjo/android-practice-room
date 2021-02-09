package com.beomjo.dagger.di

import android.content.Context
import com.beomjo.dagger.login.LoginComponent
import com.beomjo.dagger.registration.RegistrationComponent
import com.beomjo.dagger.user.UserManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun registrationComponent(): RegistrationComponent.Factory

    fun loginComponent(): LoginComponent.Factory

    fun userManager(): UserManager


}