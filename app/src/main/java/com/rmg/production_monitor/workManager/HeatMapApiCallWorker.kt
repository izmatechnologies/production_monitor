package com.rmg.production_monitor.workManager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.models.local.dao.HeatMapDao
import com.rmg.production_monitor.models.local.entity.HeatMapEntity
import com.rmg.production_monitor.models.local.entity.HeatMapIssue
import com.rmg.production_monitor.models.local.entity.HeatMapOperation
import com.rmg.production_monitor.models.local.entity.HeatMapPosition
import com.rmg.production_monitor.models.local.entity.QualityPayload
import com.rmg.production_monitor.models.local.entity.StationWiseDhu
import com.rmg.production_monitor.repository.demo.QualityDemoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class HeatMapApiCallWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val apiRepository: QualityDemoRepository,
    private val heatMapDao: HeatMapDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val lineId = inputData.getString("REQUEST_ID") ?: return Result.failure()
        return try {
            val response = apiRepository.getHeatmap(lineId.toInt())
            if (response.isSuccessful) {
                response.body()?.payload?.let { payload ->
                    val insertPayload = HeatMapEntity(
                        0, QualityPayload(
                            payload.buyer,
                            payload.RunningDay,
                            payload.RununningHour,
                            payload.color,
                            payload.heatMapIssues.map { issue ->
                                HeatMapIssue(
                                    issue.issueName,
                                    issue.count
                                )
                            },
                            payload.heatMapOperations.map { operations ->
                                HeatMapOperation(
                                    operations.operationName,
                                    operations.count
                                )
                            },
                            payload.heatMapPositions.map { positions ->
                                HeatMapPosition(
                                    positions.x,
                                    positions.y
                                )
                            },
                            payload.imageUrl,
                            payload.markingImageUrl,
                            payload.overAllDhu,
                            payload.po,
                            payload.remainingDiffective,
                            payload.stationWiseDhus.map { dhu ->
                                StationWiseDhu(
                                    dhu.dHU,
                                    dhu.stationName
                                )
                            },
                            payload.style,
                            payload.totalReject

                        )
                    )
                    heatMapDao.insertHeatMapData(insertPayload)
                }
                // Handle success
                Result.success()

            } else {
                // Handle failure
                Result.failure()
            }
        } catch (e: Exception) {
            // Handle exception
            Result.retry()
        }
    }
}