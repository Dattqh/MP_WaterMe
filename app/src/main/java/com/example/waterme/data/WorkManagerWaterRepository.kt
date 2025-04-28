package com.example.waterme.data

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.waterme.model.Plant
import com.example.waterme.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

class WorkManagerWaterRepository(context: Context) : WaterRepository {
    private val workManager = WorkManager.getInstance(context)

    override val plants: List<Plant>
        get() = DataSource.plants

    override fun scheduleReminder(duration: Long, unit: TimeUnit, plantName: String) {
        println("WorkManagerWaterRepository: Scheduling reminder for $plantName with duration $duration $unit")

        val data = Data.Builder()
            .putString(WaterReminderWorker.nameKey, plantName)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<WaterReminderWorker>()
            .setInitialDelay(duration, unit)
            .setInputData(data)
            .build()

        workManager.enqueueUniqueWork(
            "$plantName$duration",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )

        // Theo dõi trạng thái công việc
        workManager.getWorkInfoByIdLiveData(workRequest.id).observeForever { workInfo ->
            if (workInfo != null) {
                println("Work state for $plantName: ${workInfo.state}")
            }
        }
    }
}