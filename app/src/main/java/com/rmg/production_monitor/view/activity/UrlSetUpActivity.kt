package com.rmg.production_monitor.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rmg.production_monitor.databinding.ActivityUrlSetUpBinding
import com.rmg.production_monitor.viewModel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UrlSetUpActivity : AppCompatActivity() {
    lateinit var binding: ActivityUrlSetUpBinding
    private val authenticationViewModel by viewModels<AuthenticationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUrlSetUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.constraintSetUrl.visibility=View.VISIBLE
        binding.progressBar.visibility=View.GONE

        binding.tvConnectedUrl.text = "Connected URL:: ${authenticationViewModel.getUrl()}"
        binding.saveButton.setOnClickListener {
            if (binding.urlInput.text?.startsWith("http://", false) == true ||
                binding.urlInput.text?.startsWith("https://", false) == true
            ) {
                authenticationViewModel.saveUrl(binding.urlInput.text.toString())
                val b_url = authenticationViewModel.getUrl()
                Toast.makeText(this, "Url Saved $b_url", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    // Action to be performed after 5 seconds
                    val packageManager = baseContext.packageManager
                    val intent = packageManager.getLaunchIntentForPackage(baseContext.packageName)
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        baseContext.startActivity(intent)
                        Runtime.getRuntime().exit(0) // Kills the process, ensuring a fresh start
                    }
                }, 5000)

                startProgressCoroutine()
                binding.constraintSetUrl.visibility=View.GONE
                binding.progressBar.visibility=View.VISIBLE


            } else {
                Toast.makeText(this, "Please Input write Url", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun startProgressCoroutine() {
        binding.progressBar.progress = 0
        val totalDuration = 5000L // 5 seconds
        val steps = 100 // Total steps to reach 100% progress
        val delayPerStep = totalDuration / steps // Time delay between steps

        lifecycleScope.launch {
            for (i in 1..steps) {
                binding.progressBar.progress = i
                delay(delayPerStep) // Delay for each step
            }
        }
    }

}