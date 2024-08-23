package com.rmg.production_monitor.view.activity

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rmg.production_monitor.core.Config
import com.rmg.production_monitor.databinding.ActivityUrlSetUpBinding
import com.rmg.production_monitor.viewModel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UrlSetUpActivity : AppCompatActivity() {
    lateinit var binding: ActivityUrlSetUpBinding
    private val authenticationViewModel by viewModels<AuthenticationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUrlSetUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvConnectedUrl.text="Connected URL:: ${Config.BASE_URL}"
        binding.saveButton.setOnClickListener {
            if (binding.urlInput.text?.startsWith("http://", false) == true ||
                binding.urlInput.text?.startsWith("https://", false) == true
            ) {
                authenticationViewModel.saveUrl(binding.urlInput.text.toString())
                val  b_url=authenticationViewModel.getUrl()
                Toast.makeText(this, "Url Saved $b_url", Toast.LENGTH_SHORT).show()

                val intent: Intent? =getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName())
                intent?.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                Process.killProcess(Process.myPid())
                System.exit(0)

            } else {
                Toast.makeText(this, "Please Input write Url", Toast.LENGTH_SHORT).show()
            }

        }
    }

}