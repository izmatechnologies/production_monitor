package com.rmg.production_monitor.view.fragment

import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.databinding.FragmentUrlSetupBinding
import com.rmg.production_monitor.viewModel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UrlSetupFragment : BaseFragment<FragmentUrlSetupBinding>() {
    private val authenticationViewModel by viewModels<AuthenticationViewModel>()
    var changeUrl=""
    override fun getViewBinding(inflater: LayoutInflater): FragmentUrlSetupBinding {

        return FragmentUrlSetupBinding.inflate(inflater)


    }

    override fun initializeData() {
        binding.saveButton.setOnClickListener {
            if (binding.urlInput.text?.startsWith("http://", false) == true ||
                binding.urlInput.text?.startsWith("https://", false) == true
            ) {
                networkChecker {
                    authenticationViewModel.saveUrl(binding.urlInput.text.toString())
                    Toast.makeText(requireContext(), "Url Saved", Toast.LENGTH_SHORT).show()
                    requireActivity().finishAfterTransition()
                }
            } else {
                Toast.makeText(requireContext(), "Please Input write Url", Toast.LENGTH_SHORT).show()
            }

        }
    }


}