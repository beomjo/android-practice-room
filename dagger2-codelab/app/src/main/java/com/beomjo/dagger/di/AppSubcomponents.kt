package com.beomjo.dagger.di

import com.beomjo.dagger.registration.RegistrationComponent
import dagger.Module

@Module(subcomponents = [RegistrationComponent::class])
class AppSubcomponents {
}