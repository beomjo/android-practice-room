package com.beomjo.dagger.di

import com.beomjo.dagger.login.LoginComponent
import com.beomjo.dagger.registration.RegistrationComponent
import com.beomjo.dagger.user.UserComponent
import dagger.Module

@Module(subcomponents = [RegistrationComponent::class, LoginComponent::class, UserComponent::class])
class AppSubcomponents