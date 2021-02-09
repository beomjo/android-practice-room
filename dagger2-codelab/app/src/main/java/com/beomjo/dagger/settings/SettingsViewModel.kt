package com.beomjo.dagger.settings

import com.beomjo.dagger.user.UserDataRepository
import com.beomjo.dagger.user.UserManager
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
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
