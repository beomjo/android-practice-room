package com.beomjo.dagger.registration

import com.beomjo.dagger.di.ActivityScope
import com.beomjo.dagger.registration.enterdetails.EnterDetailsFragment
import com.beomjo.dagger.registration.termsandconditions.TermsAndConditionsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface RegistrationComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RegistrationComponent
    }

    fun inject(activity: RegistrationActivity)

    fun inject(fragment: EnterDetailsFragment)

    fun inject(fragment: TermsAndConditionsFragment)

}