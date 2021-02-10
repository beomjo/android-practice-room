package com.beomjo.dagger.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.beomjo.dagger.MyApplication
import com.beomjo.dagger.R
import com.beomjo.dagger.login.LoginActivity
import com.beomjo.dagger.registration.RegistrationActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val userManager = (application as MyApplication).appComponent.userManager()

        if (!userManager.isUserRegistered()) {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}