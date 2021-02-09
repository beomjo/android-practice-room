package com.beomjo.dagger.user

import com.beomjo.dagger.di.LoggedUserScope
import com.beomjo.dagger.main.MainActivity
import com.beomjo.dagger.settings.SettingsActivity
import dagger.Subcomponent

@LoggedUserScope
@Subcomponent
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }

    fun inject(activity: MainActivity)

    fun inject(activity: SettingsActivity)
}