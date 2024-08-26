package com.rmg.production_monitor.view.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
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
import com.rmg.production_monitor.view.activity.LoginActivity
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
    private lateinit var dhuAdapter:StationWiseDHUAdapter
    private lateinit var issuesAdapter:TopProductionsIssueAdapter
    private lateinit var operationsAdapter:TopProductionsIssueAdapter
    var lineId =  0
    private lateinit var handler: Handler
    // The list of coordinates for the dots
    // Declare dotCoordinates globally
    private var dotCoordinates: Array<DataPoint>? = null

    override fun getViewBinding(inflater: LayoutInflater): FragmentQualityBinding {



        return FragmentQualityBinding.inflate(inflater)
    }

    override fun callInitialApi() {
        super.callInitialApi()
        lineId = qualityViewModel.getLineId()?: 0
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
//                        initializeData()
                        heatMapData()
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

                    "User token expired".toast()
                    qualityViewModel.clearSession()

                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    requireActivity().finishAfterTransition()

                    //qualityViewModel.c()
                  //  showTokenExpiredToast()
                    //(requireActivity() as MainActivity).logout()
                }

                else -> {

                }
            }
        }
    }

    override fun initializeData() {
        super.initializeData()
        binding.apply {
            dhuList= mutableListOf()
            issuesList= mutableListOf()
            operationsList= mutableListOf()

            //DHU Adapter
            dhuAdapter=StationWiseDHUAdapter(requireContext(),dhuList)
            rvDhu.apply {
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

            //Defect Issue list
            issuesAdapter= TopProductionsIssueAdapter(requireContext(),issuesList.sortedBy {it.value }.reversed())
            rvViewIssues.apply {
                setHasFixedSize(true)
                addItemDecoration( DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                ))
                adapter=issuesAdapter
            }

            //operations List
            operationsAdapter= TopProductionsIssueAdapter(requireContext(),operationsList.sortedBy { it.value }.reversed())
            rvViewOperations.apply {
                setHasFixedSize(true)
                addItemDecoration( DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                ))
                adapter=operationsAdapter
            }

            imageView.viewTreeObserver.addOnGlobalLayoutListener {
                // Get the width and height of the imageView
                finalWidth = imageView.width.toFloat()
                finalHeight = imageView.height.toFloat()

            }
        }
    }

    private fun updateData() {
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                callInitialApi()
                handler.postDelayed(this, 10000)
            }
        })
    }

    private fun heatMapData(){
        if (::qualityPayload.isInitialized) {
            binding.apply {
                imagePath = qualityPayload.imageUrl?.replace("\\", "/")
                "${qualityPayload.heatMapPositions} = dotPositions".log("dim")

                dotCoordinates = qualityPayload.heatMapPositions.map { position ->
                    DataPoint(position.x.toDouble(), position.y.toDouble())
                }.toTypedArray()



                Glide.with(requireContext()).asBitmap().load(imagePath).error(R.drawable.chart_image)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun onResourceReady(
                            resource: Bitmap, transition: Transition<in Bitmap>?
                        ) {
                            // Make a mutable copy of the bitmap to draw on
                            mutableBitmap = resource.copy(Bitmap.Config.ARGB_8888, true)
                            // Set the modified bitmap to the ImageView
                            imageView.setImageBitmap(mutableBitmap)
                        }
                    })

                graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE // Disable grid lines

                graph.gridLabelRenderer.isHorizontalLabelsVisible = false // Hide horizontal labels (X-axis)

                graph.gridLabelRenderer.isVerticalLabelsVisible = false // Hide vertical labels (Y-axis)


                graph.viewport.isYAxisBoundsManual = true
                graph.viewport.setMinY(0.0)
                graph.viewport.setMaxY(10.0)

                graph.viewport.isXAxisBoundsManual = true
                graph.viewport.setMinX(0.0)
                graph.viewport.setMaxX(5.0)

                // Add the data points to the graph
                val dotSeries = PointsGraphSeries(dotCoordinates)
                dotSeries.color = Color.RED // Set color of the dots
                dotSeries.size = 6f // Set size of the dots
                graph.addSeries(dotSeries)
                textViewStyle.text = changeEndTextColor("Style - ${qualityPayload.style}", 6)
                textViewColor.text = changeEndTextColor("Color - ${qualityPayload.color}", 6)
                textViewBuyer.text = changeEndTextColor("Buyer - ${qualityPayload.buyer}", 6)
                tvRunDay.text = changeEndTextColor("Run Day - ${qualityPayload.RunningDay}", 9)
                tvRuningHour.text = changeEndTextColor("Running Hour - ${qualityPayload.RununningHour}", 13)
                if (qualityPayload.po?.isNotEmpty() == true){
                    textViewPO.text = changeEndTextColor("PO-${qualityPayload.po}", 2)
                }

                //OverAll DHU count
                "OVERALL DHU    ${qualityPayload.overAllDhu}".also { tvOverAllDHU.text = it }

                //Station wise DHU  list
                if (dhuList.isNotEmpty())dhuList.clear()
                val dhuValue=qualityPayload.stationWiseDhus.map {
                    DhuValueList(
                        it.stationName,
                        it.dHU
                    )
                }
                dhuList.addAll(dhuValue)
                dhuAdapter.submit(dhuList)



                //Defect Issue list
                if (issuesList.isNotEmpty())issuesList.clear()
                val issues= qualityPayload.heatMapIssues.map {
                    TopProductionsIssue(
                        it.issueName,
                        it.count
                    )
                }
                issuesList.addAll(issues)
                issuesAdapter.submit(issuesList.sortedBy { it.value }.reversed())


                //operations List
                if (operationsList.isNotEmpty())operationsList.clear()
                val operations= qualityPayload.heatMapOperations.map {
                    TopProductionsIssue(
                        it.operationName,
                        it.count
                    )
                }
                operationsList.addAll(operations)
                operationsAdapter.submit(operationsList.sortedBy { it.value }.reversed())


                //defect and reject count
                "${qualityPayload.remainingDiffective}".also {tvRemDefectCount.text = it }
                "${qualityPayload.totalReject}".also { tvRejectsCount.text = it }

            }

        }
    }
    private fun changeEndTextColor(text: String, start: Int): SpannableString {
        val spannableString = SpannableString(text)
        val white = ForegroundColorSpan(Color.WHITE)
        spannableString.setSpan(white, start, text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        return spannableString
    }


    override fun onResume() {
        super.onResume()
        updateData()
        (requireActivity() as MainActivity).binding.imgBtnRefresh.setOnClickListener {
            lineId = qualityViewModel.getLineId()?: 0
            networkChecker {
                qualityViewModel.getHeatmap(lineId)
            }
        }
    }


    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }





}