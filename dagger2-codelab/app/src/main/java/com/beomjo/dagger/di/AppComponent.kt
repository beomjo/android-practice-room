package com.beomjo.dagger.di

import com.beomjo.dagger.registration.RegistrationActivity
import dagger.Component

@Component
interface AppComponent {
    fun inject(activity: RegistrationActivity)
}