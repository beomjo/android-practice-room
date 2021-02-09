package com.beomjo.dagger.main

import com.beomjo.dagger.user.UserDataRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userDataRepository: UserDataRepository) {

    val welcomeText: String
        get() = "Hello ${userDataRepository.username}!"

    val notificationsText: String
        get() = "You have ${userDataRepository.unreadNotifications} unread notifications"
}
