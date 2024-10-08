package com.rmg.production_monitor.view.fragment

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.PointsGraphSeries
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.databinding.FragmentQualityBinding
import com.rmg.production_monitor.models.local.entity.HeatMapEntity
import com.rmg.production_monitor.models.local.viewModel.HeatmapLocalViewModel
import com.rmg.production_monitor.models.remote.quality.DhuValueList
import com.rmg.production_monitor.models.remote.quality.TopProductionsIssue
import com.rmg.production_monitor.view.activity.MainActivity
import com.rmg.production_monitor.view.adapter.StationWiseDHUAdapter
import com.rmg.production_monitor.view.adapter.TopProductionsIssueAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class QualityFragment : BaseFragment<FragmentQualityBinding>() {
    private var updateJob: Job? = null
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
   // private lateinit var handler: Handler

    // The list of coordinates for the dots
    // Declare dotCoordinates globally
    private var dotCoordinates: Array<DataPoint>? = null
    /*Local DB*/
    private lateinit var qualityPayload: HeatMapEntity
    private lateinit var heatmapLocalViewModel: HeatmapLocalViewModel
    override fun getViewBinding(inflater: LayoutInflater): FragmentQualityBinding {
        heatmapLocalViewModel= ViewModelProvider(this)[HeatmapLocalViewModel::class.java]


        return FragmentQualityBinding.inflate(inflater)
    }

    override fun callInitialApi() {
        super.callInitialApi()

        heatmapLocalViewModel.getHeatmapList.observe(viewLifecycleOwner){heatmap->
            if (heatmap !=null){
                qualityPayload=heatmap
                heatMapData()
                (Gson().toJson(qualityPayload.payload?.stationWiseDhus)).log()
            }
        }
    }

    override fun setupObserver() {
        super.setupObserver()
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
        updateJob?.cancel()

        // Start a new coroutine in the main thread (UI)
        updateJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                // Call your initial API function
                heatmapLocalViewModel.getHeatmapList.observe(viewLifecycleOwner){heatmap->
                    if (heatmap !=null){
                        qualityPayload=heatmap
                        heatMapData()
                        (Gson().toJson(qualityPayload.payload?.stationWiseDhus)).log()
                    }
                }
                // Suspend for 10 seconds (non-blocking)
                delay(10000)
            }
        }
    }
//    private fun updateData() {
//        handler = Handler(Looper.getMainLooper())
//        handler.post(object : Runnable {
//            override fun run() {
//                heatmapLocalViewModel.getHeatmapList.observe(viewLifecycleOwner){heatmap->
//                    if (heatmap !=null){
//                        qualityPayload=heatmap
//                        heatMapData()
//                        (Gson().toJson(qualityPayload.payload?.stationWiseDhus)).log()
//                    }
//                }
//                handler.postDelayed(this, 10000)
//            }
//        })
//    }

    private fun heatMapData(){
        if (::qualityPayload.isInitialized) {
            binding.apply {
                imagePath = qualityPayload.payload?.imageUrl?.replace("\\", "/")

                "${qualityPayload.payload?.heatMapPositions} = dotPositions".log("dim")

                dotCoordinates = qualityPayload.payload?.heatMapPositions?.map { position ->
                    DataPoint(position.x.toDouble(), position.y.toDouble())
                }?.toTypedArray()



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
                textViewStyle.text = changeEndTextColor("Style - ${qualityPayload.payload?.style}", 6)
                textViewColor.text = changeEndTextColor("Color - ${qualityPayload.payload?.color}", 6)
                textViewBuyer.text = changeEndTextColor("Buyer - ${qualityPayload.payload?.buyer}", 6)
                tvRunDay.text = changeEndTextColor("Run Day - ${qualityPayload.payload?.RunningDay}", 9)
                tvRuningHour.text = changeEndTextColor("Running Hour - ${qualityPayload.payload?.RununningHour}", 13)
                if (qualityPayload.payload?.po?.isNotEmpty() == true){
                    textViewPO.text = changeEndTextColor("PO-${qualityPayload.payload?.po}", 2)
                }

                //OverAll DHU count
                "OVERALL DHU    ${qualityPayload.payload?.overAllDhu}".also { tvOverAllDHU.text = it }

                //Station wise DHU  list
                if (dhuList.isNotEmpty())dhuList.clear()
                val dhuValue= qualityPayload.payload?.stationWiseDhus?.map {
                    DhuValueList(
                        it.stationName,
                        it.dHU
                    )
                }
                if (dhuValue != null) {
                    dhuList.addAll(dhuValue)
                }
                dhuAdapter.submit(dhuList)



                //Defect Issue list
                if (issuesList.isNotEmpty())issuesList.clear()
                val issues= qualityPayload.payload?.heatMapIssues?.map {
                    TopProductionsIssue(
                        it.issueName,
                        it.count
                    )
                }
                if (issues != null) {
                    issuesList.addAll(issues)
                }
                issuesAdapter.submit(issuesList.sortedBy { it.value }.reversed())


                //operations List
                if (operationsList.isNotEmpty())operationsList.clear()
                val operations= qualityPayload.payload?.heatMapOperations?.map {
                    TopProductionsIssue(
                        it.operationName,
                        it.count
                    )
                }
                if (operations != null) {
                    operationsList.addAll(operations)
                }
                operationsAdapter.submit(operationsList.sortedBy { it.value }.reversed())


                //defect and reject count
                "${qualityPayload.payload?.remainingDiffective}".also {tvRemDefectCount.text = it }
                "${qualityPayload.payload?.totalReject}".also { tvRejectsCount.text = it }

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
            heatmapLocalViewModel.getHeatmapList.observe(viewLifecycleOwner){heatmap->
                if (heatmap !=null){
                    qualityPayload=heatmap
                    heatMapData()
                    (Gson().toJson(qualityPayload.payload?.stationWiseDhus)).log()
                }
            }
        }
    }


//    override fun onPause() {
//        super.onPause()
//     //   handler.removeCallbacksAndMessages(null)
//    }

    override fun onDestroy() {
        super.onDestroy()
        updateJob?.cancel()
    }




}