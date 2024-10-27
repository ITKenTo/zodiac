package com.example.zodiac.base

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.os.LocaleList
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.example.core.activities.CoreActivity
import com.example.zodiac.R
import com.example.zodiac.ZodiacApp
import com.example.zodiac.data.repository.SharedPref
import java.util.Locale
import javax.inject.Inject

abstract class BaseActivity<T : ViewBinding> : CoreActivity<T>() {

    open val fullScreen = false

    open val hideNavigationButton = false

    @Inject
    lateinit var sharePref: SharedPref

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun initConfig(savedInstanceState: Bundle?) {
        super.initConfig(savedInstanceState)
        networkCallback = initDetectNetworkState()
        if (fullScreen) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)
        }
    }

    private fun initDetectNetworkState(): ConnectivityManager.NetworkCallback {
        val networkCallback: ConnectivityManager.NetworkCallback =
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    runOnUiThread {
                        ZodiacApp.instance().isHasNetwork = true

                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        ZodiacApp.instance().isHasNetwork = false
                    }
                }
            }
        try {
            connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return networkCallback
    }

    private fun removeDetectNetworkStateCallback() {
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        if (hideNavigationButton) {
            hideBottomNavigation()
        }
    }

    override fun onDestroy() {
        removeDetectNetworkStateCallback()
        super.onDestroy()
    }

    private fun updateLocaleFormat(c: Context, localeToSwitchTo: Locale): ContextWrapper {
        var context = c
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        val localeList = LocaleList(localeToSwitchTo)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
        context = context.createConfigurationContext(configuration)
        return ContextWrapper(context)
    }

    fun setStatusBarFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun hideBottomNavigation() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}