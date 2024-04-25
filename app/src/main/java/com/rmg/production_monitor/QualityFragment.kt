package com.rmg.production_monitor

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult.*
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentQualityBinding
import com.rmg.production_monitor.models.remote.quality.QualityPayload
import com.rmg.production_monitor.viewModel.QualityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QualityFragment : BaseFragment<FragmentQualityBinding>() {

    private val qualityViewModel by viewModels<QualityViewModel>()
    private lateinit var qualityPayload: QualityPayload
    private var imagePath: String? = null

    // The list of coordinates for the dots
    private var dotCoordinates: List<Pair<Float, Float>>? = null

    override fun getViewBinding(inflater: LayoutInflater): FragmentQualityBinding {
        return FragmentQualityBinding.inflate(inflater)
    }

    override fun callInitialApi() {
        super.callInitialApi()
        networkChecker {
            qualityViewModel.getHeatmap(1)
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        qualityViewModel.heatMapLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> {
                    hideLoader()
                    it.data?.payload?.let { payload ->
                        qualityPayload = payload
                        initializeData()
                    }
                }

                is Error -> {
                    hideLoader()
                    it.message.toString().toast()
                }

                is Loading -> {
                    showLoader()
                }

                is SessionOut -> {
                    qualityViewModel.clearSession()
                    showTokenExpiredToast()
                    //(requireActivity() as MainActivity).logout()
                }

                else -> {

                }
            }
        }
    }

    override fun initializeData() {
        super.initializeData()

        if(::qualityPayload.isInitialized) {

            imagePath = qualityPayload.imageUrl.replace("\\", "/")
            dotCoordinates = qualityPayload.heatMapPositions.map { position ->
                Pair(position.x.toFloat(), position.y.toFloat())
            }

            Glide.with(requireContext()).asBitmap().load(imagePath).error(R.drawable.chart_image)
                .into(object : BitmapImageViewTarget(binding.imageView) {
                    override fun onResourceReady(
                        resource: Bitmap, transition: Transition<in Bitmap>?
                    ) {
                        // Make a mutable copy of the bitmap to draw on
                        val mutableBitmap = resource.copy(Bitmap.Config.ARGB_8888, true)
                        val canvas = Canvas(mutableBitmap)
                        val paint = Paint().apply {
                            color = Color.RED
                            style = Paint.Style.FILL
                            isAntiAlias = true
                        }

                        val dotRadius = 20f // Radius of the dots

                        // Draw dots on the bitmap
                        dotCoordinates?.forEach { (x, y) ->
                            canvas.drawCircle(x, y, dotRadius, paint)
                        }

                        // Set the modified bitmap to the ImageView
                        binding.imageView.setImageBitmap(mutableBitmap)
                    }
                })

            binding.textViewStyle.text = changeEndTextColor("Style - ${qualityPayload.style}", 6)
            binding.textViewColor.text = changeEndTextColor("Color - ${qualityPayload.color}", 6)
            binding.textViewBuyer.text = changeEndTextColor("Buyer - ${qualityPayload.buyer}", 6)
            binding.textViewPO.text = changeEndTextColor(qualityPayload.po, 2)

            "OVERALL DHU ${qualityPayload.overAllDhu}".also { binding.textViewOverallDHU.text = it }

            qualityPayload.stationWiseDhus.forEach { stationWiseDhus ->
                if (stationWiseDhus.stationName == "Back Side") {
                    "${stationWiseDhus.dHU} Back".also { binding.textViewBack.text = it }
                } else if (stationWiseDhus.stationName == "Inside") {
                    "${stationWiseDhus.dHU} Inside QC".also { binding.textViewInside.text = it }
                }
            }

            "REM.DEFECTIVE ${qualityPayload.remainingDiffective}".also { binding.textViewRemDefective.text = it }
            "REJECTS ${qualityPayload.totalReject}".also { binding.textViewRejects.text = it }

            var operationsText = ""

            qualityPayload.heatMapOperations.forEach { operations ->
                operationsText += "\n    ${operations.operationName}"
            }

            "TOP 3 DEFECT OPERATIONS $operationsText".also { binding.textViewOperations.text = it }

            var issuesText = ""

            qualityPayload.heatMapIssues.forEach { issues ->
                issuesText += "\n    ${issues.issueName}"
            }

            "TOP 3 DEFECT ISSUES $issuesText".also { binding.textViewIssues.text = it }
        }
    }

    private fun changeEndTextColor(text: String, start: Int): SpannableString {
        val spannableString = SpannableString(text)
        val white = ForegroundColorSpan(Color.WHITE)
        spannableString.setSpan(white, start, text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        return spannableString
    }
}