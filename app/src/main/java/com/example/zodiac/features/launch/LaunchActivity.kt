package com.example.zodiac.features.launch

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.zodiac.base.BaseActivity
import com.example.zodiac.databinding.ActivityLaunchBinding
import com.example.zodiac.features.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class LaunchActivity : BaseActivity<ActivityLaunchBinding>() {
    override fun bindingView(): ActivityLaunchBinding {
        return ActivityLaunchBinding.inflate(layoutInflater)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LaunchActivity::class.java)
        }
    }

    override fun initConfig(savedInstanceState: Bundle?) {
        super.initConfig(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            startActivity(MainActivity.newIntent(this@LaunchActivity))
            finish()
        }
    }


}