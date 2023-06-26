package it.macgood.weatherapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import it.macgood.core.activity.isConnectedToInternet
import it.macgood.core.activity.restartApp
import it.macgood.core.databinding.LayoutNoInternetConnectionBinding
import it.macgood.core.extension.viewBinding
import it.macgood.weatherapp.databinding.ActivityMainBinding
import java.lang.Integer.max
import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isConnectedToInternet()) {
            setContentView(binding.root)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
            navController = navHostFragment.navController
            setupActionBarWithNavController(navController)
        } else {
            val binding = LayoutNoInternetConnectionBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.restartAppButton.setOnClickListener {
                restartApp(intent)
            }

            val intent = Intent(Settings.ACTION_SETTINGS)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}