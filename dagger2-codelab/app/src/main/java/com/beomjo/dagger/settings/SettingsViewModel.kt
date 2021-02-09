package com.beomjo.dagger.settings

import com.beomjo.dagger.user.UserDataRepository
import com.beomjo.dagger.user.UserManager

/**
 * SettingsViewModel is the ViewModel that [SettingsActivity] uses to handle complex logic.
 */
class SettingsViewModel(
    private val userDataRepository: UserDataRepository,
    private val userManager: UserManager
) {

    fun refreshNotifications() {
        userDataRepository.refreshUnreadNotifications()
    }

    fun logout() {
        userManager.logout()
    }
}
