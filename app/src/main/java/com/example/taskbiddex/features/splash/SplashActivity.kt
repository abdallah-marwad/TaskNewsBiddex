package com.example.taskbiddex.features.splash

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.taskbiddex.common.core.BaseActivity
import com.example.taskbiddex.databinding.ActivitySplashBinding
import com.example.taskbiddex.features.news.presentation.news.NewsActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MySplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val fadeIn = ObjectAnimator.ofFloat(binding.screenLogo, "alpha", 0f, 1f)
        fadeIn.duration = 1000
        fadeIn.start()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                delay(1300)
                    startActivity(Intent(this@MySplashActivity, NewsActivity::class.java))
                    finish()

            }
        }
    }
}