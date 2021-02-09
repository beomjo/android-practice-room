package com.beomjo.dagger

import com.beomjo.dagger.storage.FakeStorage
import com.beomjo.dagger.user.UserManager

class MyTestApplication : MyApplication() {

    override val userManager by lazy {
        UserManager(FakeStorage())
    }
}
