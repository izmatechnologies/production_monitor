package com.rmg.production_monitor.view.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.PointsGraphSeries
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult.Error
import com.rmg.production_monitor.core.data.NetworkResult.Loading
import com.rmg.production_monitor.core.data.NetworkResult.SessionOut
import com.rmg.production_monitor.core.data.NetworkResult.Success
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentQualityBinding
import com.rmg.production_monitor.models.remote.quality.DhuValueList
import com.rmg.production_monitor.models.remote.quality.QualityPayload
import com.rmg.production_monitor.models.remote.quality.TopProductionsIssue
import com.rmg.production_monitor.view.activity.MainActivity

import com.rmg.production_monitor.view.adapter.StationWiseDHUAdapter
import com.rmg.production_monitor.view.adapter.TopProductionsIssueAdapter
import com.rmg.production_monitor.viewModel.QualityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QualityFragment : BaseFragment<FragmentQualityBinding>() {
    private val qualityViewModel by viewModels<QualityViewModel>()
    private lateinit var qualityPayload: QualityPayload
    private var imagePath: String? = null
    private var finalHeight: Float = 0.0f
    private var finalWidth: Float = 0.0f
    private lateinit var mutableBitmap: Bitmap
    private lateinit var dhuList: MutableList<DhuValueList>
    private lateinit var issuesList: MutableList<TopProductionsIssue>
    private lateinit var operationsList: MutableList<TopProductionsIssue>
    var lineId =  0

    // The list of coordinates for the dots
    // Declare dotCoordinates globally
    private var dotCoordinates: Array<DataPoint>? = null

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


        dhuList= mutableListOf()
        issuesList= mutableListOf()
        operationsList= mutableListOf()

        binding.imageView.viewTreeObserver.addOnGlobalLayoutListener {
            // Get the width and height of the imageView
            finalWidth = binding.imageView.width.toFloat()
            finalHeight = binding.imageView.height.toFloat()

        }
        if (::qualityPayload.isInitialized) {
            imagePath = qualityPayload.imageUrl.replace("\\", "/")
            "${qualityPayload.heatMapPositions} = dotPositions".log("dim")

            dotCoordinates = qualityPayload.heatMapPositions.map { position ->
                DataPoint(position.x.toDouble(), position.y.toDouble())
            }.toTypedArray()



            Glide.with(requireContext()).asBitmap().load(imagePath).error(R.drawable.chart_image)
                .into(object : BitmapImageViewTarget(binding.imageView) {
                    override fun onResourceReady(
                        resource: Bitmap, transition: Transition<in Bitmap>?
                    ) {
                        // Make a mutable copy of the bitmap to draw on
                        mutableBitmap = resource.copy(Bitmap.Config.ARGB_8888, true)
                        // Set the modified bitmap to the ImageView
                        binding.imageView.setImageBitmap(mutableBitmap)
                    }
                })

            binding.graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE // Disable grid lines

            binding.graph.gridLabelRenderer.isHorizontalLabelsVisible = false // Hide horizontal labels (X-axis)

            binding.graph.gridLabelRenderer.isVerticalLabelsVisible = false // Hide vertical labels (Y-axis)


            binding.graph.viewport.isYAxisBoundsManual = true
            binding.graph.viewport.setMinY(0.0)
            binding.graph.viewport.setMaxY(10.0)

            binding.graph.viewport.isXAxisBoundsManual = true
            binding.graph.viewport.setMinX(0.0)
            binding.graph.viewport.setMaxX(5.0)

            // Add the data points to the graph
            val dotSeries = PointsGraphSeries(dotCoordinates)
            dotSeries.color = Color.RED // Set color of the dots
            dotSeries.size = 6f // Set size of the dots
            binding.graph.addSeries(dotSeries)



            binding.textViewStyle.text = changeEndTextColor("Style - ${qualityPayload.style}", 6)
            binding.textViewColor.text = changeEndTextColor("Color - ${qualityPayload.color}", 6)
            binding.textViewBuyer.text = changeEndTextColor("Buyer - ${qualityPayload.buyer}", 6)
            if (qualityPayload.po.isNotEmpty()){
                binding.textViewPO.text = changeEndTextColor(qualityPayload.po, 2)
            }

            "OVERALL DHU ${qualityPayload.overAllDhu}".also { binding.textViewOverallDHU.text = it }


            if (dhuList.isNotEmpty())dhuList.clear()
            //Station wise DHU
            val dhuValue=qualityPayload.stationWiseDhus.map {
                DhuValueList(
                    it.stationName,
                    it.dHU
                )
            }
            dhuList.addAll(dhuValue)

            val dhuAdapter=StationWiseDHUAdapter(dhuValue)
            binding.rvDhu.apply {
                setHasFixedSize(true)
                addItemDecoration( DividerItemDecoration(
                    activity,
                    DividerItemDecoration.HORIZONTAL
                ))
                addItemDecoration( DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                ))
                adapter=dhuAdapter
            }

            //Defect Issue
            if (issuesList.isNotEmpty())issuesList.clear()
            val issues= qualityPayload.heatMapIssues.map {
                TopProductionsIssue(
                    it.issueName,
                    it.count
                )
            }
            issuesList.addAll(issues)
            val issuesAdapter=
                context?.let {context-> TopProductionsIssueAdapter(context,issuesList.sortedBy {it.value }.reversed()) }
            binding.rvViewIssues.apply {
                setHasFixedSize(true)
                addItemDecoration( DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                ))
                adapter=issuesAdapter
            }

            //operations List
            if (operationsList.isNotEmpty())operationsList.clear()
            val operations= qualityPayload.heatMapOperations.map {
                TopProductionsIssue(
                    it.operationName,
                    it.count
                )
            }
            operationsList.addAll(operations)
            val operationsAdapter=
                context?.let {context-> TopProductionsIssueAdapter(context,operationsList.sortedBy { it.value }.reversed()) }
            binding.rvViewOperations.apply {
                setHasFixedSize(true)
                addItemDecoration( DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                ))
                adapter=operationsAdapter
            }

            "${qualityPayload.remainingDiffective}".also {
                binding.tvRemDefectCount.text = it
            }
            "${qualityPayload.totalReject}".also { binding.tvRejectsCount.text = it }

        }

        (requireActivity() as MainActivity).binding.imgBtnRefresh.setOnClickListener {

            lineId = qualityViewModel.getLineId()?.toInt() ?: 0
            networkChecker {
                qualityViewModel.getHeatmap(lineId)
            }
        }

    }
    private fun changeEndTextColor(text: String, start: Int): SpannableString {
        val spannableString = SpannableString(text)
        val white = ForegroundColorSpan(Color.WHITE)
        spannableString.setSpan(white, start, text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        return spannableString
    }








}