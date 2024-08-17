package com.rmg.production_monitor.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.rmg.production_monitor.R
import com.rmg.production_monitor.databinding.ActivityMainBinding
import com.rmg.production_monitor.databinding.ActivityUrlSetUpBinding
import com.rmg.production_monitor.viewModel.AuthenticationViewModel

class UrlSetUpActivity : AppCompatActivity() {
    lateinit var binding: ActivityUrlSetUpBinding
    private val authenticationViewModel by viewModels<AuthenticationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUrlSetUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            if (binding.urlInput.text?.startsWith("http://", false) == true ||
                binding.urlInput.text?.startsWith("https://", false) == true
            ) {
                authenticationViewModel.saveUrl(binding.urlInput.text.toString())
                Toast.makeText(this, "Url Saved", Toast.LENGTH_SHORT).show()
                finishAfterTransition()

            } else {
                Toast.makeText(this, "Please Input write Url", Toast.LENGTH_SHORT).show()
            }

        }
    }
}