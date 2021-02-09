package com.beomjo.dagger.di

import android.content.Context
import com.beomjo.dagger.main.MainActivity
import com.beomjo.dagger.registration.RegistrationActivity
import com.beomjo.dagger.registration.RegistrationComponent
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

    fun inject(activity: MainActivity)

}