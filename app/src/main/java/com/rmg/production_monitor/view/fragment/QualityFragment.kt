package com.rmg.production_monitor.view.fragment

import android.content.Context
import android.content.Intent
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
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.PointsGraphSeries
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult.*
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.extention.showLogoutDialog
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentQualityBinding
import com.rmg.production_monitor.models.remote.quality.QualityPayload
import com.rmg.production_monitor.view.activity.LoginActivity
import com.rmg.production_monitor.view.activity.MainActivity
import com.rmg.production_monitor.view.activity.ToolbarInterface
import com.rmg.production_monitor.viewModel.QualityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QualityFragment : BaseFragment<FragmentQualityBinding>() ,ToolbarInterface{

    private val qualityViewModel by viewModels<QualityViewModel>()
    private lateinit var qualityPayload: QualityPayload
    private var imagePath: String? = null

    private var finalHeight: Float = 0.0f
    private var finalWidth: Float = 0.0f
    private lateinit var mutableBitmap: Bitmap
    private var initialBitmap:Boolean=false
    private var flag:Boolean=false
    var lineId =  0

    // The list of coordinates for the dots
    // Declare dotCoordinates globally
    var dotCoordinates: Array<DataPoint>? = null

    override fun getViewBinding(inflater: LayoutInflater): FragmentQualityBinding {
        return FragmentQualityBinding.inflate(inflater)
    }

    override fun callInitialApi() {
        super.callInitialApi()
        lineId = qualityViewModel.getLineId()?.toInt() ?: 0
        networkChecker {
            qualityViewModel.getHeatmap(lineId)
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


        binding.imageView.viewTreeObserver.addOnGlobalLayoutListener {
            // Get the width and height of the imageView
            finalWidth = binding.imageView.width.toFloat()
            finalHeight = binding.imageView.height.toFloat()

        }
        if (::qualityPayload.isInitialized) {
            imagePath = qualityPayload.imageUrl.replace("\\", "/")
            "${qualityPayload.heatMapPositions} = dotPositions".log("dim")

            "finalHeight: $finalHeight finalWidth: $finalWidth".log("dim")
            dotCoordinates = qualityPayload.heatMapPositions.map { position ->
                DataPoint(position.x.toDouble(), position.y.toDouble())
            }.toTypedArray()
//            if (initialBitmap){
//                drawCanvas(dotCoordinates?: mutableListOf())
//            }



            Glide.with(requireContext()).asBitmap().load(imagePath).error(R.drawable.chart_image)
                .into(object : BitmapImageViewTarget(binding.imageView) {
                    override fun onResourceReady(
                        resource: Bitmap, transition: Transition<in Bitmap>?
                    ) {
                        // Make a mutable copy of the bitmap to draw on
                        mutableBitmap = resource.copy(Bitmap.Config.ARGB_8888, true)
                        val canvas = Canvas(mutableBitmap)
                        val paint = Paint().apply {
                            color = Color.RED
                            style = Paint.Style.FILL
                            isAntiAlias = true
                        }

                        val dotRadius = 20f // Radius of the dots

                        // Draw dots on the bitmap
//                        dotCoordinates?.forEach { (x, y) ->
//                            canvas.drawCircle(
//                                x,
//                                y,
//                                dotRadius,
//                                paint
//                            )
//                        }
//                        "$dotCoordinates for x,y point".log("dim")


                        // Set the modified bitmap to the ImageView
                        binding.imageView.setImageBitmap(mutableBitmap)
                    }
                })

//            Glide.with(requireContext())
//                .load(imagePath)
//                .error(R.drawable.chart_image)
//                .into(binding.imageView)

            binding.graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE // Disable grid lines

            binding.graph.gridLabelRenderer.isHorizontalLabelsVisible = false // Hide horizontal labels (X-axis)

            binding.graph.gridLabelRenderer.isVerticalLabelsVisible = false // Hide vertical labels (Y-axis)


            binding.graph.viewport.isYAxisBoundsManual = true
            binding.graph.viewport.setMinY(0.0)
            binding.graph.viewport.setMaxY(10.0)

            binding.graph.viewport.isXAxisBoundsManual = true
            binding.graph.viewport.setMinX(0.0)
            binding.graph.viewport.setMaxX(5.0)

            // Data points
//            val dataPoints = arrayOf(
//                DataPoint(1.4314515888690948, 8.536292761564255),
//                DataPoint(4.133064448833466, 8.134373128414154),
//                DataPoint(4.279233813285828, 0.6418716907501221)
//            )

            // Add the data points to the graph
            val dotSeries = PointsGraphSeries(dotCoordinates)
            dotSeries.color = Color.RED // Set color of the dots
            dotSeries.size = 10f // Set size of the dots
            binding.graph.addSeries(dotSeries)



            binding.textViewStyle.text = changeEndTextColor("Style - ${qualityPayload.style}", 6)
            binding.textViewColor.text = changeEndTextColor("Color - ${qualityPayload.color}", 6)
            binding.textViewBuyer.text = changeEndTextColor("Buyer - ${qualityPayload.buyer}", 6)
            if (qualityPayload.po.isNotEmpty()){
                binding.textViewPO.text = changeEndTextColor(qualityPayload.po, 2)
            }

            "OVERALL DHU ${qualityPayload.overAllDhu}".also { binding.textViewOverallDHU.text = it }

            qualityPayload.stationWiseDhus.forEach { stationWiseDhus ->
                if (stationWiseDhus.stationName == "Back Side") {
                    "${stationWiseDhus.dHU} Back".also { binding.textViewBack.text = it }
                } else if (stationWiseDhus.stationName == "Inside") {
                    "${stationWiseDhus.dHU} Inside QC".also { binding.textViewInside.text = it }
                }
            }

            "REM.DEFECTIVE ${qualityPayload.remainingDiffective}".also {
                binding.textViewRemDefective.text = it
            }
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

        setPageName("HeatMap")

    }

//    private fun drawCanvas(dotCoordinates: List<Pair<Float, Float>>) {
//
//    }

    private fun changeEndTextColor(text: String, start: Int): SpannableString {
        val spannableString = SpannableString(text)
        val white = ForegroundColorSpan(Color.WHITE)
        spannableString.setSpan(white, start, text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        return spannableString
    }

    override fun onRefreshButtonClick() {
        "toast".toast(requireContext())
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            (requireActivity() as MainActivity).setOnToolBarListener(this)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ex.toString().log("dim")
        }
    }
}