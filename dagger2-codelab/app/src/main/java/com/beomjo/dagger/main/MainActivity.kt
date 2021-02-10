package com.beomjo.dagger.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.beomjo.dagger.MyApplication
import com.beomjo.dagger.R
import com.beomjo.dagger.login.LoginActivity
import com.beomjo.dagger.registration.RegistrationActivity
import com.beomjo.dagger.settings.SettingsActivity
import com.beomjo.dagger.user.UserManager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userManager = (application as MyApplication).appComponent.userManager()
        userManager.userComponent!!.inject(this)

        setupViews()
    }

    /**
     * Updating unread notifications onResume because they can get updated on SettingsActivity
     */
    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.notifications).text = mainViewModel.notificationsText
    }

    private fun setupViews() {
        findViewById<TextView>(R.id.hello).text = mainViewModel.welcomeText
        findViewById<Button>(R.id.settings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
